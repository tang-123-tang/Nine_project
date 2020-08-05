package com.group.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.dao.BookMapper;
import com.group.dao.BorrowbooksMapper;
import com.group.dao.UserMapper;
import com.group.pojo.Borrowbooks;
import com.group.service.BorrowbooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class BorrowbooksServiceImpl implements BorrowbooksService {
    @Autowired
    BorrowbooksMapper borrowbooksMapper;

    @Autowired
    UserMapper um;
    @Autowired
    BookMapper bm;
    @Override
    public List<Borrowbooks> selectByUsername(String username) {

        return     borrowbooksMapper.selectByUsername(username);
    }

    @Override
    public int updateById(Borrowbooks borrowbooks) {
        return borrowbooksMapper.updateByPrimaryKeySelective(borrowbooks);
    }

    @Override
    public List<Borrowbooks> selectByUsernameByappraisal(String username) {
        return borrowbooksMapper.selectByUsernameByappraisal( username) ;
    }

    @Override
    public List<Borrowbooks> selectByUsernameByappraisal1() {
        return borrowbooksMapper.selectByUsernameByappraisal1( ) ;
    }
    public int add(Borrowbooks borrowbooks) {
        return borrowbooksMapper.insertSelective(borrowbooks);
    }

    public int select(Integer userid) {
        return borrowbooksMapper.select(userid);
    }

    public Borrowbooks selectbyid(Integer bookid) {
        return borrowbooksMapper.selectByid(bookid);
    }

    @Override
    public int deleteById(int id) {
        return borrowbooksMapper.deleteByPrimaryKey(id);
    }

    public Borrowbooks selectByid(Integer bookid) {
        return borrowbooksMapper.selectByPrimaryKey(bookid);
    }

    public HashMap<String,Object> queryAllBorrowBooks(Borrowbooks borrowbooks) {
        //��ѯ���еĽ�����Ϣ
        /*��ҳ��ѯ���裺1�����÷�ҳ��ѯ��ҳ�룬ÿҳ��ʾ������
         * 2�����ʵ����������sql
         * 3��������ҳ����
         * 4����������ģ��
         */
        PageHelper.startPage(borrowbooks.getPage(),borrowbooks.getRow());
        List<Borrowbooks> borrowbook_list = borrowbooksMapper.queryAllBorrowBooks(borrowbooks);
        PageInfo<Borrowbooks> pageInfo = new PageInfo<Borrowbooks>(borrowbook_list);
        HashMap<String,Object> hashMap = new HashMap<String, Object>();
        //��ǰҳ����
        hashMap.put("list",pageInfo.getList());
        //��һҳ
        hashMap.put("nextPage",pageInfo.getNextPage());
        //��һҳ
        hashMap.put("prePage",pageInfo.getPrePage());
        //��ҳ
        hashMap.put("indexPage",pageInfo.getFirstPage());
        //βҳ
        hashMap.put("endPage",pageInfo.getLastPage());
        //��ҳ��
        hashMap.put("allPages",pageInfo.getPages());
        //��������
        hashMap.put("alltotals",pageInfo.getTotal());
        return hashMap;
    }
    public List<Borrowbooks> selectByuserid(Integer userid) {
        return borrowbooksMapper.selectBtuseid(userid);
    }

    public int updateBybookid(Borrowbooks borrowbooks) {
        return borrowbooksMapper.updateByPrimaryKeySelective(borrowbooks);
    }




    @Override
    public HashMap<String, Object> selectAllBooks(Borrowbooks s) {
        PageHelper.startPage(s.getPage(),s.getRow());
        //����sql
        List<Borrowbooks> borrowbooksList=borrowbooksMapper.selectByPage(s.getUserid());
        for (Borrowbooks b:borrowbooksList
        ) {
            System.out.println(b);
        }

        PageInfo<Borrowbooks> pageInfo=new PageInfo<Borrowbooks>(borrowbooksList);

        //������������
        HashMap<String,Object> map=new HashMap<String,Object>();
        //��ȡÿҳ����
        map.put("list",pageInfo.getList());
        //��ȡ������
        map.put("count",pageInfo.getTotal());
        //��һҳ
        map.put("prepage",pageInfo.getPrePage());
        //��һҳ
        map.put("nextpage",pageInfo.getNextPage());
        //��ҳ��ҳ
        map.put("pages",pageInfo.getPages());
        //��ҳ
        map.put("firstpage",pageInfo.getNavigateFirstPage());
        //βҳ
        map.put("endpage",pageInfo.getNavigateLastPage());
        //��ǰҳ
        map.put("thispage",s.getPage());

        return map;
    }

    @Override
    public int returnBook(Borrowbooks borrowbooks) {
        return borrowbooksMapper.updateByPrimaryKeySelective(borrowbooks);
    }

    @Override
    public int returnAllBook(List<String> asList) {
        return borrowbooksMapper.returnAllBook(asList);
    }

    @Override
    public HashMap<String, Object> selectAllNoReturnBook(Borrowbooks borrowbooks) {
        PageHelper.startPage(borrowbooks.getPage(),borrowbooks.getRow());
        //����sql
        List<Borrowbooks> borrowbooksList=borrowbooksMapper.selectAllNoReturnBookByPage(borrowbooks);
        for (Borrowbooks b:borrowbooksList
        ) {
            System.out.println(b);
        }

        PageInfo<Borrowbooks> pageInfo=new PageInfo<Borrowbooks>(borrowbooksList);

        //������������
        HashMap<String,Object> map=new HashMap<String,Object>();
        //��ȡÿҳ����
        map.put("list",pageInfo.getList());
        //��ȡ������
        map.put("count",pageInfo.getTotal());
        //��һҳ
        map.put("prepage",pageInfo.getPrePage());
        //��һҳ
        map.put("nextpage",pageInfo.getNextPage());
        //��ҳ��ҳ
        map.put("pages",pageInfo.getPages());
        //��ҳ
        map.put("firstpage",pageInfo.getNavigateFirstPage());
        //βҳ
        map.put("endpage",pageInfo.getNavigateLastPage());
        //��ǰҳ
        map.put("thispage",borrowbooks.getPage());

        return map;
    }

    @Override
    public int deleteBook(Borrowbooks borrowbooks) {
        return borrowbooksMapper.updateById(borrowbooks.getId());
    }

    @Override
    public int deleteAllBook(List<String> asList) {
        return borrowbooksMapper.deleteAllBook(asList);
    }

    @Override
    public int totaldown(Integer userid) {
        return 0;
    }

    @Override
    public int totaldowns(Integer count, Integer userid) {
        return 0;
    }

    @Override
    public int totalup(Integer bookid) {
        return 0;
    }

    @Override
    public int totalups(List<String> asList) {
        return 0;
    }
/*

    @Override
    public int totaldown(Integer userid) {
        return um.totaldown(userid);
    }

    @Override
    public int totaldowns(Integer count, Integer userid) {
        return um.totaldowns(count,userid);
    }

    @Override
    public int totalup(Integer bookid) {
        return bm.totalup(bookid);
    }

    @Override
    public int totalups(List<String> asList) {
        return bm.totalups(asList);
    }

*/


}
