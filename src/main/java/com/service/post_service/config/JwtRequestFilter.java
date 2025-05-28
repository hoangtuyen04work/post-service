package com.service.post_service.config;

import com.commons.commons_client.utils.AuthTokenClient;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private AuthTokenClient authTokenClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String userId = null;
        List<String> roles = null;
        String jwt = null;
        if (header != null && header.startsWith("Bearer ")) {
            jwt = header.replace("Bearer ", "");
            boolean isOk = authTokenClient.auth(jwt);
            if(isOk){
                SignedJWT signedJWT = null;
                try {
                    signedJWT = SignedJWT.parse(jwt);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                JWTClaimsSet claims = null;
                try {
                    claims = signedJWT.getJWTClaimsSet();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                try {
                    roles = claims.getStringListClaim("roles");
                    userId = claims.getSubject();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    List<GrantedAuthority> authorities = roles.stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .collect(Collectors.toList());
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userId, null, authorities);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }


        filterChain.doFilter(request, response);
    }
}
