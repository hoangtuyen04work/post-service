package com.service.post_service.specification;

import com.service.post_service.entity.PostEntity;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecification {
    public static Specification<PostEntity> hasTitle(String title) {
        return (title == null || title.isBlank()) ? null : (root, query, cb) ->
                cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<PostEntity> hasContent(String content) {
        return (content == null || content.isBlank()) ? null : (root, query, cb) ->
                cb.like(cb.lower(root.get("content")), "%" + content.toLowerCase() + "%");
    }

    public static Specification<PostEntity> hasUserId(String userId) {
        return (userId == null || userId.isBlank()) ? null : (root, query, cb) ->
                cb.like(cb.lower(root.get("userId").get("userId")), "%" + userId + "%");
    }
}
