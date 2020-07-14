package vn.edu.topica.crawler.caresoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.topica.crawler.caresoft.entities.CustomFields;

public interface CustomFieldRepository extends JpaRepository<CustomFields, Long> {
}
