package com.carapi.api.security;

import com.carapi.domain.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "mainUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRep;

    //returns an user object with details about its authentication
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.carapi.domain.User user = userRep.findByLogin(username);
        
        if (user == null) {
            throw new UsernameNotFoundException("user nor found");
        }

        return user;
    }
}
