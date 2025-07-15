package kh.edu.cstad.mbapi.controller;

import kh.edu.cstad.mbapi.service.KYCService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kyc")
@RequiredArgsConstructor
public class KYCController {
    private final KYCService kycService;

    @PatchMapping("/verify/{nationalCardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void verifyKyc(@PathVariable String nationalCardId) {

        kycService.verifyKyc(nationalCardId);
    }
}
