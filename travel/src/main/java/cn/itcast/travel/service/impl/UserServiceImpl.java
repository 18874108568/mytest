package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    UserDao dao = new UserDaoImpl();

    /**
     * 注册用户
     *
     * @param luser
     * @return
     */
    @Override
    public Boolean regist(User luser) {
        //先查询数据库有没有账户数据
        User u = dao.findUser(luser.getUsername());
            if(u!=null){
                //如果有数据，注册失败
                return false;
            }
        //如果没有数据，注册成功，并保存数据
        luser.setCode(UuidUtil.getUuid());
        luser.setStatus("N");
        dao.addUser(luser);


        //发送邮件编辑邮件正文
        String s="<a href=http://47.112.213.44/travel/user/active?code="+luser.getCode()+"'>点击激活【黑马旅游网】</a>";
        MailUtils.sendMail(luser.getEmail(),s,"激活邮件");
        return true;
    }

    /**
     * 激活邮箱
     * @param code
     * @return
     */
    @Override
    public Boolean active(String code) {
         User user=dao.findCode(code);
         if(user!=null){
             dao.updateStatus(code);
             return true;
         }else {
             return false;
         }
    }

    /**
     * 登录验证
     * @param log_user
     * @return
     */
    @Override
    public User login(User log_user) {
        //查找用户是否存在
        return dao.findByUserAndPassword(log_user);
    }
}
