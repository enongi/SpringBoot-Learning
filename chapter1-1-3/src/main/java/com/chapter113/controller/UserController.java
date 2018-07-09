package com.chapter113.controller;

import com.chapter113.entity.User;
import com.chapter113.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
//@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public ModelAndView insetUser(HttpServletRequest request, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        User user = new User();
        if(!password.equals(repassword)){
            user = userService.selectService(username,password);
        }else{
            session.setAttribute("errormsg","两次密码不一致！请重新输入！");
        }
        if (user == null){
            userService.insertService(username,password);
            modelAndView.setViewName("login");
            return modelAndView;
        }else{
            session.setAttribute("errormsg","账号已存在！请重新输入！");
        }
        return modelAndView;
    }

   /* @RequestMapping("/tologin")
    public String index() {
        return "/login";
    }*/
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username !=null && password != null){
            User user = userService.selectService(username,password);
            if (user != null){
                session.setAttribute("user",user);
                modelAndView.setViewName("detail");
                return modelAndView;
            }else{
                session.setAttribute("errormsg","账号或密码错误！请重新输入！");
            }
        }
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
