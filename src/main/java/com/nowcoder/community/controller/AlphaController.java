package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello Spring Boot.";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getData() {
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) {
        //请求数据
        //获取请求方式
        System.out.println(request.getMethod());
        //请求路径
        System.out.println(request.getServletPath());
        //请求行
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ":" + value);
        }
        //获取请求参数：这个是在访问网站时自己加上的参数
        System.out.println(request.getParameter("code"));
        System.out.println(request.getParameter("name"));

        //返回响应数据
        //设置返回数据的类型为网页类型的文本，并设置字符集格式为utf-8来让这个网页支持中文。
        response.setContentType("text/html;charset=utf-8");
        try (PrintWriter writer = response.getWriter();) {
            //获取输出流
            //打印网页
            writer.write("<h1>牛客网</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //GET请求获取参数的方式一：
    //  /students?current=1&limit=20
    // 指明method为GET之后，就只能处理GET请求了。请求方式还有POST。
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    //GET请求获取参数的方式二：
    // 路径  /student/123，直接把参数添加到路径中，作为路径的一部分。
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody()
    public String getStudent(@PathVariable("id") int id) {
        System.out.println(id);
        return "a student";
    }

    //POST请求：浏览器向服务器提交数据
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    //响应HTML数据，方式一：
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    // 根据响应的底层原理，我们在响应是返回的是 Model + View 给前端控制器，
    // 之后前端控制器再将 Model 中的数据填入 View 中，返回给浏览器一个动态
    // 的网页。 其中：因为是返回一个网页，因此不用加上 @ResponseBody；
    // 将 View 存在 templates 目录下。
    public ModelAndView getTeacher() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "张三");
        mav.addObject("age", 30);
        // 指定模板的位置,不用加.html后缀，因为默认了是html文件了
        mav.setViewName("/demo/view");
        return mav;
    }

    //响应HTML数据，方式二
    @RequestMapping(path = "/school", method = RequestMethod.GET)
    // 如果没有加 @ResponseBody, 则返回的是一个网页, 如果返回值的类型
    // 是String类型，则认为返回的是视图的路径，也不用加上.html后缀
    public String getSchool(Model model) {
        // model对象不用自己创建，前端控制器调用方法时，会自动实例化传给方法
        model.addAttribute("name", "北京大学");
        model.addAttribute("age", 80);
        return "/demo/view";
    }

    //响应JSON数据（一般用在异步请求的情况，异步请求举例：B站的注册）
    // 异步请求：当前网页不刷新，但是访问了浏览器。
    // Java对象 -> JSON字符串  -> JS对象, 即 Java对象不能直接转为JS对象，我们
    // 可以先将Java对象转换为JSON字符串，最后再将其转换为JS对象。

    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    // 如果向浏览器返回JSON格式，则需要加上 @ResponseBody 注解, 因为不加此注解
    // 认为返回的是html文件
    @ResponseBody
    // 虽然返回值是Map类型，但是会自动转换为JSON格式的文件
    public Map<String, Object> getEmp() {
        Map<String, Object> emp = new HashMap<String, Object>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        return emp;
    }

    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    // 如果向浏览器返回JSON格式，则需要加上 @ResponseBody 注解, 因为不加此注解
    // 认为返回的是html文件
    @ResponseBody
    // 虽然返回值是Map类型，但是会自动转换为JSON格式的文件
    public List<Map<String, Object>> getEmps() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> emp = new HashMap<String, Object>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        list.add(emp);

        emp = new HashMap<String, Object>();
        emp.put("name", "李四");
        emp.put("age", 24);
        emp.put("salary", 9000.00);
        list.add(emp);

        emp = new HashMap<String, Object>();
        emp.put("name", "王五");
        emp.put("age", 25);
        emp.put("salary", 10000.00);
        list.add(emp);

        return list;
    }

    // cookie示例
    @RequestMapping(path = "/cookie/set", method = RequestMethod.GET)
    // 以json的格式返回响应
    @ResponseBody
    // 需要将cookie返回给浏览器，我们可以将其存在一个response对象中
    public String setCookie(HttpServletResponse response) {
        // 创建cookie
        // CommunityUtil.generateUUID()是自己实现的，用来随机获取一个字符串的函数。
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
        // 设置cookie生效的范围
        cookie.setPath("/community/alpha");
        // 设置cookie的生存时间
        cookie.setMaxAge(60 * 10);
        // 发送cookie
        response.addCookie(cookie);

        return "set cookie";
    }

    @RequestMapping(path = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    // @CookieValue("code") String code 注解：用于获取来自浏览器请求中的,
    // name为 "code" 的cookie，所对应的值。
    public String getCookie(@CookieValue("code") String code) {
        System.out.println(code);
        return "get cookie";
    }

    // session示例
    // session存在服务器端，cookie存在浏览器端
    @RequestMapping(path = "/session/set", method = RequestMethod.GET)
    @ResponseBody
    // session对象只需要声明之后浏览器就会将其自动注入
    // 手动创建session,并且设置，服务器会默认创建session
    public String setSession(HttpSession session) {
        // 向session里面添加数据
        session.setAttribute("id", 1);
        session.setAttribute("name", "Test");
        return "set session";
    }

    @RequestMapping(path = "/session/get", method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session) {
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get session";
    }

}
