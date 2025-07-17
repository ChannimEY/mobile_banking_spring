package kh.edu.cstad.mbapi.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, length = 10)
    private  String gender;

    @Column(unique = true, length = 100)
    private  String email;
    @Column(unique = true , length = 14)
    private String phoneNumber;

    @Column(length = 50)
    private String employmentType;

    @Column(length = 50)
    private String position;

    @Column(length = 50)
    private String companyName;

    @Column(length = 50)
    private String mainSourceOfIncome;

    @Column(length = 50)
    private BigDecimal monthlyIncomeRange;

   @Column(columnDefinition = "TEXT")
    private String remark;

    @Column(nullable = false)
    private Boolean isDeleted;

    @Column(length = 100)
    private String address;

    @Column(length = 50)
    private String cityOrProvince;

    @Column(length = 50)
    private String country;

    @Column(length = 50)
    private String zipCode;

    @OneToMany (mappedBy = "customer")
//    @JoinColumn(name = "cust_id",referencedColumnName = "customer_id") change col name
   private List<Account> accounts;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private KYC kyc;

    @ManyToOne
    private CustomerSegment customerSegment;

}
