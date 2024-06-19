package com.cg.profile.service;

import com.cg.profile.entity.AdminVerification;
import com.cg.profile.exception.RecordNotFoundException;

public interface AdminAuthenticationService {

	public String sendEmailOnRegistration(String toEmail, String name) throws RecordNotFoundException;

	public Boolean verifyOtp(String adminEmail, String otp) throws RecordNotFoundException;

	public String verifyEmail(String adminEmail);

	public AdminVerification StringSendEmailOnForgotPassword(String toEmail) throws RecordNotFoundException;

	public void sendEmailOnPasswordChange(String toEmail, String name) throws RecordNotFoundException;

}
