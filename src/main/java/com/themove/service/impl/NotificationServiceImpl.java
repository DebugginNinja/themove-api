package com.themove.service.impl;

import com.themove.entity.Notification;
import com.themove.model.User;
import com.themove.repository.NotificationRepository;
import com.themove.repository.UserRepository;
import com.themove.service.NotificationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createNotification(User recipient,
                                   User sender,
                                   String type,
                                   Long postId,
                                   Long commentId) {

        // 🚫 prevent self-notifications
        if (recipient.getId().equals(sender.getId())) {
            return;
        }

        Notification notification = new Notification(
                recipient,
                sender,
                type,
                postId,
                commentId
        );

        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getUserNotifications() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return notificationRepository.findByRecipientOrderByCreatedAtDesc(user);
    }
}