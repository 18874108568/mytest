package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate jt = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int queryConut(int cid, String rname) {
        // String sql="SELECT COUNT(*) FROM tab_route WHERE cid=?";
        String sql = "SELECT COUNT(*) FROM tab_route WHERE 1=1";
        StringBuilder sb = new StringBuilder(sql);
        List list = new ArrayList();
        if (cid != 0) {
            sb.append(" and cid = ? ");
            list.add(cid);
        }
        if (rname != null && rname.length() != 0 && !rname.equals("null")) {
            sb.append(" and rname like ? ");
            list.add("%" + rname + "%");
        }
        sql = sb.toString();
        return jt.queryForObject(sql, Integer.class, list.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        //String sql="SELECT * FROM tab_route WHERE cid=? limit ?,?";
        String sql = "SELECT * FROM tab_route WHERE 1=1";
        StringBuilder sb = new StringBuilder(sql);
        List list = new ArrayList();
        if (cid != 0) {
            sb.append(" and cid = ? ");
            list.add(cid);
        }
        if (rname != null && rname.length() != 0 && !rname.equals("null")) {
            sb.append(" and rname like ? ");
            list.add("%" + rname + "%");
        }
        sb.append(" limit ?,?");
        list.add(start);
        list.add(pageSize);
        sql = sb.toString();
        return jt.query(sql, new BeanPropertyRowMapper<Route>(Route.class), list.toArray());
    }

    /**
     * 查询某一条旅游信息
     *
     * @param rid
     * @return
     */
    @Override
    public Route findOne(String rid) {
        Route route = null;
        try {
            String sql = "SELECT * FROM tab_route WHERE rid=?";
            route = jt.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return route;
    }

    @Override
    public List<Route> finduidAll(int uid, int start, int pageSize) {
        List<Route> list = null;
        try {
            String sql="SELECT * FROM tab_route WHERE rid IN (SELECT rid FROM tab_favorite WHERE uid=?) limit ?,?";
            list = jt.query(sql, new BeanPropertyRowMapper<Route>(Route.class), uid,start,pageSize);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void updata(int rid, int count) {
        String sql="UPDATE tab_route SET count=? where rid=?";
        jt.update(sql,count,rid);
    }

    @Override
    public int favoriteConut(String mname, String dmo, String gmo) {
        String sql="SELECT COUNT(*) FROM tab_route WHERE COUNT!=0";
        StringBuilder sb=new StringBuilder(sql);
        List list = new ArrayList();
        if(mname!=null && mname.length()>0 && !"null".equals(mname) && !"undefined".equals(mname)){
            sb.append(" and rname like ? ");
            list.add("%"+mname+"%");
        }
        if(dmo!=null && dmo.length()>0 && !"null".equals(dmo) && !"undefined".equals(mname)){
            sb.append(" and price> ? ");
            list.add(dmo);
        }
        if(gmo!=null && gmo.length()>0 && !"null".equals(gmo) && !"undefined".equals(mname)){
            sb.append(" and price< ? ");
            list.add(gmo);
        }
        sql=sb.toString();
        return jt.queryForObject(sql,Integer.class,list.toArray());
    }

    @Override
    public List<Route> rankByPage(int start, int pageSize, String mname, String dmo, String gmo, String cid) {
        //String sql="SELECT * FROM tab_route where count>0 ORDER by count desc limit ?,?";
        String sql="SELECT * FROM tab_route where count>0";
        StringBuilder sb=new StringBuilder(sql);
        List list = new ArrayList();
        if(mname!=null && mname.length()>0 && !"null".equals(mname) && !"undefined".equals(mname)){
            sb.append(" and rname like ? ");
            list.add("%"+mname+"%");
        }
        if(dmo!=null && dmo.length()>0 && !"null".equals(dmo) && !"undefined".equals(mname)){
            sb.append(" and price> ? ");
            list.add(dmo);
        }
        if(gmo!=null && gmo.length()>0 && !"null".equals(gmo) && !"undefined".equals(mname)){
            sb.append(" and price< ? ");
            list.add(gmo);
        }
        if(cid!=null && cid.length()>0 && !"null".equals(cid) && !"undefined".equals(mname)){
            sb.append(" and cid=? ");
            list.add(cid);
        }
        sb.append(" ORDER by count desc limit ?,?");
        list.add(start);
        list.add(pageSize);
        sql=sb.toString();
        return jt.query(sql,new BeanPropertyRowMapper<Route>(Route.class),list.toArray());
    }

    @Override
    public List<Route> zhuti() {
        String sql="SELECT * FROM tab_route ORDER by rdate ASC";
        return jt.query(sql,new BeanPropertyRowMapper<Route>(Route.class));
    }
}
