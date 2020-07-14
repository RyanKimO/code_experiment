package com.ryan.code.experiment.transaction.locking.service;

import com.ryan.code.experiment.transaction.locking.model.AccessCount;
import com.ryan.code.experiment.transaction.locking.repository.OutlinkAccessCountRepository;
import com.ryan.code.experiment.transaction.locking.repository.OutlinkTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Developer : ryan kim
 * Date : 2020-07-11
 */
@Slf4j
@Service
@AllArgsConstructor
public class OutlinkTokenCountService {

    private final OutlinkTokenRepository outlinkTokenRepository;
    private final OutlinkAccessCountRepository outlinkAccessCountRepository;


    @Transactional(isolation = Isolation.DEFAULT)
    public void iDefault(final String outlinkToken) {
        // 최초: Caused by: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '81ABD7629C5B' for key 'access_count.access_count_token_uindex'
        // 데이터 있음: 업데이트 누락. 10번 호출해도 1밖에 증가안함
        AccessCount accessCount = outlinkAccessCountRepository.findByToken(outlinkToken);
        if(accessCount == null) {
            accessCount = new AccessCount();
            accessCount.setToken(outlinkToken);
            accessCount.setCnt(1);
            outlinkAccessCountRepository.save(accessCount);
        } else {
            accessCount.setCnt(accessCount.getCnt() + 1);
        }
    }


    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void readUncommitted(final String outlinkToken) {

        AccessCount accessCount = outlinkAccessCountRepository.findByToken(outlinkToken);
        if(accessCount == null) {
            accessCount = new AccessCount();
            accessCount.setToken(outlinkToken);
            accessCount.setCnt(1);
            outlinkAccessCountRepository.save(accessCount);
        } else {
            accessCount.setCnt(accessCount.getCnt() + 1);
        }
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    // 최초: Caused by: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '81ABD7629C5B' for key 'access_count.access_count_token_uindex'
    // 데이터 있음: 업데이트 누락. 10번 호출해도 1밖에 증가안함
    public void readCommitted(final String outlinkToken) {

        AccessCount accessCount = outlinkAccessCountRepository.findByToken(outlinkToken);
        if(accessCount == null) {
            accessCount = new AccessCount();
            accessCount.setToken(outlinkToken);
            accessCount.setCnt(1);
            outlinkAccessCountRepository.save(accessCount);
        } else {
            accessCount.setCnt(accessCount.getCnt() + 1);
        }
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void repeatableRead(final String outlinkToken) {

        AccessCount accessCount = outlinkAccessCountRepository.findByToken(outlinkToken);
        if(accessCount == null) {
            accessCount = new AccessCount();
            accessCount.setToken(outlinkToken);
            accessCount.setCnt(1);
            outlinkAccessCountRepository.save(accessCount);
        } else {
            accessCount.setCnt(accessCount.getCnt() + 1);
        }
    }


    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void serializable(final String outlinkToken) {

        AccessCount accessCount = outlinkAccessCountRepository.findByToken(outlinkToken);
        if(accessCount == null) {
            accessCount = new AccessCount();
            accessCount.setToken(outlinkToken);
            accessCount.setCnt(1);
            outlinkAccessCountRepository.save(accessCount);
        } else {
            accessCount.setCnt(accessCount.getCnt() + 1);
        }
    }
}
