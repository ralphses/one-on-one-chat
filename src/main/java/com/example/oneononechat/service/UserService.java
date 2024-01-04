package com.example.oneononechat.service;

import com.example.oneononechat.dao.UserDao;
import com.example.oneononechat.model.AppUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.oneononechat.enums.UserStatus.OFFLINE;
import static com.example.oneononechat.enums.UserStatus.ONLINE;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public  void saveUser(AppUser user) {
        user.setStatus(ONLINE);
        userDao.save(user);
    }

    public void disconnectUser(AppUser user) {
        userDao.findById(user.getId()).ifPresent(user1 -> user1.setStatus(OFFLINE));
    }

    public List<AppUser> findConnectedUsers() {
        return userDao.findAllByStatus(ONLINE);
    }
}
