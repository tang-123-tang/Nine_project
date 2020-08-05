package com.group.service;

import com.group.pojo.Book;
import com.group.pojo.Borrowbooks;

import java.util.HashMap;
import java.util.List;

public interface BookService {
    HashMap<String, Object> selectByPage(Book book);

    Book selectByid(int bookid);
    int updateBiid(Book book);

    int deleteByid(Integer bookid);

    void update(Integer book);

    int del(List<String> list);
}
