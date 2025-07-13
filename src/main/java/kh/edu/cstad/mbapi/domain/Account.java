package kh.edu.cstad.mbapi.domain;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String accNum;
    private Boolean isDeleted;
    private BigDecimal overLimit;

    @ManyToOne
    private Customer customer;


    @ManyToOne
//    @JoinColumn(name = "account_type_id")
    private AccountType accountType;



}
