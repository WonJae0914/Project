package com.example.board.Service;


import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.board.Entity.SiteUser;
import com.example.board.Repository.UserRepository;
import com.example.board.Security.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public SiteUser create(String username, String password, String name, String phone, String birth, String gender) { 
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password)); 
		user.setName(name);
		user.setPhone(phone);
		user.setBirth(birth);
		user.setGender(gender);
		

																												

		this.userRepository.save(user);
		return user;
		
	}
	public SiteUser getUser(String username) {

		Optional<SiteUser> siteUser = this.userRepository.findByusername(username); 
		Optional<SiteUser> siteUserIp = this.userRepository.findByusername(username); 

		if(siteUser.isPresent()) {
			return siteUser.get();
		}else {
			throw new DataNotFoundException("데이터가 없습니다");

		}
	}

}
