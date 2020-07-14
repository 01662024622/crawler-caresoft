package vn.edu.topica.crawler.caresoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.topica.crawler.caresoft.entities.CustomFieldMapping;

public interface CustomFieldMappingRepository extends JpaRepository<CustomFieldMapping, Long> {
}
