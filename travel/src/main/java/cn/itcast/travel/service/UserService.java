package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    /**
     * 注册用户
     * @param luser
     * @return
     */
    Boolean regist(User luser);

    /**
     * 激活
     * @param code
     * @return
     */
    Boolean active(String code);
    //登录验证
    User login(User log_user);
}
