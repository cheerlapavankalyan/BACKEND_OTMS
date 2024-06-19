package com.cg.profile.service;

import java.util.List;

import com.cg.profile.entity.Profile;
import com.cg.profile.exception.ProfileException;
import com.cg.profile.exception.RecordAlreadyExistException;
import com.cg.profile.exception.RecordNotFoundException;

public interface ProfileService {

	Profile register(Profile profile) throws RecordAlreadyExistException, ProfileException, RecordNotFoundException;

	Profile updateAdmin(Profile profile, String username) throws RecordNotFoundException, RecordAlreadyExistException;

	Profile updateStudent(Profile profile, String username) throws RecordNotFoundException, RecordAlreadyExistException;

	void deleteAdmin(String userName) throws RecordNotFoundException;

	void deleteStudent(String userName) throws RecordNotFoundException;

	Profile getByUserName(String userName) throws RecordNotFoundException;
	
	Profile getByEmail(String email) throws RecordNotFoundException;

	Profile getByStudentName(String userName) throws RecordNotFoundException;
	
	Profile getByFirstName(String firstName) throws RecordNotFoundException;

	List<Profile> viewAllStudents() throws RecordNotFoundException;
	
	String resetPassword(String otp, String newPass) throws RecordNotFoundException;
	
//	void savePercentageForStudent(String username, double percentage);
	
}
