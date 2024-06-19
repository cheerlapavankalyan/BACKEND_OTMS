package com.cg.profile.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.profile.entity.Profile;
import com.cg.profile.exception.RecordAlreadyExistException;
import com.cg.profile.exception.RecordNotFoundException;
import com.cg.profile.service.ProfileServiceImpl;

import jakarta.validation.Valid;

@RestController

public class ProfileController {

	@Autowired
	private ProfileServiceImpl profileService;

	@PutMapping("/updateAdmin/{userName}")
	public ResponseEntity<Profile> updateAdmin(@Valid @RequestBody Profile profile, @PathVariable String userName)
			throws RecordNotFoundException, RecordAlreadyExistException {
		Profile p = profileService.updateAdmin(profile, userName);
		return new ResponseEntity<>(p, HttpStatus.OK);
	}

	@PutMapping("/updateStudent/{userName}")
	public ResponseEntity<Profile> updateStudent(@Valid @RequestBody Profile profile, @PathVariable String userName)
			throws RecordNotFoundException, RecordAlreadyExistException {
		Profile p = profileService.updateStudent(profile, userName);
		return new ResponseEntity<>(p, HttpStatus.OK);
	}

	@DeleteMapping("/deleteAdmin/{userName}")
	public ResponseEntity<?> deleteAdmin(@PathVariable String userName) throws RecordNotFoundException {
		profileService.deleteAdmin(userName);
		return ResponseEntity.ok("Deleted admin profile");
	}

	@DeleteMapping("/deleteStudent/{userName}")
	public ResponseEntity<?> deleteStudent(@PathVariable String userName) throws RecordNotFoundException {
		profileService.deleteStudent(userName);
		return ResponseEntity.ok("Deleted student profile");
	}

	@GetMapping("/byUserName/{userName}")
	public ResponseEntity<Profile> getProfileByUserName(@PathVariable String userName) throws RecordNotFoundException {
		Profile p = profileService.getByUserName(userName);
		return new ResponseEntity<>(p, HttpStatus.OK);
	}

	@GetMapping("/byEmail/{email}")
	public ResponseEntity<Profile> getProfileByEmail(@PathVariable String email) throws RecordNotFoundException {
		Profile p = profileService.getByEmail(email);
		return new ResponseEntity<>(p, HttpStatus.OK);
	}

	@GetMapping("/byFirstName/{firstName}")
	public ResponseEntity<Profile> getProfileByFirstName(@PathVariable String firstName)
			throws RecordNotFoundException {
		Profile p = profileService.getByFirstName(firstName);
		return new ResponseEntity<>(p, HttpStatus.OK);
	}

	@GetMapping("/byStudentName/{userName}")
	public ResponseEntity<Profile> getProfileByStudentName(@PathVariable String userName)
			throws RecordNotFoundException {
		Profile p = profileService.getByStudentName(userName);
		return new ResponseEntity<>(p, HttpStatus.OK);
	}

	@GetMapping("/viewAllStudents")
	public ResponseEntity<List<Profile>> viewAllStudents() throws RecordNotFoundException {
		List<Profile> l = profileService.viewAllStudents();
		return new ResponseEntity<>(l, HttpStatus.OK);
	}

//	@PostMapping("/scoree/{username}/{percentage}")
//	public ResponseEntity<Double> saveScoreForStudent(@PathVariable String username, @PathVariable double percentage) {
//		profileService.savePercentageForStudent(username, percentage);
//		return ResponseEntity.ok(percentage);
//	}

//	@GetMapping("/score/{userName}")
//	public ResponseEntity<Double> getScoreForStudent(@PathVariable String userName) {
//		double score = profileService.getPercentageForStudent(userName);
//		return new ResponseEntity<>(score, HttpStatus.OK);
//	}
}
