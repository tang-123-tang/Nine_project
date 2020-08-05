package com.group.controller;

import com.group.pojo.User;
import com.group.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService us;
  @RequestMapping("login")
  public  String login(){
      return "login";
  }
    @RequestMapping("checkLogin.do")
    @ResponseBody
    public String checkLogin(User user, String remember, HttpServletResponse response, HttpServletRequest request){
        //System.out.println(remember);
        System.out.println(remember);
        //����ҵ��������� shiro��֤
        String info = us.checkLogin(user,remember,response,request);
        return info;
    }
    //DefaultFilter //��������涨������shiro���еĹ�����
    @RequestMapping("success.do")
    public String success(){

        return "index";
    }
    @RequestMapping("de.do")
    public String de(){

        return "book/addbook";
    }
    //�˳��û�
    @RequestMapping(value = "logout.do",produces = "application/json;charset=utf-8")
    @ResponseBody
    public User logout(HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        User p = us.queryCookie(request);
        System.out.println(p+"   persond");
        return p;
    }



    @RequestMapping("showWelcome.do")
    public String showWelcome(){
        //Ҫ��ȡ������ĳЩ��ͨ��EL���ʽ��ʾ��welcome.jsp
        return "welcome";
    }
    //ע��
    @RequestMapping(value = "regest.do" ,produces = "application/text;charset=utf-8")
    @ResponseBody
    public String regest(User user,@RequestParam(value="yanz",required=false) int yanz,HttpServletRequest request){
         if(yanz!=((int)request.getSession().getAttribute("yanz")))
             return "��֤�����";
        if(user.getUsername()==null||user.getPassword()==null)
            return "����/�û�����Ϊ��";
        boolean falg=us.insert(user);
if(falg)
        return "ע��ɹ�";

    return "ע��ʧ�ܣ�������ע��";
    }

    @RequestMapping(value = "email.do" )
    @ResponseBody
    public boolean email(String email){

        return us.sendEmail(email);
    }
}
