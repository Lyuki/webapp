package com.mycompany.webapp.controller;

import com.mycompany.webapp.dao.UserRepository;
import com.mycompany.webapp.model.AllUser;
import static com.mycompany.webapp.model.MyConstants.LANGUAGEOPT;
import java.io.IOException;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.addAttribute("allUsers", userRepo.findAll());
        model.addAttribute("language", LANGUAGEOPT);
        return "listUser";
    }

    public static class Form {

        private String username;
        private String password;
        private String roles;

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

        public String getRoles() {
            return roles;
        }

        public void setRoles(String roles) {
            this.roles = roles;
        }

    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("addUser");
            modelAndView.addObject("language", LANGUAGEOPT);
            
            Form form = new Form();
            modelAndView.addObject("user", form);
        return modelAndView;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public View create(Form form) throws IOException {
        AllUser user = new AllUser();
        user.setUsername(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.addRole(form.getRoles());
        userRepo.create(user);
        return new RedirectView("/", true);
    }

    @RequestMapping(value = "edit/{username}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("username") String username, Principal principal) {
        AllUser user = userRepo.findByUsername(username);
        if (user == null) {
            return new ModelAndView(new RedirectView("/user/list", true));
        }

        ModelAndView modelAndView = new ModelAndView("editUser");
        modelAndView.addObject("username", username);
        modelAndView.addObject("language", LANGUAGEOPT);
        
        Form editForm = new Form();
        modelAndView.addObject("edituser", editForm);

        return modelAndView;
    }

    @RequestMapping(value = "edit/{username}", method = RequestMethod.POST)
    public View edit(@PathVariable("username") String username, Form form, Principal principal)
            throws IOException {
        AllUser user = userRepo.findByUsername(username);
        if (user == null) {
            return new RedirectView("/user/list", true);
        }

        user.setPassword(passwordEncoder.encode(form.getPassword()));

        if (form.getRoles() != null) {
            user.addRole(form.getRoles());
        }
        userRepo.editUser(user);
        return new RedirectView("/user/list", true);
    }

    @RequestMapping(value = "delete/{username}", method = RequestMethod.GET)
    public View deleteTicket(@PathVariable("username") String username) {
        userRepo.deleteByUsername(username);
        return new RedirectView("/user/list", true);
    }

}
