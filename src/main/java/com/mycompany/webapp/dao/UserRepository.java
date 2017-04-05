package com.mycompany.webapp.dao;

import com.mycompany.webapp.model.AllUser;
import java.util.List;

public interface UserRepository {

    public void create(AllUser user);
    public List<AllUser> findAll();
    public AllUser findByUsername(String username);
    public void deleteByUsername(String username);
}
