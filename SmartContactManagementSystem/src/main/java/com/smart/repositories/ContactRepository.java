package com.smart.repositories;

import com.smart.domain.ContactDomain;
import com.smart.domain.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<ContactDomain , Integer> {

    @Query("from ContactDomain c where c.userDomain.userId = :contactId")
    public List<ContactDomain> getContactsByUser(@Param("contactId") int contactId);

    // for search bar
    public List<ContactDomain> findByContactNameContainingAndUserDomain(String contactName , UserDomain userDomain);

}
