package com.ryan.code.experiment.transaction.locking.repository;

import com.ryan.code.experiment.transaction.locking.model.OutlinkToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutlinkTokenRepository extends JpaRepository<OutlinkToken, String> {

    OutlinkToken findByToken(String token);
}
