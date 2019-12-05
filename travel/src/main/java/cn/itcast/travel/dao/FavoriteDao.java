package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

import java.util.List;

public interface FavoriteDao {
   Favorite findByRU(int rid, int uid);

    int findcount(int rid);

    void addFavorite(int rid, int uid);


    int queryConut(int uid);
}
