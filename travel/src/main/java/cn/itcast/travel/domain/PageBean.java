package cn.itcast.travel.domain;

import java.util.List;

public class PageBean {
    private int totalConut; //总记录数
    private int totalPage;  //总页数
    private int currentPage; //当前页数
    private int pageSize;    //每页展示的条数
    private List<Route> list;  //每页展示的数据集合

    public int getTotalConut() {
        return totalConut;
    }

    public void setTotalConut(int totalConut) {
        this.totalConut = totalConut;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
