package com.ryan.code.experiment.transaction.locking.repository;

import com.ryan.code.experiment.transaction.locking.model.AccessCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutlinkAccessCountRepository extends JpaRepository<AccessCount, String> {

    AccessCount findByToken(String token);
}
