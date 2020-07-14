package com.ryan.code.experiment.transaction.locking.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

@Data
@Entity
@Table(name = "outlink_token")
public class OutlinkToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "outlink_company_base_idx", nullable = false)
    private int outlinkCompanyBaseIdx;

    @NaturalId
    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "link_position")
    private String linkPosition;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    @Column(name = "status")
    private String status;

    @Column(name = "link_count")
    private int linkCount;

}
