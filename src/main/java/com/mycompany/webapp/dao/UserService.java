package com.mycompany.webapp.dao;

import com.mycompany.webapp.model.AllUser;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepo;
    
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        AllUser user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRoles()));

        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
