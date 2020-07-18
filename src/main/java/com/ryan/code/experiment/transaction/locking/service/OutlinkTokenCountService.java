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
    private final SaveTxNew saveTxNew;


    @Transactional(isolation = Isolation.DEFAULT)
    public void iDefault(final String outlinkToken) {
        // 최초: Caused by: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '81ABD7629C5B' for key 'access_count.access_count_token_uindex'
        // 데이터 있음: 업데이트 누락. 10번 호출해도 1밖에 증가안함

        // 비관락 쓰기락 사용 : 읽고 쓰기는 안되나 업데이트는 잘 동작함
        AccessCount accessCount = outlinkAccessCountRepository.findByToken(outlinkToken);
        if(accessCount == null) {
            accessCount = new AccessCount();
            accessCount.setToken(outlinkToken);
            accessCount.setCnt(1);
            try {
                saveTxNew.save(accessCount);
            } catch(Exception e) {
                System.out.println("롤백 나니?");
            }
        } else {
            accessCount.setCnt(accessCount.getCnt() + 1);
        }
        // commit
    }


    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void readUncommitted(final String outlinkToken) {
        // 최초 : Caused by: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '81ABD7629C5B' for key 'access_count.access_count_token_uindex'
        // 데이터 있음: 업데이트 누락. 10번 호출해도 1밖에 증가안함

        // 비관락 쓰기락 사용 : 읽고 쓰기는 안되나 업데이트는 잘 동작함
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
    public void readCommitted(final String outlinkToken) {
        // 최초: Caused by: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '81ABD7629C5B' for key 'access_count.access_count_token_uindex'
        // 데이터 있음: 업데이트 누락. 10번 호출해도 1밖에 증가안함

        // 비관락 쓰기락 사용 : 읽고 쓰기는 안되나 업데이트는 잘 동작함
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
        // 최초: Caused by: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '81ABD7629C5B' for key 'access_count.access_count_token_uindex'
        // 데이터 있음: 업데이트 누락. 10번 호출해도 1밖에 증가안함

        // 비관락 쓰기락 사용 : 읽고 쓰기는 안되나 업데이트는 잘 동작함
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
        // lock 설없을시
        // 최초: org.springframework.dao.CannotAcquireLockException: could not execute statement;
        // 데이터 이미 있는경우: 1밖에 증가안함

        // 비관 쓰기 락 사용시
        // 읽고 없으면 쓰는 부분에서 데드 락
        // 업데이트는 잘됨

        // 이런식으로 랜덤으로 슬립시켜서 할 수는 있다... ㅎㅎㅎㅎ
        //        int randomSleepTime = new Random().nextInt(1000);
        //        try {
        //            Thread.sleep(randomSleepTime);
        //        } catch(InterruptedException e) {
        //            e.printStackTrace();
        //        }

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


    @Transactional
    public void atomicUpdate(String outlinkToken) {
        AccessCount accessCount = outlinkAccessCountRepository.findByToken(outlinkToken);
        if(accessCount == null) {
            accessCount = new AccessCount();
            accessCount.setToken(outlinkToken);
            accessCount.setCnt(1);
            outlinkAccessCountRepository.save(accessCount);
        } else {
            outlinkAccessCountRepository.updateByToken(outlinkToken);
        }
    }


    @Transactional
    public void upsert(String outlinkToken) {
        AccessCount accessCount = new AccessCount();
        accessCount.setToken(outlinkToken);
        accessCount.setCnt(1);
        outlinkAccessCountRepository.save(accessCount);
    }
}
