package com.smart.config;

import com.smart.data.transfer.objects.UserDto;
import com.smart.domain.UserDomain;
import com.smart.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsSeviceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
       UserDomain userDomain =  userRepository.getUserByName(userEmail);
       if(userDomain == null)
       {
           throw new UsernameNotFoundException("Could not found user !");
       }
       CustomUserDetails customUserDetails = new CustomUserDetails(userDomain);
        return customUserDetails;
    }
}
