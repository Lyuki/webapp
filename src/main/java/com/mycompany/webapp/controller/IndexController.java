package com.mycompany.webapp.controller;

import com.mycompany.webapp.dao.PollRepository;
import com.mycompany.webapp.dao.VoteRepository;
import com.mycompany.webapp.model.Poll;
import com.mycompany.webapp.model.Vote;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

@Controller
public class IndexController {

   @Autowired
    PollRepository pollRepo;
    Poll poll = new Poll();
    @Autowired
    VoteRepository voteRepo;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("poll", pollRepo.findAll());

        VoteForm voteForm = new VoteForm();
        modelAndView.addObject("VoteForm", voteForm);

        return modelAndView;
    }
    
    public static class VoteForm {
        private long pollId;
        private String customerName;
        private long ansId;

        public long getPollId() {
            return pollId;
        }

        public void setPollId(long pollId) {
            this.pollId = pollId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public long getAnsId() {
            return ansId;
        }

        public void setAnsId(long ansId) {
            this.ansId = ansId;
        }
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public View createVote(VoteForm form, Principal principal) throws IOException {
        Vote vote = new Vote();
        vote.setId(1);
        vote.setPollId(form.getPollId());
        vote.setCustomerName(principal.getName());
        vote.setAnsId(form.getAnsId());
        
        voteRepo.create(vote);
        return new RedirectView("/", true);
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
        return new RedirectView("/topic/Lecture", true);
    }
    
    @RequestMapping("Lab")
    public View Labtopics() {
        return new RedirectView("/topic/Lab", true);
    }
    
    @RequestMapping("Other")
    public View Othertopics() {
        return new RedirectView("/topic/Other", true);
    }
}

