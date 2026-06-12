package com.themove.repository;

import com.themove.entity.Like;
import com.themove.entity.Post;
import com.themove.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserAndPost(User user, Post post);

    long countByPost(Post post);
}