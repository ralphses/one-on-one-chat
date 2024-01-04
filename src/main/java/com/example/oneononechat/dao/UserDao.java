package com.example.oneononechat.dao;

import com.example.oneononechat.enums.UserStatus;
import com.example.oneononechat.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<AppUser, Long> {

    List<AppUser> findAllByStatus(UserStatus status);
}
