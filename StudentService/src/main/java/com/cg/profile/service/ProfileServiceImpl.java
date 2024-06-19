package com.cg.profile.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cg.profile.entity.AdminVerification;
import com.cg.profile.entity.Profile;
import com.cg.profile.exception.ProfileException;
import com.cg.profile.exception.RecordAlreadyExistException;
import com.cg.profile.exception.RecordNotFoundException;
import com.cg.profile.repository.AdminVerificationRepository;
import com.cg.profile.repository.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileRepository profileRepo;

	@Autowired
	PasswordEncoder pen;

	@Autowired
	private AdminVerificationRepository adminVerificationRepository;

	@Autowired
	private AdminAuthenticationServiceImpl adminAuthenticationServiceImpl;

	@Autowired
	RestTemplate restTemplate;

//	@Autowired
//	private ParameterStoreService paramService;

	@Override
	public Profile register(Profile profile)
			throws RecordAlreadyExistException, ProfileException, RecordNotFoundException {
		Optional<Profile> username = profileRepo.findByUserName(profile.getUserName());
		Optional<Profile> phNum = profileRepo.findByPhoneNumber(profile.getPhoneNumber());
		Optional<Profile> email = profileRepo.findByEmail(profile.getEmail());
		Optional<Profile> firstName = profileRepo.findByFirstNameIgnoreCase(profile.getFirstName());
		if (username.isPresent()) {
			throw new RecordAlreadyExistException("Username Already taken. Please try another one!");
		} else if (firstName.isPresent()) {
			throw new RecordAlreadyExistException("First Name Already exists. Please login!");
		} else if (email.isPresent()) {
			throw new RecordAlreadyExistException("Email Already taken. Please try another one!");
		} else if (phNum.isPresent()) {
			throw new RecordAlreadyExistException("Mobile Number Already taken. Please try another one!");
		} else if (!(profile.getRole().equalsIgnoreCase("Admin") || profile.getRole().equalsIgnoreCase("User"))) {
			throw new ProfileException("Invalid ROLE. Accepted roles are: Admin, User");
		}
		profile.setRole(profile.getRole().toUpperCase());
		profile.setPassword(pen.encode(profile.getPassword()));
		adminAuthenticationServiceImpl.sendEmailOnRegistration(profile.getEmail(), profile.getUserName());
		return profileRepo.save(profile);
	}

	@Override
	public Profile updateAdmin(Profile profile, String userName)
			throws RecordNotFoundException, RecordAlreadyExistException {
		Profile pro = profileRepo.findByUserName(userName)
				.orElseThrow(() -> new RecordNotFoundException("User not found with the given username : " + userName));
		List<Profile> findAll = profileRepo.findAll();
		if (pro.getRole().equals("ADMIN")) {
			profile.setId(pro.getId());
			profile.setFirstName(pro.getFirstName());
			profile.setPassword(pro.getPassword());
			profile.setUserName(pro.getUserName());
			profile.setPassword(pro.getPassword());
			int emailCount = 0;
			for (Profile p : findAll) {
				if (p.getEmail().equals(profile.getEmail()) && !pro.getEmail().equals(profile.getEmail())) {
					emailCount++;
				}
			}
			if (emailCount == 0) {
				profile.setEmail(profile.getEmail());
			} else {
				throw new RecordAlreadyExistException("Email Already taken. Please try another one!");
			}
			profile.setRole(pro.getRole());
			int phCount = 0;
			for (Profile p : findAll) {
				if (p.getPhoneNumber().equals(profile.getPhoneNumber())
						&& !pro.getPhoneNumber().equals(profile.getPhoneNumber())) {
					phCount++;
				}
			}
			if (phCount == 0) {
				profile.setPhoneNumber(profile.getPhoneNumber());
			} else {
				throw new RecordAlreadyExistException("Mobile Number Already taken. Please try another one!");
			}
			profile.setPhoneNumber(profile.getPhoneNumber());
			profile.setGender(pro.getGender());
		} else {
			throw new AccessDeniedException("Not eligible to update");
		}
		Profile save = profileRepo.save(profile);
		return save;
	}

	@Override
	public Profile updateStudent(Profile profile, String userName)
			throws RecordNotFoundException, RecordAlreadyExistException {
		Profile pro = profileRepo.findByUserName(userName)
				.orElseThrow(() -> new RecordNotFoundException("User not found with the given username : " + userName));
		List<Profile> findAll = profileRepo.findAll();
		if (pro.getRole().equals("USER")) {
			profile.setId(pro.getId());
			profile.setFirstName(pro.getFirstName());
			profile.setPassword(pro.getPassword());
			profile.setUserName(pro.getUserName());
			profile.setPassword(pro.getPassword());
			int emailCount = 0;
			for (Profile p : findAll) {
				if (p.getEmail().equals(profile.getEmail()) && !pro.getEmail().equals(profile.getEmail())) {
					emailCount++;
				}
			}
			if (emailCount == 0) {
				profile.setEmail(profile.getEmail());
			} else {
				throw new RecordAlreadyExistException("Email Already taken. Please try another one!");
			}
			profile.setRole(pro.getRole());
			int phCount = 0;
			for (Profile p : findAll) {
				if (p.getPhoneNumber().equals(profile.getPhoneNumber())
						&& !pro.getPhoneNumber().equals(profile.getPhoneNumber())) {
					phCount++;
				}
			}
			if (phCount == 0) {
				profile.setPhoneNumber(profile.getPhoneNumber());
			} else {
				throw new RecordAlreadyExistException("Mobile Number Already taken. Please try another one!");
			}
			profile.setPhoneNumber(profile.getPhoneNumber());
			profile.setGender(pro.getGender());
		} else {
			throw new AccessDeniedException("Not eligible to update");
		}
		Profile save = profileRepo.save(profile);
		return save;
	}

	@Override
	public void deleteAdmin(String userName) throws RecordNotFoundException {
		Profile p = profileRepo.findByUserName(userName).orElseThrow(
				() -> new RecordNotFoundException("User not found with the given username to delete: " + userName));
		if (p.getRole().equals("ADMIN")) {
			profileRepo.deleteByUserName(userName);
		} else {
			throw new AccessDeniedException("Access is denied for this user.");
		}
	}

	@Override
	public void deleteStudent(String userName) throws RecordNotFoundException {
		Profile p = profileRepo.findByUserName(userName).orElseThrow(
				() -> new RecordNotFoundException("User not found with the given username to delete: " + userName));
		if (p.getRole().equals("USER")) {
			profileRepo.deleteByUserName(userName);
		} else {
			throw new AccessDeniedException("Access is denied for this user.");
		}
	}

	@Override
	public Profile getByUserName(String userName) throws RecordNotFoundException {
		Profile profile = profileRepo.findByUserName(userName)
				.orElseThrow(() -> new RecordNotFoundException("User not found with the given username : " + userName));
		return profile;
	}

	@Override
	public Profile getByEmail(String email) throws RecordNotFoundException {
		Profile profile = profileRepo.findByEmail(email)
				.orElseThrow(() -> new RecordNotFoundException("User not found with the given email : " + email));
		return profile;
	}

	@Override
	public Profile getByFirstName(String firstName) throws RecordNotFoundException {
		Profile profile = profileRepo.findByFirstNameIgnoreCase(firstName).orElseThrow(
				() -> new RecordNotFoundException("User not found with the given firstname : " + firstName));
		return profile;
	}

	@Override
	public Profile getByStudentName(String userName) throws RecordNotFoundException {
		Profile profile = profileRepo.findByUserName(userName).orElseThrow(
				() -> new RecordNotFoundException("Student not found with the given username : " + userName));
		Profile profile2 = null;
		if (profile.getRole().equals("USER")) {
			profile2 = profile;
		} else {
			throw new AccessDeniedException(userName + " not registered as student");
		}
		return profile2;
	}

	@Override
	public List<Profile> viewAllStudents() throws RecordNotFoundException {
		List<Profile> findAll = profileRepo.findAll();
		List<Profile> findAllStudents = new ArrayList<>();
		for (Profile profile : findAll) {
			if (profile.getRole().equals("USER")) {
				findAllStudents.add(profile);
			}
		}
		if (findAllStudents.isEmpty()) {
			throw new RecordNotFoundException("No students found");
		}
		return findAllStudents;
	}

	@Override
	public String resetPassword(String otp, String newPass) throws RecordNotFoundException {
		Optional<AdminVerification> e = adminVerificationRepository.findByOtp(otp);
		if (e.isEmpty()) {
			throw new RecordNotFoundException("Enter the correct otp");
		}
		String email = e.get().getEmail();
		Profile pr = profileRepo.findByEmail(email).get();
		pr.setPassword(pen.encode(newPass));
		profileRepo.save(pr);
		adminAuthenticationServiceImpl.sendEmailOnPasswordChange(email, pr.getUserName());
		return "Password reset successfully";
	}

//	@Override
//	public double getPercentageForStudent(String username) {
//		Profile profile = profileRepo.findByUserName(username).orElse(null);
//		if (profile != null && "student".equalsIgnoreCase(profile.getRole())) {
//			return profile.getPercentage();
//		} else {
//			return 0.0;
//		}
//	}

//	@Override
//	public void savePercentageForStudent(String username, double percentage) {
//		Profile profile = profileRepo.findByUserName(username).orElse(null);
//		if (profile != null && "student".equalsIgnoreCase(profile.getRole())) {
//			profile.setPercentage(percentage);
//			profileRepo.save(profile);
//		}
//	}

//	public void fetchParameter() {
//	String parameterValue = paramService.getParameterValue("/student-service/database-url");
//	System.out.println("Parameter value: " + parameterValue);
//}
}
