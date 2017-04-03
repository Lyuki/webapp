/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webapp.controller;

import com.mycompany.webapp.dao.UserRepository;
import com.mycompany.webapp.model.AllUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("register")
public class RegisterController {

  @Autowired
  UserRepository gbEntryRepo;
 
  @RequestMapping(value = "register", method = RequestMethod.GET)
  public ModelAndView regForm() {
    return new ModelAndView("register", "command", new AllUser()); //view,model
  }
  

  @RequestMapping(value = "reg", method = RequestMethod.POST)
  public View regHandle(AllUser entry) {
    gbEntryRepo.create(entry);
    return new RedirectView("/", true);
  }
}
