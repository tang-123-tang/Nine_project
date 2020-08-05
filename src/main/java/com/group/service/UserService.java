package com.group.service;

import com.group.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
    String checkLogin(User user, String remember, HttpServletResponse response, HttpServletRequest request);

    User queryCookie(HttpServletRequest request);

    boolean insert(User user);

    boolean sendEmail(String email);
}
