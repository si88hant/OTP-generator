package com.example.otp_generator.repository;

import com.example.otp_generator.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepo extends JpaRepository<OTP, String> {
    Optional<OTP> findByMobileNumber(String mobileNumber);
}
