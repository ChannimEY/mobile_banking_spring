package kh.edu.cstad.mbapi.dto;

import java.math.BigDecimal;

public record UpdateAccountRequest(
        String accNum,
        BigDecimal overLimit

) {
}
