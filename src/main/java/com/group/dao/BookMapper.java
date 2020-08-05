package com.group.dao;

import com.group.pojo.Book;
import com.group.pojo.Borrowbooks;

import java.util.List;

public interface BookMapper {
    int deleteByPrimaryKey(Integer bookid);

    int insert(Book record);

    int insertSelective(Book record);

    Book selectByPrimaryKey(Integer bookid);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);
    List<Book> select(Book book);
    Book selectByid(Integer id);

    void update(Integer book);

    int del(List<String> list);
}