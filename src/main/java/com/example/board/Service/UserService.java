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
	
	public SiteUser create(String username, String email, String password) { 
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
		user.setPassword(passwordEncoder.encode(password)); 
															
															 
		this.userRepository.save(user);
		return user;
		
	}
	
	public SiteUser getUser(String username) {
		Optional<SiteUser> siteUser = this.userRepository.findByusername(username); 
		
		if(siteUser.isPresent()) {
			return siteUser.get();
		}else {
			
			throw new DataNotFoundException("없습니다");
		}
	}

}
