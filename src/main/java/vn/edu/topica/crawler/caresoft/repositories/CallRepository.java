package vn.edu.topica.crawler.caresoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.topica.crawler.caresoft.entities.CallHistory;

public interface CallRepository extends JpaRepository<CallHistory, Long> {
  @Query("SELECT max(calls.startTime) FROM CallHistory AS calls ")
  Object findLastStartTime();
}
