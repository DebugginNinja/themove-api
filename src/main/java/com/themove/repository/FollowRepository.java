package com.themove.repository;

import com.themove.entity.Follow;
import com.themove.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findByFollower(User follower);

    boolean existsByFollowerAndFollowing(User follower, User following);

    List<Follow> findAllByFollower(User follower);
}