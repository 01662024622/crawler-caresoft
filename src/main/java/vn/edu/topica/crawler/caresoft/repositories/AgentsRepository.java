package vn.edu.topica.crawler.caresoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.topica.crawler.caresoft.entities.Agents;

public interface AgentsRepository extends JpaRepository<Agents, Long> {
}
