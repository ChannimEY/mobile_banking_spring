package kh.edu.cstad.mbapi.dto;

public record CreateCustomerRequest(
        String fullname,
        String gender,
        String email,
        String phoneNumber,
        String remark

) {
}
