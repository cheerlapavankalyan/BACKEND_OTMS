package com.cg.profile.service;

import java.util.Optional;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.cg.profile.entity.AdminVerification;
import com.cg.profile.entity.Profile;
import com.cg.profile.exception.RecordNotFoundException;
import com.cg.profile.repository.AdminVerificationRepository;
import com.cg.profile.repository.ProfileRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class AdminAuthenticationServiceImpl implements AdminAuthenticationService {

	@Autowired
	private ProfileRepository profileRepo;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private AdminVerificationRepository eRepo;

	@Override
	public String sendEmailOnRegistration(String toEmail, String name) throws RecordNotFoundException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

		try {
			helper.setFrom("PruebaExamen <pruebaexamen1581@gmail.com>");
			helper.setTo(toEmail);
			helper.setSubject("Welcome To PruebaExamen");

			String htmlText = "<html><body>" + "<p>Hi <b>" + name + "</b>,<p>"
					+ "<p>Thank you for creating an account in PruebaExamen. </p>"
					+ "<p>Take quiz in our PruebaExamen and test your knowledge.</p>"
					+ "<p>Regards,<br>Team PruebaExamen</p>" + "</body></html>";

			helper.setText(htmlText, true);

			javaMailSender.send(message);
			AdminVerification v = new AdminVerification();
			v.setEmail(toEmail);
			v.setOtp(null);
			eRepo.save(v);

			return "Mail sent successfully";
		} catch (MessagingException e) {
			throw new RecordNotFoundException("Error sending email");
		}
	}

	@Override
	public Boolean verifyOtp(String userEmail, String otp) throws RecordNotFoundException {
		Optional<AdminVerification> e = eRepo.findByEmail(userEmail);
		if (e.isPresent()) {
			AdminVerification e1 = e.get();
			if (e1.getOtp().equals(otp)) {
				eRepo.delete(e1);
				return true;
			} else {
				throw new RecordNotFoundException("Wrong Otp!!");
			}
		} else {
			throw new RecordNotFoundException("Enter Valid Email ID");
		}
	}

	@Override
	public String verifyEmail(String userEmail) {
		Optional<Profile> p = profileRepo.findByEmail(userEmail);
		if (p.isEmpty()) {
			return "User Not Registered with this Email Id";
		} else {
			return null;
		}
	}

	@Override
	public AdminVerification StringSendEmailOnForgotPassword(String toEmail) throws RecordNotFoundException {
		Optional<Profile> p = profileRepo.findByEmail(toEmail);
		if (p.isEmpty()) {
			throw new RecordNotFoundException("Email doesn't exist!!");
		}
		eRepo.deleteAll();
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("PruebaExamen <pruebaexamen@gmail.com>");
		message.setTo(toEmail);
		message.setSubject("Password Change Requested");
		String code = RandomStringUtils.randomNumeric(4);
		String text = "Hi,\n\nA password change has been requested for your account."
				+ " If this was you, please use this OTP to reset your password:\n\n" + code
				+ "\n\nRegards, \nTeam PruebaExamen";
		message.setText(text);
		javaMailSender.send(message);

		AdminVerification v = new AdminVerification();
		v.setEmail(toEmail);
		v.setOtp(code);
		eRepo.save(v);
		return v;
	}

	@Override
	public void sendEmailOnPasswordChange(String toEmail, String name) throws RecordNotFoundException {
		Optional<Profile> p = profileRepo.findByEmail(toEmail);
		if (p.isPresent()) {
			String namee = p.get().getUserName();
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("PruebaExamen <pruebaexamen@gmail.com>");
			message.setTo(toEmail);
			message.setSubject("Password Changed Successfully");
			String text = "Hi " + name + ", \n\nYour password has been reset successfully." + "\n\n"
					+ "You can continue exploring the test." + "\n\n"
					+ "Regards,\nTeam PruebaExamen";
			message.setText(text);
			javaMailSender.send(message);
		}
	}

}
