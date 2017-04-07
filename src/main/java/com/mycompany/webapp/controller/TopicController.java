package com.mycompany.webapp.controller;

import com.mycompany.webapp.dao.TopicRepository;
import com.mycompany.webapp.model.Attachment;
import com.mycompany.webapp.model.Topics;
import com.mycompany.webapp.view.DownloadingView;
import java.io.IOException;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("topic")
public class TopicController {
     @Autowired
    TopicRepository topicRepo;

    private volatile long TOPIC_ID_SEQUENCE = 1;
    private Map<Long, Topics> topicDatabase = new LinkedHashMap<>();

    @RequestMapping(value = {"", "topic"}, method = RequestMethod.GET)
    public String topic(ModelMap model) {
        //model.addAttribute("topicDatabase", topicDatabase);
        model.addAttribute("topicDatabase", topicRepo.findAll());
        return "topic";
    }

    @RequestMapping(value = "reply/{topicId}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("topicId") long topicId) {
        //Topics topic = this.topicDatabase.get(topicId);
        Topics topic = topicRepo.findByID(topicId);
        if (topic == null) {
            return new ModelAndView(new RedirectView("/topic", true));
        }  
        ModelAndView modelAndView = new ModelAndView("reply");
        modelAndView.addObject("topicId", Long.toString(topicId));
        modelAndView.addObject("topic", topic);
        return modelAndView;
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView create() {
        return new ModelAndView("newTopic", "topicForm", new Form());
    }

    public static class Form {

        private String title;
        private String msg;
        private String category;
        private List<MultipartFile> attachments;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public View create(Form form, Principal principal) throws IOException {
        Topics topic = new Topics();
        topic.setId(this.getNextTopicId());
        topic.setCustomerName(principal.getName());
        topic.setTitle(form.getTitle());
        topic.setMsg(form.getMsg());
        topic.setCategory(form.getCategory());
        
        for (MultipartFile filePart : form.getAttachments()) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null && attachment.getContents().length > 0) {
                topic.addAttachment(attachment);
            }
        }
        
        this.topicDatabase.put(topic.getId(), topic);
        topicRepo.create(topic);
        //return new RedirectView("/topic/" + topic.getId(), true);
        return new RedirectView("/topic", true);
    }

    private synchronized long getNextTopicId() {
        return this.TOPIC_ID_SEQUENCE++;
    }

    @RequestMapping(
            value = "/{topicId}/attachment/{attachment:.+}",
            method = RequestMethod.GET
    )
    public View download(@PathVariable("topicId") long topicId,
            @PathVariable("attachment") String name) {
        Topics topic = this.topicDatabase.get(topicId);
        if (topic != null) {
            Attachment attachment = topic.getAttachment(name);
            if (attachment != null) {
                return new DownloadingView(attachment.getName(),
                        attachment.getMimeContentType(), attachment.getContents());
            }
        }
        return new RedirectView("/topic", true);
    }

    @RequestMapping(
            value = "/{topicId}/delete/{attachment:.+}",
            method = RequestMethod.GET
    )
    public View deleteAttachment(@PathVariable("topicId") long topicId,
            @PathVariable("attachment") String name) {
        Topics topic = this.topicDatabase.get(topicId);
        if (topic != null) {
            if (topic.hasAttachment(name)) {
                topic.deleteAttachment(name);
            }
        }
        return new RedirectView("/topic/edit/" + topicId, true);
    }
    

    @RequestMapping(value = "edit/{topicId}", method = RequestMethod.GET)
    public ModelAndView showEdit(@PathVariable("topicId") long topicId, Principal principal) {
        Topics topic = topicRepo.findByID(topicId);
        if (topic == null || !principal.getName().equals(topic.getCustomerName())) {
            return new ModelAndView(new RedirectView("/topic/reply", true));
        }

        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("topicId", Long.toString(topicId));
        modelAndView.addObject("topic", topic);

        Form topicForm = new Form();
        topicForm.setMsg(topic.getMsg());
        topicForm.setTitle(topic.getTitle());
        modelAndView.addObject("topicForm", topicForm);

        return modelAndView;
    }

    @RequestMapping(value = "edit/{topicId}", method = RequestMethod.POST)
    public View edit(@PathVariable("topicId") long topicId, Form form, Principal principal)
            throws IOException {
        Topics topic = topicRepo.findByID(topicId);
        if (topic == null || !principal.getName().equals(topic.getCustomerName())) {
            return new RedirectView("/topic/reply", true);
        }
        topic.setMsg(form.getMsg());
        topic.setTitle(form.getTitle());
        
        for (MultipartFile filePart : form.getAttachments()) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null && attachment.getContents().length > 0) {
                topic.addAttachment(attachment);
            }
        }

        topicRepo.editByID(topic);
        return new RedirectView("/topic/reply/" + topic.getId(), true);
    }

    @RequestMapping(value = "delete/{topicId}", method = RequestMethod.GET)
    public View deleteTopic(@PathVariable("topicId") long topicId) {
        topicRepo.deleteByID(topicId);
        return new RedirectView("/topic", true);
    }

}
