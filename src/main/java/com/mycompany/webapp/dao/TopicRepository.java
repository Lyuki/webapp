package com.mycompany.webapp.dao;

import com.mycompany.webapp.model.Topics;
import java.util.List;


public interface TopicRepository {
    public void create(Topics topic);
    public List<Topics> findAll();
    public Topics findByID(long id);
    public void deleteByID(long id);
}
