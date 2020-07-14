package vn.edu.topica.crawler.caresoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.topica.crawler.caresoft.entities.ContactList;

public interface ContactListRepository extends JpaRepository<ContactList, Long> {

    @Query("SELECT max(c.updatedAt) FROM ContactList AS c ")
    Object findLastUpdatedTime();
}
