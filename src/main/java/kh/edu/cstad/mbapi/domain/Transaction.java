package kh.edu.cstad.mbapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
//    @JoinColumn(name = "transaction_type_id")
    private TransactionType transactionType;

    @ManyToOne
//    @JoinColumn(name = "sender_id")
    private Account sender;

    @ManyToOne
//    @JoinColumn(name = "receiver_id")
    private Account receiver;

    @Column(nullable = false)
    private BigDecimal amount;

    private String remark;

    private LocalDateTime timestamp;
}
