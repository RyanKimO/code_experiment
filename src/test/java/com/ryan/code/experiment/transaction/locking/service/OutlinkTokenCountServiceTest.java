package com.ryan.code.experiment.transaction.locking.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.ryan.code.experiment.transaction.locking.model.AccessCount;
import com.ryan.code.experiment.transaction.locking.repository.OutlinkAccessCountRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

/**
 * Developer : ryan kim
 * Date : 2020-07-11
 */
@SpringBootTest
class OutlinkTokenCountServiceTest {

    @Autowired
    OutlinkTokenCountService outlinkTokenCountService;

    @Autowired
    OutlinkAccessCountRepository outlinkAccessCountRepository;


    @Test
    @Rollback(false)
    public void multi_thread_transaction_lock() throws InterruptedException {
        String token = "81ABD7629C5B";

        int numberOfThreads = 10;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for(int i = 0; i < numberOfThreads; i++) {
            int finalI = i;
            service.execute(() -> {
                try {
                    outlinkTokenCountService.iDefault(token);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }

        latch.await();

        AccessCount accessCountRes = outlinkAccessCountRepository.findByToken(token);

        assertNotNull(accessCountRes);

        int resCount = accessCountRes.getCnt();
        assertEquals(numberOfThreads, resCount);
    }
}