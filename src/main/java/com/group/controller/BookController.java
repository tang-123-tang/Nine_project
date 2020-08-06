package com.group.controller;

import com.group.dao.EvaluationMapper;
import com.group.pojo.Book;
import com.group.pojo.Borrowbooks;

import com.group.pojo.Evaluation;
import com.group.service.BookService;
import com.group.service.BorrowbooksService;

import com.group.service.EvaluationService;
import com.group.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("book")
public class BookController {
    @Autowired
    BookService service;
    @Autowired
    BorrowbooksService borrowbooksService;
    @Autowired
    UserService userService;
@Autowired
EvaluationService ev;
    @RequestMapping(value = "one.do")
    public String one(MultipartFile  file ,MultipartFile  filee,Book book){
        System.out.println(file+"dddd");
        System.out.println(filee+"dddsdsdsd");
        System.out.println(book);
        return "ond";
    }
    @RequestMapping(value = "one1.do")
    public String one1(){

        return "ond";
    }
    //ͼ��ҳ��
    @RequestMapping(value = "Library.do")
    public String Library(Book book, ModelMap map){
        HashMap<String, Object> listMap = service.selectByPage(book);
        map.addAttribute("data", listMap);
        map.addAttribute("bookname", book.getBookname());
        map.addAttribute("author", book.getAuthor());
        map.addAttribute("press", book.getPress());
        map.addAttribute("price", book.getPrice());
        map.addAttribute("bookid", book.getBookid());
        return "book/Library";
    }
    @RequestMapping(value = "Library1.do")
    public String Library1(Book book, ModelMap map){
        HashMap<String, Object> listMap = service.selectByPage(book);
        map.addAttribute("data", listMap);
        map.addAttribute("bookname", book.getBookname());
        map.addAttribute("author", book.getAuthor());
        map.addAttribute("press", book.getPress());
        map.addAttribute("price", book.getPrice());
        map.addAttribute("bookid", book.getBookid());
        return "book/Library1";
    }
    //�޸���תҳ��
    @RequestMapping(value = "updatePage.do")
    public String updatePage(Book book, ModelMap map){
        if(book!=null) {
            Book listMap = service.selectByid(book.getBookid());
            map.addAttribute("list", listMap);
        }
        return "book/updatePage";
    }
    //�޸�ͼ������
    @RequestMapping(value = "update.do")
    public String update(Book book, ModelMap map){
        int a=  service.updateBiid(book);
        return "redirect:Library.do";
    }
    //ɾ��ͼ������
    @RequestMapping(value = "delete.do")
    public String delete(Book book){
        Integer bookid = book.getBookid();
        int a=  service.deleteByid(bookid);
        return "redirect:Library.do";
    }
    //���İ�ť
    @RequestMapping(value ="borrow.ajax",produces = "application/text;charset=utf-8")
    @ResponseBody
    public String borrow(Borrowbooks borrowbooks, HttpServletRequest request){
        Integer userid = (Integer) request.getSession().getAttribute("userId");
        Integer bookid = borrowbooks.getBookid();
        borrowbooks.setUserid(userid);
        System.out.println(userid);
        Book book = service.selectByid(bookid);
        int a =borrowbooksService.select(userid);
        System.out.println(a);
        Borrowbooks s=borrowbooksService.selectbyid(userid,bookid);

        if (s!=null){
            return "���Ѿ�����Ȿ���ˣ�";
        }else if (book.getSum()==0){
            return "����̫�����˱�����ˣ�����";
        }else if (a>=3){
            return "����ĵ������Ѿ������˸�����߼�¼�ˣ���";
        }else {
            borrowbooksService.add(borrowbooks);
            service.update(bookid);
            return "���ĳɹ�";
        }
    }
    //ͼ������
    @RequestMapping(value = "view.do")
    public String view(Book book){
        System.out.println("book = " + book);
        return "book/xiyouji";
    }
    //����ɾ��
    @RequestMapping(value = "delAjax.ajax",produces = "application/text;charset=utf-8")
    @ResponseBody
    public String view(@RequestParam("id") String id ){

        //���ַ���ת������
        List<String> list = Arrays.asList(id.split(","));
        for (String s : list) {
            String m="";
            for (int i = 2; i < s.length()-2; i++) {
                m+=s.charAt(i);
            }
            int bookid=Integer.parseInt(m);
            service.deleteByid(bookid);
        }

        return "ɾ���ɹ�";
    }
    //���ͼ����תҳ��
    @RequestMapping(value = "addpage.do")
    public String addpa(){
        return "book/addbook";
    }
    //���ͼ����תҳ��
    @RequestMapping(value = "/book/addbook.do")
    public String addbook(){
        System.out.println("���");

        return "��ӳɹ�";
    }
    //�û�����
    @RequestMapping(value = "evaluation.ajax",produces = "application/text;charset=utf-8")
    @ResponseBody
    public String evaluation(Evaluation evaluation ,HttpServletRequest request){
        Integer userid=(Integer) request.getSession().getAttribute("userId");
        String username=(String ) request.getSession().getAttribute("userName");
        evaluation.setUserid(userid);
        evaluation.setUsername(username);
        int c= ev.insrte(evaluation);
        return "�ύ�ɹ�";
    }
    //�û��鿴����
    @RequestMapping(value = "evaluationd.do")
    public String evaup(ModelMap map ,HttpServletRequest request){
        Integer userid=(Integer) request.getSession().getAttribute("userId");
        List<Evaluation> evaluations= ev.selectByuserid(userid);
        for (Evaluation e : evaluations) {
            // �������ڸ�ʽ
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = simpleDateFormat.format( e.getData());
            map.addAttribute("data",format);
        }
        map.addAttribute("dapt",evaluations);
        return "book/evaluationform";
    }
    //����Ա�鿴����
    @RequestMapping(value = "evaluationdd.do")
    public String evaudp(ModelMap map){
        List<Evaluation> evaluations= ev.selectBy();
        for (Evaluation e : evaluations) {
            // �������ڸ�ʽ
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = simpleDateFormat.format( e.getData());
            map.addAttribute("data",format);
        }
        map.addAttribute("dapt",evaluations);
        return "book/evaluationform";
    }
    @RequestMapping(value = "evel.do")
    public String wee(){
        return "book/evaluation";
    }
    //ͼ����ĳ�ʱҳ��
    @RequestMapping(value = "evaluationform.do")
    public String evaluationform(ModelMap map,HttpServletRequest request) throws ParseException, ParseException {
        Integer userid=(Integer)request.getSession().getAttribute("userId");
        List<Borrowbooks> borrowbooks= borrowbooksService.selectByuserid(userid);
        List<Map> list=new ArrayList<Map>();
        for (Borrowbooks e : borrowbooks) {
            // �������ڸ�ʽ
            //���������ڼ��������
            Map<String,Object> amp=new HashMap<String, Object>();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String dateString = format.format(date);
            String datastring = format.format(e.getDate());
            Date date1 = format.parse(dateString);
            Date date2 = format.parse(datastring);
            int a = (int) ((date1.getTime() - date2.getTime()) / (1000*3600*24));
            int i=0;
            if (a>=7){
                if (i==0) {
                    amp.put("info","����ͼ����ĳ�ʱ�ˣ�����������Ӧ������");
                    i++;
                }
                //��ȡͼ��
                Book book1 = service.selectByid(e.getBookid());
                //��ȡ����ʱ��
                String dff = format.format( e.getDate());
                //��ͼ��id
                amp.put("bookid",e.getBookid());
                //���û���
                amp.put("username",request.getSession().getAttribute("userName"));
                //��ͼ����
                amp.put("bookname",book1.getBookname());
                //������id
                amp.put("id",e.getId());
                //�����ʱ��
                amp.put("date",dff);
                list.add(amp);
            }
        }
        map.addAttribute("list",list);
        return "book/borrowingform";
    }
    //����
    @RequestMapping("huanshu.do")
    public String huanshu(Borrowbooks borrowbooks){
        borrowbooks.setState(0);
        int a= borrowbooksService.updateBybookid(borrowbooks);
        return "redirect:evaluationform.do";
    }
    //ͼ������
    @RequestMapping("xujie.do")
    public String xujie(Borrowbooks borrowbooks){
        Date date=new Date();
        borrowbooks.setDate(date);
        int a= borrowbooksService.updateBybookid(borrowbooks);
        return "redirect:evaluationform.do";
    }

