package com.service.post_service.service;

import com.commons.commonscore.exception.AppException;
import com.commons.commonscore.exception.ErrorCode;
import com.service.post_service.entity.UserIdEntity;
import com.service.post_service.repo.UserIdRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UserIdService {
    @Autowired
    private UserIdRepo repo;

    public UserIdEntity getCurrentUser() throws AppException {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return findUserIdEntity(userId);
    }

    public UserIdEntity findUserIdEntity(String id) throws AppException {
        return repo.findByUserId(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public String createUserId(String id) throws AppException {
        boolean x = repo.existsByUserId(id);
        if(x) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        UserIdEntity entity = UserIdEntity.builder()
                .userId(id)
                .build();
        repo.save(entity);
        return entity.getUserId();
    }
}
