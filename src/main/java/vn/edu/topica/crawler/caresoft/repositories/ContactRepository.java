package vn.edu.topica.crawler.caresoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.topica.crawler.caresoft.entities.Contact;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("SELECT contact.id FROM Contact contact WHERE contact.id NOT IN (SELECT DISTINCT contactList.id FROM ContactList contactList)")
    List<Long> findIdNotInContacts();
}