    /*�����*/
    @RequestMapping("bookList.do")
    public String bookList(){
        return "book/addBook";
    }

    @RequestMapping("addBook.do")
    @ResponseBody
    public String addBook( MultipartFile imgFile, MultipartFile document,Book book,HttpServletRequest request){
        System.out.println("00000000"+imgFile.getSize());
        System.out.println("00000000"+document.getSize());
        System.out.println("11111111"+book);
        //��ȡ��������ַ
        String realpath = request.getSession().getServletContext().getRealPath("/upload");
        //Book book=new Book();
        String img="",document0="";
        try{
//�ж�file���鲻��Ϊ�ղ��ҳ��ȴ���0
            if (document != null && document.getSize()> 0) {
                //ѭ����ȡfile�����е��ļ�

                    //�����ļ�
                    document0 = saveFile(document, realpath);

            }
            if (imgFile != null && imgFile.getSize()> 0) {
                //ѭ����ȡfile�����е��ļ�

                    //�����ļ�
                    img = saveFile(imgFile, realpath);

            }

            book.setPicturepath(img);
            book.setBookpath(document0);

            service.addBook(book);
            return "��ӳɹ�";
        }catch (Exception e){
        }
        return "null";
    }

    //�����ļ�
    private String saveFile(MultipartFile file, String path) {
        // �ж��ļ��Ƿ�Ϊ��
        String savePath;
        if (!file.isEmpty()) {
            try {
                File filepath = new File(path);
                if (!filepath.exists())
                    filepath.mkdirs();
                // �ļ�����·��
                String se=file.getOriginalFilename();

                savePath = path +"/"+se;
                // ת���ļ�
                System.out.println(savePath);
                file.transferTo(new File(savePath));
                System.out.println("/upload/"+se);
                return "/upload/"+se;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
