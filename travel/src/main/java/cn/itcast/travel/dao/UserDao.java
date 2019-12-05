package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    User findUser(String username);

    void addUser(User luser);

    User findCode(String code);

    void updateStatus(String code);

    User findByUserAndPassword(User log_user);
}
