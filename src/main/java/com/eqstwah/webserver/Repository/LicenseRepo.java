package com.eqstwah.webserver.Repository;

import org.springframework.stereotype.Repository;
import com.eqstwah.webserver.Entity.LicenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LicenseRepo extends JpaRepository<LicenseEntity, Long> {
    
}
