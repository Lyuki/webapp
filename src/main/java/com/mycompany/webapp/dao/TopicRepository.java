package com.mycompany.webapp.dao;

import com.mycompany.webapp.model.Attachment;
import com.mycompany.webapp.model.Topics;
import java.util.List;


public interface TopicRepository {
    public void create(Topics topic);
    public void editByID(Topics topic);
    public List<Topics> findAll(String cate);
    public Topics findByID(long id);
    public Attachment findAttachByID(long id, String filename);
    public void deleteByID(long id);
}
