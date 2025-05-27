package com.service.post_service.controller;

import com.commons.commonscore.dto.ApiResponse;
import com.commons.commonscore.exception.AppException;
import com.service.post_service.service.UserIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserIdController {
    @Autowired
    private UserIdService userIdService;

    @PostMapping
    public ApiResponse<String> newUserId(@RequestBody String userId) throws AppException {
        return ApiResponse.<String>builder()
                .data(userIdService.createUserId(userId))
                .build();
    }
}
