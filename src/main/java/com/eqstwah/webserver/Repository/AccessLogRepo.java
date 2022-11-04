package com.eqstwah.webserver.Repository;

import org.springframework.stereotype.Repository;
import com.eqstwah.webserver.Entity.AccessLogEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface AccessLogRepo extends JpaRepository<AccessLogEntity, Long> {

    @Query(value = "select * from access_log where token=:token", nativeQuery = true)
    List<AccessLogEntity> findByToken(@Param("token")String token);
    
}
