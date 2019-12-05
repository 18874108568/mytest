package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao{
    private JdbcTemplate jt=new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询账号是否存在
     * @param
     * @return
     */
    @Override
    public User findUser(String username) {
        User user=null;
        try {
            String sql="SELECT * FROM tab_user where username=?";
            user = jt.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
            return user;
        } catch (DataAccessException e) {
        }
        return user;
    }

    @Override
    public void addUser(User luser) {
        String sql="INSERT into tab_user(username,password,email,name,telephone,sex,birthday,status,code) VALUES (?,?,?,?,?,?,?,?,?)";
        jt.update(sql,luser.getUsername(),luser.getPassword(),luser.getEmail(),
                luser.getName(),luser.getTelephone(),luser.getSex(),
                luser.getBirthday(),luser.getStatus(),luser.getCode());
    }

    @Override
    public User findCode(String code) {
        User user=null;
        try {
            String sql="SELECT * from tab_user where code=?";
           user = jt.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {

        }
        return user;
    }

    @Override
    public void updateStatus(String code) {
        String sql="UPDATE tab_user SET status='Y' where code=?";
        jt.update(sql,code);
    }
    //查找登录用户账号密码和激活状态
    @Override
    public User findByUserAndPassword(User log_user) {
        User user=null;
        try {
            String sql="SELECT * FROM tab_user where username=? and password=?";
            user = jt.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), log_user.getUsername(), log_user.getPassword());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }


}
