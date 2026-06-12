package com.themove.service;

import com.themove.entity.Notification;

import com.themove.model.User;
import java.util.List;

public interface NotificationService {

    void createNotification(User recipient,
                            User sender,
                            String type,
                            Long postId,
                            Long commentId);

    List<Notification> getUserNotifications();
}