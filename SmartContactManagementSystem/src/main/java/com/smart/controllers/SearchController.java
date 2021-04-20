package com.smart.controllers;

import com.smart.domain.ContactDomain;
import com.smart.domain.UserDomain;
import com.smart.repositories.ContactRepository;
import com.smart.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class SearchController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ContactRepository contactRepository;

    @GetMapping("/search/{query}")
    public ResponseEntity<?> searchContact(@PathVariable("query") String query, Principal principal) {
        UserDomain userDomain = this.userRepository.getUserByName(principal.getName());
        List<ContactDomain> contactDomains = this.contactRepository.findByContactNameContainingAndUserDomain(query, userDomain);
        return ResponseEntity.ok(contactDomains);
    }
}
