package com.energize.shop.shopApp.services;

import com.energize.shop.shopApp.repository.UserDetailsImpl;
import com.energize.shop.shopApp.repository.UsersRepository;
import com.energize.shop.shopApp.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UsersRepository usersRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
User user = usersRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not find with userName:"+username));
return UserDetailsImpl.build(user);

    }
}
