package com.service.post_service.repo;


import com.service.post_service.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<PostEntity, String> , JpaSpecificationExecutor<PostEntity> {
//    Page<PostEntity> findPostByUser(UserEntity user, Pageable pageable);
    Page<PostEntity> findPostByContentContainingIgnoreCase(String keyWord, Pageable pageable);
//    @Query("SELECT p FROM PostEntity p WHERE p.user in :users")
//    Page<PostEntity> findByUsers(Set<UserEntity> users, Pageable pageable);
}
