package com.example.otp_generator.service;

import com.example.otp_generator.entity.OTP;
import com.example.otp_generator.entity.OTPRequest;
import com.example.otp_generator.repository.OTPRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {

    @Autowired
    private OTPRepo otpRepo;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String OTP_CACHE_PREFIX = "OTP_";


    public OTP generateOTP(OTPRequest otpRequest) {

        String key = OTP_CACHE_PREFIX + otpRequest.getMobileNumber();

        //check if OTP is cached
        try{
            Object cachedObject = redisTemplate.opsForValue().get(key);
            if (cachedObject != null) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule()); // Register the module to handle Java 8 date/time
                OTP cachedOtp = mapper.convertValue(cachedObject, OTP.class);
                return cachedOtp;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //generate new otp if not found in cache
        OTP otp = otpRepo.findByMobileNumber(otpRequest.getMobileNumber())
                .orElse(new OTP());
        otp.setMobileNumber(otpRequest.getMobileNumber());
        otp.setOtp(generateRandomOTP());
        otp.setOtpTime(LocalDateTime.now());


        //save otp to db and cache
        OTP savedOTP = otpRepo.save(otp);
        try{
            redisTemplate.opsForValue().set(key, savedOTP, 10, TimeUnit.MINUTES);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return savedOTP;

    }

    public OTP resendOTP(String mobileNumber) {
        String key = OTP_CACHE_PREFIX + mobileNumber;

        //check if OTP is in cache
        OTP cachedOTP = (OTP) redisTemplate.opsForValue().get(key);
        if (cachedOTP != null) {
            return cachedOTP;
        }

        //fetch from the db if not found in the cache
        OTP otp = otpRepo.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new RuntimeException("No OTP found. Please generate a new OTP."));

        return otp;
    }


    public OTP regenerateOTP(OTPRequest otpRequest) {
        //check if input mobile number is present in db?
        //if no then generate a new otp - store in cache and db
        //if yes then update the db record and cache

        String key = OTP_CACHE_PREFIX + otpRequest.getMobileNumber();

        OTP otp = otpRepo.findByMobileNumber(otpRequest.getMobileNumber())
                .orElse(new OTP());

        otp.setOtp(generateRandomOTP());
        otp.setOtpTime(LocalDateTime.now());
        otp.setMobileNumber(otpRequest.getMobileNumber());  //for the case when mobile no doesnt exists in db

        OTP savedOTP = otpRepo.save(otp);

        // Update Redis cache
        try {
            redisTemplate.opsForValue().set(key, savedOTP, 10, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update Redis cache.");
        }

        return savedOTP;
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
