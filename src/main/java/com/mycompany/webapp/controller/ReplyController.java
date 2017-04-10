package com.mycompany.webapp.controller;

import com.mycompany.webapp.dao.ReplyRepository;
import com.mycompany.webapp.model.Attachment;
import com.mycompany.webapp.model.Reply;
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
@RequestMapping("replys")
public class ReplyController {

    @Autowired
    ReplyRepository replyRepo;

    private volatile long REPLY_ID_SEQUENCE = 1;
    private Map<Long, Reply> replyDatabase = new LinkedHashMap<>();

    @RequestMapping(value = {"", "replys"}, method = RequestMethod.GET)
    public String topic(ModelMap model) {
        //model.addAttribute("topicDatabase", topicDatabase);
        model.addAttribute("replyDatabase", replyRepo.findAll());
        return "topic";
    }

    @RequestMapping(value = {"view/{topicId}"}, method = RequestMethod.GET)
    public ModelAndView reply(ModelMap model, @PathVariable("topicId") long topicId) {
        model.addAttribute("topicId", topicId);
        model.addAttribute("replyDatabase", replyRepo.findAll());
        return new ModelAndView("/viewReply");
    }

    @RequestMapping(value = "create/{topicId}", method = RequestMethod.GET)
    public ModelAndView createReply(@PathVariable("topicId") long topicId) {
        /*ModelAndView modelAndView = new ModelAndView("createReply", "replyForm", new Form());
         modelAndView.addObject("tId", topicId);
         return modelAndView;*/

        ModelAndView modelAndView = new ModelAndView("createReply");
        modelAndView.addObject("topicId", Long.toString(topicId));

        Form replyForm = new Form();
        modelAndView.addObject("replyForm", replyForm);

        return modelAndView;
        //return new ModelAndView("createReply", "replyForm", new Form());
    }

    public static class Form {

        private String msg;
        private long topicId;
        private List<MultipartFile> attachments;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public long getTopicId() {
            return topicId;
        }

        public void setTopicId(long topicId) {
            this.topicId = topicId;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }

    @RequestMapping(value = "create/{topicId}", method = RequestMethod.POST)
    public View create(Form form, Principal principal) throws IOException {
        Reply reply = new Reply();
        reply.setId(this.getNextReplyId());
        reply.setCustomerName(principal.getName());
        reply.setTopicId(form.getTopicId());
        reply.setMsg(form.getMsg());

        for (MultipartFile filePart : form.getAttachments()) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null && attachment.getContents().length > 0) {
                reply.addAttachment(attachment);
            }
        }

        this.replyDatabase.put(reply.getId(), reply);
        replyRepo.create(reply);
        //return new RedirectView("/topic/" + topic.getId(), true);
        return new RedirectView("/replys/view/" + form.getTopicId(), true);
    }

    private synchronized long getNextReplyId() {
        return this.REPLY_ID_SEQUENCE++;
    }

    @RequestMapping(
            value = "/{replyId}/attachment/{attachment:.+}",
            method = RequestMethod.GET
    )
    public View download(@PathVariable("replyId") long replyId,
            @PathVariable("attachment") String name) {
        Reply reply = this.replyDatabase.get(replyId);
        if (reply != null) {
            Attachment attachment = reply.getAttachment(name);
            if (attachment != null) {
                return new DownloadingView(attachment.getName(),
                        attachment.getMimeContentType(), attachment.getContents());
            }
        }
        return new RedirectView("/topic", true);
    }

    @RequestMapping(
            value = "/{replyId}/delete/{attachment:.+}",
            method = RequestMethod.GET
    )
    public View deleteAttachment(@PathVariable("replyId") long replyId,
            @PathVariable("attachment") String name) {
        Reply reply = this.replyDatabase.get(replyId);
        if (reply != null) {
            if (reply.hasAttachment(name)) {
                reply.deleteAttachment(name);
            }
        }
        return new RedirectView("/topic" + replyId, true);
    }

    @RequestMapping(value = "delete/{replyId}", method = RequestMethod.GET)
    public View deleteReply(@PathVariable("replyId") long topicId) {
        replyRepo.deleteByID(topicId);
        return new RedirectView("/topic", true);
    }

}
