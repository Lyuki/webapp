package com.mycompany.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import com.mycompany.webapp.model.Topics;

@Controller
public class IndexController {

   @RequestMapping("/")
    public String index() {
        return "index";
    }
    
    @RequestMapping("login")
    public String login() {
        return "login";
    }
    
    @RequestMapping("create")
    public String addUser() {
        return "addUser";
    }
    
    @RequestMapping("Lecture")
    public String Lecturetopics() {
        Topics topic = new Topics();
        topic.setCategory("Lecture");
        return "topic";
    }
    
    @RequestMapping("Lab")
    public String Labtopics() {
        Topics topic = new Topics();
        topic.setCategory("Lab");
        return "topic";
    }
    
    @RequestMapping("Other")
    public String Othertopics() {
        Topics topic = new Topics();
        topic.setCategory("Other");
        return "topic";
    }
}

