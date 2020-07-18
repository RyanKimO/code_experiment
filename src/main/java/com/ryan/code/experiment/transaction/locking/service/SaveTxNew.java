package com.ryan.code.experiment.transaction.locking.service;

import com.ryan.code.experiment.transaction.locking.model.AccessCount;
import com.ryan.code.experiment.transaction.locking.repository.OutlinkAccessCountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Developer : ryan kim
 * Date : 2020-07-18
 */
@Slf4j
@Service
@AllArgsConstructor
public class SaveTxNew {

    private final OutlinkAccessCountRepository outlinkAccessCountRepository;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AccessCount save(AccessCount accessCount) {
        return outlinkAccessCountRepository.save(accessCount);
    }


}
