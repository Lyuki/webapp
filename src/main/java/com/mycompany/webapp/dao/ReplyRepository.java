package com.mycompany.webapp.dao;

import com.mycompany.webapp.model.Reply;
import java.util.List;

public interface ReplyRepository {

    public void create(Reply reply);

    public void editByID(Reply reply);

    public List<Reply> findAll();

    public Reply findByID(long id);

    public void deleteByID(long id);
}