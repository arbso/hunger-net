package com.lufthansa.backend.security;


import com.lufthansa.backend.model.User;
import com.lufthansa.backend.repository.UserRepository;
import com.lufthansa.backend.service.DishService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@ComponentScan(basePackages = "com.lufthansa.backend.*")
public class MyUserDetails implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(MyUserDetails.class);
    private final UserRepository userRepository;

    public MyUserDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username);
        logger.info("Found user. Preparing to log in..");
        if (user == null) {
            logger.warn("Username " + username + " has not been found. Try a different one or enter it correcly");
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }

        return org.springframework.security.core.userdetails.User//
                .withUsername(username)//
                .password(user.getPassword())//
                .authorities(user.getRoles())//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }

}
