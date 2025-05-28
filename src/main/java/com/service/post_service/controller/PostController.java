package com.service.post_service.controller;

import com.commons.commonscore.dto.ApiResponse;

import com.commons.commonscore.dto.response.PageResponse;

import com.commons.commonscore.exception.AppException;
import com.service.post_service.dto.request.PostCreationRequest;
import com.service.post_service.dto.response.PostResponse;
import com.service.post_service.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService service;

    @GetMapping
    public ApiResponse<PageResponse<PostResponse>> findByName(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return ApiResponse.<PageResponse<PostResponse>>builder()
                .data(service.findPost(userId, title, content, pageable))
                .build();
    }

    @PostMapping
    public ApiResponse<PostResponse> createPost(@RequestBody PostCreationRequest request) throws AppException {
        return ApiResponse.<PostResponse>builder()
                .data(service.createPost(request))
                .build();
    }

    @PutMapping
    public ApiResponse<PostResponse> updatePost(@RequestBody PostCreationRequest request) throws AppException {
        return ApiResponse.<PostResponse>builder()
                .data(service.updatePost(request))
                .build();
    }

}

