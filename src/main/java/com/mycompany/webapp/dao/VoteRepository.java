package com.mycompany.webapp.dao;

import com.mycompany.webapp.model.Vote;
import java.util.List;

public interface VoteRepository {
    public void create(Vote vote);
    public List<Vote> findAll();
    public Vote findByPollID(long pollId, String username);
}
