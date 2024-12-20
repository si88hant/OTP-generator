package com.example.otp_generator.controller;

import com.example.otp_generator.entity.OTP;
import com.example.otp_generator.entity.OTPRequest;
import com.example.otp_generator.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/otp")
public class OTPController {

    @Autowired
    private OTPService otpService;

    @PostMapping("/generate-otp")
    public OTP  generateOTP(@RequestBody OTPRequest otpRequest) {
        OTP res = otpService.generateOTP(otpRequest);
        return res;
    }

    @GetMapping("/resend-otp/{mobileNumber}")
    public OTP resendOTP(@PathVariable String mobileNumber) {
        return otpService.resendOTP(mobileNumber);
    }

    @PostMapping("/regenerate-otp")
    public OTP regenerateOTP(@RequestBody OTPRequest otpRequest) {
        return otpService.regenerateOTP(otpRequest);
    }

    @GetMapping("/get-all-otps")
    public List<OTP> getAllOTPs() {
        return otpService.getAllOTPs();
    }
}
