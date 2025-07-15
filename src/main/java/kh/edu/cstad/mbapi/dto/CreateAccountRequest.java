package kh.edu.cstad.mbapi.dto;

import java.math.BigDecimal;

public record CreateAccountRequest(
         String accNum,
        BigDecimal overLimit,
         Integer   customerId,
         Integer accountTypeId

) {
}
