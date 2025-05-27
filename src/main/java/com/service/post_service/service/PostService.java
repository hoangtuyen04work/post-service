package com.service.post_service.service;

import com.commons.commonscore.dto.response.PageResponse;
import com.commons.commonscore.exception.AppException;
import com.service.post_service.dto.request.PostCreationRequest;
import com.service.post_service.dto.response.PostResponse;
import com.service.post_service.entity.PostEntity;
import com.service.post_service.repo.PostRepo;
import com.service.post_service.specification.PostSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService{
    @Autowired
    private PostRepo repo;

    @Autowired
    private UserIdService userIdService;

    private PostEntity toPostEntity(PostCreationRequest postCreationRequest) throws AppException {
        return PostEntity.builder()
                .content(postCreationRequest.getContent())
                .title(postCreationRequest.getTitle())
                .userId(userIdService.findUserIdEntity(postCreationRequest.getUserId()))
                .build();
    }

    private PostResponse toPostResponse(PostEntity postEntity) {
        return PostResponse.builder()
                .id(postEntity.getId())
                .content(postEntity.getContent())
                .title(postEntity.getTitle())
                .creationDate(postEntity.getCreationDate())
                .deleteDate(postEntity.getDeleteDate())
                .modifiedDate(postEntity.getModifiedDate())
                .userId(postEntity.getUserId().getUserId())
                .build();
    }

    public PostResponse createPost(PostCreationRequest request) throws AppException {
        PostEntity entity = toPostEntity(request);
        entity = repo.save(entity);
        return toPostResponse(entity);
    }

    public PostResponse updatePost(PostCreationRequest request) throws AppException {
        PostEntity entity = toPostEntity(request);

        entity = repo.save(entity);
        return toPostResponse(entity);
    }

    public PageResponse<PostResponse> findPost(String title, String content, PageRequest pageable)  {
        Specification<PostEntity> spec = Specification
                .where(PostSpecification.hasTitle(title))
                .and(PostSpecification.hasContent(content));
        Page<PostEntity> list = repo.findAll(spec, pageable);
        return PageResponse.<PostResponse>builder()
                .content(list.stream().map(this::toPostResponse).collect(Collectors.toList()))
                .pageNumber(list.getNumber())
                .pageSize(list.getSize())
                .totalElements(list.getTotalElements())
                .totalPages(list.getTotalPages())
                .build();
    }
}
