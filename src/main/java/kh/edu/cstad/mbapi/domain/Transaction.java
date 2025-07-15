package kh.edu.cstad.mbapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)

    private TransactionType transactionType;

    @ManyToOne(optional = false)
    private Account sender;

    @ManyToOne(optional = false)
    private Account receiver;

    @Column(nullable = false)
    private BigDecimal amount;

    private String remark;

    private LocalDateTime timestamp;
    @Column(nullable = false)
    private Boolean isDeleted;

}
