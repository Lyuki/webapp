package com.mycompany.webapp.controller;

import com.mycompany.webapp.dao.PollRepository;
import com.mycompany.webapp.model.Poll;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("poll")

public class PollController {
  @Autowired
    PollRepository pollRepo;
  
  @RequestMapping(value = {"", "poll"}, method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.addAttribute("allPoll", pollRepo.findAll());
        return "listPoll";
    }
    
     public static class Form {
        private String question;
        private String ans1;
        private String ans2;
        private String ans3;
        private String ans4;

        public String getQuestion() {
          return question;
        }

        public void setQuestion(String question) {
          this.question = question;
        }

        public String getAns1() {
          return ans1;
        }

        public void setAns1(String ans1) {
          this.ans1 = ans1;
        }

        public String getAns2() {
          return ans2;
        }

        public void setAns2(String ans2) {
          this.ans2 = ans2;
        }

        public String getAns3() {
          return ans3;
        }

        public void setAns3(String ans3) {
          this.ans3 = ans3;
        }

        public String getAns4() {
          return ans4;
        }

        public void setAns4(String ans4) {
          this.ans4 = ans4;
        }
    }
    
    @RequestMapping(value = "createPoll", method = RequestMethod.GET)
    public ModelAndView createPoll() {
        return new ModelAndView("createPoll", "poll", new Form());
    }
    
    @RequestMapping(value = "createPoll", method = RequestMethod.POST)
    public View create(Form form) throws IOException {
        Poll poll = new Poll();
        poll.setQuestion(form.getQuestion());
        poll.setAns1(form.getAns1());
        poll.setAns1(form.getAns2());
        poll.setAns1(form.getAns3());
        poll.setAns1(form.getAns4());
        
        pollRepo.create(poll);
        return new RedirectView("/", true);
    }
    
}
