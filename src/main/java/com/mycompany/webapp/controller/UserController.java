package com.mycompany.webapp.controller;

import com.mycompany.webapp.dao.UserRepository;
import com.mycompany.webapp.model.AllUser;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserRepository userRepo;

    //@Autowired
    //private PasswordEncoder passwordEncoder;
    @RequestMapping(value = {"","list"}, method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.addAttribute("allUsers", userRepo.findAll());
        return "listUser";
    }

    public static class Form {

        private String username;
        private String password;
        private String[] roles;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }

    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView create() {
        return new ModelAndView("addUser", "user", new Form());
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public View create(Form form) throws IOException {
        AllUser user = new AllUser();
        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());
        //user.setPassword(passwordEncoder.encode(form.getPassword()));
        for (String role : form.getRoles()) {
            user.addRole(role);
        }
        if(userRepo.findByUsername(form.getUsername()) == null){
          userRepo.create(user);
        }
        
        return new RedirectView("/", true);
    }

    @RequestMapping(value = "delete/{username}", method = RequestMethod.GET)
    public View deleteTicket(@PathVariable("username") String username) {
        userRepo.deleteByUsername(username);
        return new RedirectView("/user/list", true);
    }

}