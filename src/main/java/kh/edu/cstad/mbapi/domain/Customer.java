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
@Table(name="customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    @Column(nullable = false, length = 100)
    private String fullname;

    @Column(nullable = false, length = 10)
    private  String gender;

    @Column(unique = true, length = 100)
    private  String email;
    @Column(unique = true , length = 14)
    private String phoneNumber;

   @Column(columnDefinition = "TEXT")
    private String remark;

    @Column(nullable = false)
    private Boolean isDelete;

    @OneToMany (mappedBy = "customer")
//    @JoinColumn(name = "cust_id",referencedColumnName = "customer_id") change col name
   private List<Account> accounts;

    @OneToOne(mappedBy = "customer")
    private  KYC kyc;

}
