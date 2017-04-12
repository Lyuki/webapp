package com.mycompany.webapp.controller;

import com.mycompany.webapp.dao.PollRepository;
import com.mycompany.webapp.dao.VoteRepository;
import static com.mycompany.webapp.model.MyConstants.LANGUAGEOPT;
import com.mycompany.webapp.model.Poll;
import com.mycompany.webapp.model.Vote;
import java.io.IOException;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PollController {
    @Autowired
     PollRepository pollRepo;

    private volatile long POLL_ID_SEQUENCE = 1;
    private Map<Long, Poll> pollDatabase = new LinkedHashMap<>();
    
    /*@RequestMapping(value = {"", "createPoll"}, method = RequestMethod.GET)
    public String poll(ModelMap model) {
        //model.addAttribute("topicDatabase", topicDatabase);
        model.addAttribute("pollDatabase", pollRepo.findAll());
        return "createPoll";
    }*/
    
    @RequestMapping(value = "createPoll", method = RequestMethod.GET)
        public ModelAndView create() {
            ModelAndView modelAndView = new ModelAndView("newPoll");
            modelAndView.addObject("language", LANGUAGEOPT);
            
            Form form = new Form();
            modelAndView.addObject("pollForm", form);
        return modelAndView;
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
    
    @RequestMapping(value = "createPoll", method = RequestMethod.POST)
    public View createPoll(Form form, Principal principal) throws IOException {
        Poll poll = new Poll();
        poll.setId(this.getNextPollId());
        poll.setCustomerName(principal.getName());
        poll.setQuestion(form.getQuestion());
        poll.setAns1(form.getAns1());
        poll.setAns2(form.getAns2());
        poll.setAns3(form.getAns3());
        poll.setAns4(form.getAns4());

        this.pollDatabase.put(poll.getId(), poll);
        pollRepo.create(poll);
        //return new RedirectView("/topic/" + topic.getId(), true);
        return new RedirectView("/", true);
    }
    
    private synchronized long getNextPollId() {
        return this.POLL_ID_SEQUENCE++;
    }
    
    @Autowired
    VoteRepository voteRepo;
    
    private volatile long VOTE_ID_SEQUENCE = 1;
    private Map<Long, Vote> voteDatabase = new LinkedHashMap<>();
    
    @RequestMapping(value = "index", method = RequestMethod.GET)
        public ModelAndView createVote() {
        return new ModelAndView("index", "voteForm", new voteForm());
    }
        
    public static class voteForm {
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
    
    @RequestMapping(value = "index", method = RequestMethod.POST)
    public View createVote(voteForm vform, Principal principal) throws IOException {
        Vote vote = new Vote();
        vote.setId(this.getNextVoteId());
        vote.setPollId(vform.getPollId());
        vote.setCustomerName(principal.getName());
        vote.setAnsId(vform.getAnsId());
        
        this.voteDatabase.put(vote.getId(), vote);
        voteRepo.create(vote);
        //return new RedirectView("/topic/" + topic.getId(), true);
        return new RedirectView("/", true);
    }
    
    private synchronized long getNextVoteId() {
        return this.VOTE_ID_SEQUENCE++;
    }
    
}
