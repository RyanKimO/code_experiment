package com.ryan.code.experiment.transaction.locking.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "access_count")
//@SQLInsert(sql = "INSERT INTO access_count(token, cnt) VALUES (?, ?) ON DUPLICATE KEY UPDATE cnt = cnt + 1")
public class AccessCount implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "token", unique = true)
    private String token;

    @Column(name = "cnt")
    private int cnt;

}
