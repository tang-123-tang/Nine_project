package com.group.dao;

import com.group.pojo.Borrowbooks;

import java.util.List;

public interface BorrowbooksMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Borrowbooks record);

    int insertSelective(Borrowbooks record);

    Borrowbooks selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Borrowbooks record);

    int updateByPrimaryKey(Borrowbooks record);

    List<Borrowbooks> selectByUsername(String username);
    List<Borrowbooks>  selectByUsernameByappraisal(String username);

    List<Borrowbooks> selectByUsernameByappraisal1();
    int select(Integer userid);

    Borrowbooks selectByid(Integer bookid);

    List<Borrowbooks> queryAllBorrowBooks(Borrowbooks borrowbooks);

    List<Borrowbooks> selectBtuseid(Integer userid);

    //��ҳ��ѯδ�黹
    List<Borrowbooks> selectByPage(int userid);

    //��������
    int returnAllBook(List<String> asList);

    //��ҳ��ѯ�Ի���
    List<Borrowbooks> selectAllNoReturnBookByPage(Borrowbooks borrowbooks);

    int updateById(Integer id);
    //����ɾ������ɾ����
    int deleteAllBook(List<String> asList);


}