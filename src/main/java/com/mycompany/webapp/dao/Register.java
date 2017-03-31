package com.mycompany.webapp.dao;

import com.mycompany.webapp.model.User;
import java.util.List;

public interface Register {
    public void create(User entry);
    //public List<GuestBookEntry> findAll();
    public User findById(int id);
    //public void update(GuestBookEntry entry);
}
