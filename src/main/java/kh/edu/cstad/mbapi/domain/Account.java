package kh.edu.cstad.mbapi.domain;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String accNum;
    @Column(nullable = false)
    private Boolean isDeleted;
    private BigDecimal overLimit;

    @ManyToOne
    private Customer customer;


    @ManyToOne
    private AccountType accountType;

    @OneToMany(mappedBy = "sender")
    private List<Transaction> transactionList;


}
