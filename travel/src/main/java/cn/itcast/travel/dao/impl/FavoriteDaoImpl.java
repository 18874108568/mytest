package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate jt=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Favorite findByRU(int rid, int uid) {
        Favorite f=null;
        try {
            String sql="SELECT * FROM tab_favorite WHERE rid=? and uid=?";
           f = jt.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
           return f;
        } catch (DataAccessException e) {

            return f;
        }
    }

    @Override
    public int findcount(int rid) {
        int count=0;
        try {
            String sql="SELECT COUNT(*) FROM tab_favorite WHERE rid=?";
            count = jt.queryForObject(sql, Integer.class, rid);
            return count;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public void addFavorite(int rid, int uid) {
        String sql="INSERT into tab_favorite VALUES(?,?,?)";
        jt.update(sql,rid,new Date(),uid);
    }

    @Override
    public int queryConut(int uid) {
        String sql="SELECT COUNT(*) FROM tab_favorite WHERE uid=?";
        return jt.queryForObject(sql, Integer.class, uid);
    }
}
