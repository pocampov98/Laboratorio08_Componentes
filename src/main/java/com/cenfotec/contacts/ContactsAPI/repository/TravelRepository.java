package com.cenfotec.contacts.ContactsAPI.repository;

import java.util.Collection;

import com.cenfotec.contacts.ContactsAPI.models.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {

    @Query(value = "SELECT t FROM Travel t WHERE t.contactId = ?1")
    Collection<Travel> findTravelsByUserId(Long contactId);

}