package kh.edu.cstad.mbapi.domain;

import jakarta.persistence.*;
        import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class KYC {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Column(unique = true,nullable = false,length = 13)
    private  String nationalCardId;

    @Column(nullable = false)
    private Boolean isVerify;

    @Column(nullable = false)
    private Boolean isDeleted;

    @OneToOne
    private  Customer customer;




}
