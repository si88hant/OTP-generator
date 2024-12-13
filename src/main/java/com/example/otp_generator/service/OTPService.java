package com.example.otp_generator.service;

import com.example.otp_generator.entity.OTP;
import com.example.otp_generator.entity.OTPRequest;
import com.example.otp_generator.repository.OTPRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OTPService {

    @Autowired
    private OTPRepo otpRepo;

    public OTP generateOTP(OTPRequest otpRequest) {
        OTP otp = otpRepo.findByMobileNumber(otpRequest.getMobileNumber())
                .orElse(new OTP());
        otp.setMobileNumber(otpRequest.getMobileNumber());
        otp.setOtp(generateRandomOTP());
        otp.setOtpTime(LocalDateTime.now());
        return otpRepo.save(otp);
    }

    private String generateRandomOTP() {
        int min = 100000;
        int max = 999999;
        return String.valueOf(min + new Random().nextInt(max - min + 1));
    }

    public List<OTP> getAllOTPs() {
        return otpRepo.findAll();
    }

}
