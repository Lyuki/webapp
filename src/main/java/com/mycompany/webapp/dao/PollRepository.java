package com.mycompany.webapp.dao;

import com.mycompany.webapp.model.Poll;
import java.util.List;

public interface PollRepository {
    public void create(Poll poll);
    public List<Poll> findAll();
    public Poll findByID(long id);
    public Poll findByPollID();
}
