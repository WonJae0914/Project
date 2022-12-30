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
	
	public SiteUser create(String username, String email, String password) { // ���̵� ������ ��������, �н����� �̸����� ���ο����� �Ѵ�. 
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // ��ť��Ƽ���� �ҷ����� Ŭ����. �н����� ��ȣȭ �ϱ� ���� ���� Ŭ����. 
		user.setPassword(passwordEncoder.encode(password)); //  ��й�ȣ�� ���̷�Ʈ�� ����� �ʴ´�. ��ȣȭ�Ͽ� ����. ��������. ������ ��ť��Ƽ�� ����ϰ� �ֱ� ��
															// �� ������ �ڵ��� ����ȭ�ؼ� ��� ��. ��ȣȭ �Ҷ����� ��� �ۼ��ϱ� ���ŷӱ� ����
															// BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); ��� �ʵ忡 private final PasswordEncoder passwordEncoder; ����. ������ ��ť��Ƽ�� @Bean �����ؾ� �� 
		this.userRepository.save(user);
		return user;
		
	}
	
	public SiteUser getUser(String username) {
		Optional<SiteUser> siteUser = this.userRepository.findByusername(username); // Optional : null����Ʈ �̽��� �����ϱ� ���� Ŭ������ ��� (1.7���� ���Ͽ����� ����)
		
		if(siteUser.isPresent()) {
			return siteUser.get();
		}else {
			
			throw new DataNotFoundException("�����Ͱ� �����ϴ�");
		}
	}

}
