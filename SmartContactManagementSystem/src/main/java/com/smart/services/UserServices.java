package com.smart.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.data.transfer.objects.UserDto;
import com.smart.domain.UserDomain;
import com.smart.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public BindingResult formValidation(BindingResult bindingResult) {
        return bindingResult;
    }

    public UserDomain mapDtoObjectToDomain(UserDto userDto) {
        return objectMapper.convertValue(userDto, UserDomain.class);
    }

    public UserDto mapDomainObjectToDto(UserDomain userDomain) {
        return objectMapper.convertValue(userDomain, UserDto.class);
    }
}
