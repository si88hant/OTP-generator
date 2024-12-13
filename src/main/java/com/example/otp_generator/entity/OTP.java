package com.example.otp_generator.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_otp")
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mobile_number", nullable = false, length = 10, unique = true)
    private String mobileNumber;

    @Column(name = "otp", nullable = false, length = 6)
    private String otp;

    @Column(name = "otp_time", nullable = false)
    private LocalDateTime otpTime;

    // Default constructor
    public OTP() {}

    // Constructor with all fields
    public OTP(Long id, String mobileNumber, String otp, LocalDateTime otpTime) {
        this.id = id;
        this.mobileNumber = mobileNumber;
        this.otp = otp;
        this.otpTime = otpTime;
    }

    // Getter for id
    public Long getId() {
        return id;
    }

    // Setter for id
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for mobileNumber
    public String getMobileNumber() {
        return mobileNumber;
    }

    // Setter for mobileNumber
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    // Getter for otp
    public String getOtp() {
        return otp;
    }

    // Setter for otp
    public void setOtp(String otp) {
        this.otp = otp;
    }

    // Getter for otpTime
    public LocalDateTime getOtpTime() {
        return otpTime;
    }

    // Setter for otpTime
    public void setOtpTime(LocalDateTime otpTime) {
        this.otpTime = otpTime;
    }
}
