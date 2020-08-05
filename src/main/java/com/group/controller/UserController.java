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
        //交给业务层来进行 shiro认证
        String info = us.checkLogin(user,remember,response,request);
        return info;
    }
    //DefaultFilter //这个类里面定义所有shiro所有的过滤器
    @RequestMapping("success.do")
    public String success(){

        return "index";
    }
    @RequestMapping("de.do")
    public String de(){

        return "book/addbook";
    }
    //退出用户
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
        //要获取服务器某些并通过EL表达式显示在welcome.jsp
        return "welcome";
    }
    //注册
    @RequestMapping(value = "regest.do" ,produces = "application/text;charset=utf-8")
    @ResponseBody
    public String regest(User user,@RequestParam(value="yanz",required=false) int yanz,HttpServletRequest request){
         if(yanz!=((int)request.getSession().getAttribute("yanz")))
             return "验证码错误";
        if(user.getUsername()==null||user.getPassword()==null)
            return "密码/用户不能为空";
        boolean falg=us.insert(user);
if(falg)
        return "注册成功";

    return "注册失败，请重新注册";
    }

    @RequestMapping(value = "email.do" )
    @ResponseBody
    public boolean email(String email){

        return us.sendEmail(email);
    }
}
