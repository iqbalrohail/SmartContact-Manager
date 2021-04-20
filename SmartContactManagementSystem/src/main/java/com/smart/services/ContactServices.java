package com.smart.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.data.transfer.objects.ContactDto;
import com.smart.domain.ContactDomain;
import com.smart.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class ContactServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public BindingResult formValidation(BindingResult bindingResult) {
        return bindingResult;
    }

    public ContactDomain mapDtoObjectToDomain(ContactDto contactDto) {
        return objectMapper.convertValue(contactDto, ContactDomain.class);
    }

    public List<ContactDto> mapDomainObjectToDto(List<ContactDomain> contactDomain) {
        return (List<ContactDto>) objectMapper.convertValue(contactDomain, ContactDto.class);
    }
}
