package kh.edu.cstad.mbapi.service.Impl;

import kh.edu.cstad.mbapi.domain.KYC;
import kh.edu.cstad.mbapi.repository.KYCRepository;
import kh.edu.cstad.mbapi.service.KYCService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class KYCServiceImpl implements KYCService {
    private final KYCRepository kycRepository;

    @Override
    public void verifyKyc(String nationalCardId) {
        KYC kyc = kycRepository.findByNationalCardId(nationalCardId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "KYC not found"));
        kyc.setIsVerify(true);
        kycRepository.save(kyc);
    }
}
