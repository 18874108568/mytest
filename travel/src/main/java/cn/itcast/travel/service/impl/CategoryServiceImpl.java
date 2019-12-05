package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService{
    private CategoryDao dao=new CategoryDaoImpl();
    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Category> findAll() {
        //获取Jedis连接对象来查缓存数据
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        List<Category> list=null;
        if(categorys==null || categorys.size()==0){
            //缓存没有数据，从数据库中查询数据
           list = dao.findAll();
            for (int i = 0; i < list.size(); i++) {
                //把数据库中的数据存入缓存
                jedis.zadd("category",list.get(i).getCid(),list.get(i).getCname());
            }
        }
        //如果缓存有数据
        list=new ArrayList<Category>();
        for (Tuple tuple : categorys) {
            Category c=new Category();
            c.setCname(tuple.getElement());
            c.setCid((int) tuple.getScore());
            list.add(c);
        }
        return list;
    }
}
