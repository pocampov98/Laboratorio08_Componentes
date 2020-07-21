package com.cenfotec.contacts.ContactsAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cenfotec.contacts.ContactsAPI.models.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}