package org.sits.pr.api.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.sits.pr.api.entity.UserInfo;
import org.sits.pr.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserInfo userInfo = userRepository.findByUserName(userName);
        if(userInfo == null) {
            throw  new UsernameNotFoundException("No User Found");
        }
        
        boolean isEnabled = (userInfo.getIsEnabled() == 1) ?  true : false;
        
        return new org.springframework.security.core.userdetails.User
        		(
        		userInfo.getUserName(),
        		userInfo.getPassword(),
                isEnabled,
                true,
                true,
                true,
                getAuthorities(List.of(userInfo.getRole())) );
        
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority>  authorities = new ArrayList<>();
        for(String role: roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
