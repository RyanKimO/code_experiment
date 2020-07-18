package com.ryan.code.experiment.transaction.locking.repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import com.ryan.code.experiment.transaction.locking.model.AccessCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

@Repository
public interface OutlinkAccessCountRepository extends JpaRepository<AccessCount, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "10000")})
    AccessCount findByToken(String token);

    @Modifying
    @Query("UPDATE AccessCount AC set AC.cnt = AC.cnt + 1 where AC.token = :token")
    void updateByToken(String token);
}
