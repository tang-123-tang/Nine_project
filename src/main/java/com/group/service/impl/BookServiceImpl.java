package com.group.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.dao.BookMapper;
import com.group.pojo.Book;
import com.group.pojo.Borrowbooks;
import com.group.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookMapper dao;
    public HashMap<String, Object> selectByPage(Book book) {
        PageHelper.startPage(book.getPage(), book.getRow());
        //2��ѯ�Զ����sql
        List<Book> list = (List<Book>) dao.select(book);
        //3 ת���ɷ�ҳ����
        PageInfo<Book> pageInfo = new PageInfo<Book>(list);
        //������������
        HashMap<String, Object> map = new HashMap<String, Object>();
        //��ǰҳ����
        map.put("list", pageInfo.getList());
        //������
        map.put("count", pageInfo.getTotal());
        //��һҳ
        map.put("prePage", pageInfo.getPrePage());
        //��һҳ
        map.put("nextPage", pageInfo.getNextPage());
        //��ҳ
        map.put("indexPage", pageInfo.getFirstPage());
        //βҳ
        map.put("endPage", pageInfo.getLastPage());
//        //ÿҳ��ʾ��
////        map.put("allPage", pageInfo.getPageSize());
        //��ҳ��
        map.put("pages",pageInfo.getPages());
        return map;
    }

    public Book selectByid(int bookid) {
        return dao.selectByid(bookid);
    }

    public int updateBiid(Book book) {
        return dao.updateByPrimaryKeySelective(book);
    }

    public int deleteByid(Integer bookid) {
        return dao.deleteByPrimaryKey(bookid);
    }

    public void update(Integer book) {
        dao.update(book);
    }

    public int del(List<String> list) {
        return dao.del(list);
    }

    @Override
    public int addBook(Book book) {
      return   dao.insert(book);
    }
}
