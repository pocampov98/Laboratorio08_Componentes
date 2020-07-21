package com.cenfotec.contacts.ContactsAPI.controllers;

import java.util.Collection;
import java.util.List;

import com.cenfotec.contacts.ContactsAPI.models.Contact;
import com.cenfotec.contacts.ContactsAPI.models.Travel;
import com.cenfotec.contacts.ContactsAPI.repository.ContactRepository;
import com.cenfotec.contacts.ContactsAPI.repository.TravelRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({ "/contacts" })
public class ContactController {
    private ContactRepository contactRepository;
    private TravelRepository travelRepository;

    ContactController(ContactRepository contactRepository, TravelRepository travelRepository) {
        this.contactRepository = contactRepository;
        this.travelRepository = travelRepository;
    }

    // Contact
    @GetMapping
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    // Paginacion
    @GetMapping(path = { "/{page}/{size}" })
    public List<Contact> findAll(@PathVariable("page") int page, @PathVariable("size") int size) {
        return contactRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @GetMapping(path = { "/{id}" })
    public ResponseEntity<Contact> findById(@PathVariable long id) {
        return contactRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Contact create(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Contact> update(@PathVariable("id") long id, @RequestBody Contact contact) {
        return contactRepository.findById(id).map(record -> {
            record.setName(contact.getName());
            record.setEmail(contact.getEmail());
            record.setPhone(contact.getPhone());
            Contact updated = contactRepository.save(record);
            return ResponseEntity.ok().body(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = { "/{id}" })
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return contactRepository.findById(id).map(record -> {
            contactRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // Travel
    @GetMapping(path = { "/travels/{id}" })
    public Collection<Travel> findTravelsById(@PathVariable("id") long contactId) {
        return travelRepository.findTravelsByUserId(contactId);
    }

    @PostMapping(path = { "/travels" })
    public Travel createTravel(@RequestBody Travel travel) {
        return travelRepository.save(travel);
    }

}