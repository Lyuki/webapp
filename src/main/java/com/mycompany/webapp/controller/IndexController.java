package com.mycompany.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.View;

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
   
   @RequestMapping("createPoll")
    public String createPoll() {
        return "newPoll";
    }
    
    @RequestMapping("Lecture")
    public View Lecturetopics() {
        ModelMap model = new ModelMap();
        model.addAttribute("cate", "Lecture");
        return new RedirectView("/topic", true);
    }
    
    @RequestMapping("Lab")
    public View Labtopics() {
        //Topics topic = new Topics();
        //topic.setCategory("Lab");
        ModelMap model = new ModelMap();
        model.addAttribute("cate", "Lab");
        return new RedirectView("/topic", true);
    }
    
    @RequestMapping("Other")
    public View Othertopics() {
        //Topics topic = new Topics();
        //topic.setCategory("Other");
        ModelMap model = new ModelMap();
        model.addAttribute("cate", "Other");
        return new RedirectView("/topic", true);
    }
}

