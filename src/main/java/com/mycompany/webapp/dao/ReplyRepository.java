package com.mycompany.webapp.dao;

import com.mycompany.webapp.model.Attachment;
import com.mycompany.webapp.model.Reply;
import java.util.List;

public interface ReplyRepository {

    public void create(Reply reply);

    public void editByID(Reply reply);

    public List<Reply> findAll(long tid);

    public Reply findByID(long id);
    
    public Attachment findAttachByID(long id, String filename);

    public void deleteByID(long id);
    
}
