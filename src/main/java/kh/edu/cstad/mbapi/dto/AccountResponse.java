package kh.edu.cstad.mbapi.dto;


import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record AccountResponse(
        String accNum,
        String customerName,
        String accountTypeName,
        BigDecimal overLimit
) {}

