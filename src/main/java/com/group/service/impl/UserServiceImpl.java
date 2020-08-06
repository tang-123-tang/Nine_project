package com.group.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.dao.UserMapper;
import com.group.pojo.User;
import com.group.service.UserService;
import com.group.utils.GetIp;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    //ע���ʼ����Ͷ���
    @Autowired
    private JavaMailSender mailSender;
@Autowired
    GetIp getIp;
    @Override

    public String checkLogin(User p, String remember, HttpServletResponse response, HttpServletRequest request) {
        //1.�õ���ǰ�û�
        Subject s = SecurityUtils.getSubject();

        //2.�жϵ�ǰ�û��Ƿ���֤��������ش���
        if(!s.isAuthenticated()){
            //UsernamePasswordToken ������  �Ժ��ѱ����������˺������shiro����ݺ�ƾ֤�ȶ�
            UsernamePasswordToken upt = new UsernamePasswordToken(p.getUsername(),p.getPassword());
            upt.setRememberMe(true);
            try {

                //������֤(��Ϊ����д���Զ����realm�࣬���Ի��Զ���realm������ȥ��֤)
                s.login(upt);
                //��¼�ɹ�
                //�����û�����session
            //�����û�������ҳ��Դ��session
                User user = new User();
                user = userMapper.queryPersonByPersonName(p.getUsername());
                s.getSession().setAttribute("userinfo", user);

                s.getSession().setAttribute("userName",p.getUsername());
//��ȡ��ҳ��Ϣ
                HashMap<String, Object> indexinfo = new HashMap<String, Object>();
                //��ȡip
                Object ip = getIp.getLocalHostLANAddress();
                //��ȡ��ǰʱ��
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = simpleDateFormat.format(new Date());
                //��ȡ�˻�
                String username = p.getUsername();
                //����
                String name = "";
                if (userMapper.queryPersonByPersonName(p.getUsername()).getSex().equals("��")) {
                    name = "Mr." + p.getUsername();
                } else {
                    name = "Miss." + p.getUsername();
                }
                //��ȡ����������
                String servicename = "D:/tomcat/apache-tomcat-8.0.32";
                //��ȡ������ip
                String serviceip = request.getSession().getServletContext().getRealPath("/");
                //��ȡ��������
                // ��ȡ ServletContext ����
                ServletContext context = request.getSession().getServletContext();
                int online=(Integer) context.getAttribute("onLineNum")+1;
                context.setAttribute("onLineNum",online);

                indexinfo.put("ip", ip);
                indexinfo.put("date", date);
                indexinfo.put("username", username);
                indexinfo.put("name", name);
                indexinfo.put("servicename", servicename);
                indexinfo.put("serviceip", serviceip);

                s.getSession().setAttribute("indexinfo", indexinfo);




                Set<String> roles = new HashSet<>();
                //��ѯ���ݿ�õ��û���Ȩ��
                String role = userMapper.queryPersonByPersonName(p.getUsername()).getRole();
                    roles.add(role);
                s.getSession().setAttribute("roles",roles);


                //�жϸ�ѡ���״̬
                if(remember.equals("YES")){
                    //1.����cookie  Servlet  cookie��ʹ��
                   /* Cookie c = new Cookie("USERNAME",p.getPersonName());
                    Cookie c2 = new Cookie("PASSWORD",p.getPersonPassword());
                    //2.����cookie��ʱ��
                    c.setMaxAge(30*24*60*60);
                    c2.setMaxAge(30*24*60*60);
                    //3.��cookie��д�������
                    response.addCookie(c);
                    response.addCookie(c2);*/

                    //��shiro  cookie��ʹ��
                    //1.����SimpleCookie
                    SimpleCookie simpleCookie1 = new SimpleCookie();
                    //2,��cookie��ֵ
                    simpleCookie1.setName("USERNAME");
                    simpleCookie1.setValue(p.getUsername());
                    simpleCookie1.setMaxAge(30*24*60*60);
                    //4.��д�������
                    simpleCookie1.saveTo(request,response);
                    SimpleCookie simpleCookie = new SimpleCookie();
                    simpleCookie.setName("PASSWORD");
                    simpleCookie.setValue(p.getPassword()+p.getUsername());
                    //3.����cookieʱ��
                    simpleCookie.setMaxAge(30*24*60*60);
                    //4.��д�������
                    simpleCookie.saveTo(request,response);
                }else {
                    Cookie[] cookies = request.getCookies();
                    //System.out.println(cookies);
                    if(cookies!=null){
                        for (Cookie c: cookies ) {
                            if(c.getName().equals("USERNAME")){
                                //System.out.println(222);
                                //servlet ���ɾ��Cookie ,��ʱ������Ϊ0 �����ظ������
                                /*c.setMaxAge(0);
                                response.addCookie(c);*/
                                SimpleCookie sc = new SimpleCookie();
                                sc.setName("USERNAME");
                                sc.setValue("");
                                sc.setMaxAge(30);
                                sc.saveTo(request, response);
                            }
                            if(c.getName().equals("PASSWORD")){
                                SimpleCookie sc = new SimpleCookie();
                                sc.setName("PASSWORD");
                                sc.setValue("");
                                sc.setMaxAge(30);
                                sc.saveTo(request, response);
                            }
                        }
                    }
                }
                return "success";
            }catch (AuthenticationException e){
                //��¼ʧ��
                return "ERROR";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //��ѯ�Ƿ�������Ҫ��cookie
    @Override
    public User queryCookie(HttpServletRequest request) {

        User p = new User();
        p.setUsername("");
        //�õ����е�cookie
        // System.out.println(5555);
        Cookie[] cookies = request.getCookies();
        System.out.println(cookies);
        //System.out.println(cookies);
        if(cookies!=null){
            String se="";
            for (Cookie c: cookies ) {
                if(c.getName().equals("USERNAME")){
                    p.setUsername(c.getValue());
                }
                if(c.getName().equals("PASSWORD")){
                  se=  c.getValue();

                }

            }
            p.setPassword(se.replace(p.getUsername(),""));
            return p;
        }

        p.setUsername("");
        return p;
    }

    @Override
    public boolean insert(User user) {
        //���ܲ��Դ���
        //���ü��ܷ�ʽ
        String algorithmName="MD5";
        //���ô����ܵ�ԭ����
        Object source=user.getPassword();
        //���ü��η�ʽ(һ����˵�������û���������)
        Object salt= ByteSource.Util.bytes(user.getUsername());
        //���ܴ���
        int hashIterations=1024;
        SimpleHash newPassword=new SimpleHash(algorithmName, source, salt, hashIterations);

        user.setPassword(newPassword.toString());
        if(userMapper.queryPersonByPersonName(user.getUsername())!=null){
            return  false;
        }
        if(userMapper.insertSelective(user)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean sendEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        //���巢�����ʼ���ַ
        message.setFrom(email);
        //����������ʼ���ַ
        message.setTo(email);
        //�����ʼ����⣬�����ʼ�����
        message.setSubject("ͼ��ע����֤�룺");
        //�����ʼ�����
        int x = (int) (Math.random() * 1000);
        message.setText("��ȷʵ�Ƿ��Ǳ��ˣ������ȷ" + x);
           Subject subject=SecurityUtils.getSubject();
           subject.getSession().setAttribute("yanz",x);
        try {
            //�����ʼ�
            mailSender.send(message);
            System.out.println("���ͳɹ�");
            return true;
        } catch (Exception e) {
            System.out.println("����ʧ��");
            e.printStackTrace();
        }
     return false;



    }


    //��ѯ�����û�
    @Override
    public HashMap<String, Object> queryAllUser(User user) {
        //1.����ÿҳ�Ĳ�ѯҳ�룬ÿҳ��ʾ������
        PageHelper.startPage(user.getPage(),user.getRow());
        //2.��ѯ�Զ���sql
        List<User> list = userMapper.selectByPage(user);
        //3.ת���ɷ�ҳ����
        PageInfo<User> pageInfo = new PageInfo<User>(list);

        //������������
        HashMap<String, Object> map = new HashMap<String, Object>();
        //�����
        map.put("list",pageInfo.getList());
        //������
        map.put("count",pageInfo.getTotal());
        //��ȡ��һҳ
        map.put("prePage",pageInfo.getPrePage());
        //��ȡ��һҳ
        map.put("nextPage",pageInfo.getNextPage());
        //��ҳ
        map.put("indexPage",pageInfo.getFirstPage());
        //ĩҳ
        map.put("endPage",pageInfo.getLastPage());

        map.put("allPage",pageInfo.getPageSize());

        return map;
    }

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public int updateByPrimaryKeySelective(User user) {

        //���ܲ��Դ���
        //���ü��ܷ�ʽ
        String algorithmName="MD5";
        //���ô����ܵ�ԭ����
        Object source=user.getPassword();
        //���ü��η�ʽ(һ����˵�������û���������)
        Object salt= ByteSource.Util.bytes(user.getUsername());
        //���ܴ���
        int hashIterations=1024;
        SimpleHash newPassword=new SimpleHash(algorithmName, source, salt, hashIterations);

        user.setPassword(newPassword.toString());
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int updateByPrimaryKey(User user) {
        //���ܲ��Դ���
        //���ü��ܷ�ʽ
        String algorithmName="MD5";
        //���ô����ܵ�ԭ����
        Object source=user.getPassword();
        //���ü��η�ʽ(һ����˵�������û���������)
        Object salt= ByteSource.Util.bytes(user.getUsername());
        //���ܴ���
        int hashIterations=1024;
        SimpleHash newPassword=new SimpleHash(algorithmName, source, salt, hashIterations);

        user.setPassword(newPassword.toString());
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public int delete(int userid) {
        return userMapper.deleteByPrimaryKey(userid);
    }

}
