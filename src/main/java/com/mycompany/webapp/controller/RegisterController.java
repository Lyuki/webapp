/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webapp.controller;

import com.mycompany.webapp.dao.Register;
import com.mycompany.webapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author s1118541
 */
@Controller
@RequestMapping("register")
public class RegisterController {

  @Autowired
  Register gbEntryRepo;
 
  @RequestMapping(value = "register", method = RequestMethod.GET)
  public ModelAndView regForm() {
    return new ModelAndView("register", "command", new User()); //view,model
  }
  

  @RequestMapping(value = "reg", method = RequestMethod.POST)
  public View regHandle(User entry) {
    gbEntryRepo.create(entry);
    return new RedirectView("/", true);
  }
}
