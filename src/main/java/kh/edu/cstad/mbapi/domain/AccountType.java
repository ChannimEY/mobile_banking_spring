package kh.edu.cstad.mbapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "account_types")
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String typeName;  // e.g., PAYROLL, SAVING, etc.

    @Column(nullable = false)
    private Boolean isDeleted;

    @OneToMany(mappedBy = "accountType")
    @Column(nullable = true)
    private List<Account> accounts;
}
