package com.themove.service.impl;

import com.themove.entity.Like;
import com.themove.entity.Post;
import com.themove.model.User;
import com.themove.repository.LikeRepository;
import com.themove.repository.PostRepository;
import com.themove.repository.UserRepository;
import com.themove.service.LikeService;
import com.themove.service.NotificationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public LikeServiceImpl(LikeRepository likeRepository,
                           PostRepository postRepository,
                           UserRepository userRepository,
                           NotificationService notificationService) {

        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Override
    public void likePost(Long postId) {

        User user = getCurrentUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Optional<Like> existingLike =
                likeRepository.findByUserAndPost(user, post);

        // 🔁 TOGGLE BEHAVIOR
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            return;
        }

        Like like = new Like(user, post);
        likeRepository.save(like);

        // 🔔 NOTIFICATION
        notificationService.createNotification(
                post.getUser(),   // recipient
                user,             // sender
                "LIKE",
                postId,
                null
        );
    }

    @Override
    public void unlikePost(Long postId) {

        User user = getCurrentUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        likeRepository.findByUserAndPost(user, post)
                .ifPresent(likeRepository::delete);
    }

    @Override
    public long countLikes(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return likeRepository.countByPost(post);
    }

    private User getCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}