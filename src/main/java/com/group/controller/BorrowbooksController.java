package com.group.controller;

import com.group.pojo.Borrowbooks;
import com.group.service.BookService;
import com.group.service.BorrowbooksService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

@Controller
@RequestMapping("brrowBook")
public class BorrowbooksController {
    @Autowired
    BorrowbooksService borrowbook;
    @RequestMapping("appraisalsUI")
    public String  appriaisals(HttpServletRequest request, ModelMap map){
        String username=(String)request.getSession().getAttribute("userName");
        List<Borrowbooks> borrowbooks= borrowbook.selectByUsername(username);
        map.addAttribute("borrowbookList",borrowbooks);
        return "book/appraisalsUI";

    }

    @RequestMapping("appraisal")
    @ResponseBody
    public int  appriaisals(Borrowbooks borrowbooks){
        return  borrowbook.updateById(borrowbooks);

    }

    @RequestMapping("selectapraisals")

    public String  selectapraisals(HttpServletRequest request, ModelMap map){
        String username=(String)request.getSession().getAttribute("userName");
        List<Borrowbooks> borrowbooks= borrowbook.selectByUsernameByappraisal(username);
        map.addAttribute("borrowbookList",borrowbooks);
        return "book/selectapraisals";

    }

    @RequestMapping("selectapraisals1")
    public String  selectapraisals1(HttpServletRequest request, ModelMap map){
        String username=(String)request.getSession().getAttribute("userName");
        List<Borrowbooks> borrowbooks= borrowbook.selectByUsernameByappraisal1();
        map.addAttribute("borrowbookList",borrowbooks);
        return "book/selectapraisals";

    }
    @RequestMapping("deleteById")
    public  String deeteByid(int id){
        borrowbook.deleteById(id);
        return "book/selectapraisals";
    }

    //²éÑ¯ËùÓÐ
    @RequestMapping("selectAllBoorowBooks.do")
    public String queryAllBoorowBooksMessage(ModelMap modelMap, Borrowbooks borrowbooks) {
        HashMap<String, Object> borrowbookMessage_list = borrowbook.queryAllBorrowBooks(borrowbooks);
        modelMap.addAttribute("borrowbookMessage_list", borrowbookMessage_list);
        return "book/borrowBooks";
    }


    @RequestMapping("selectMyBoorowBooks.do")
    public String selectMyBoorowBooksMessage(ModelMap modelMap,HttpServletRequest request) {
        Borrowbooks borrowbooks = new Borrowbooks();
        borrowbooks.setUserid((Integer) request.getSession().getAttribute("userId"));
        HashMap<String, Object> borrowbookMessage_list = borrowbook.queryAllBorrowBooks(borrowbooks);
        modelMap.addAttribute("borrowbookMessage_list", borrowbookMessage_list);
        return "book/myBorrowBooks";
    }


}
