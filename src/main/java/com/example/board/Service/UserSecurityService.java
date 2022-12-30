package com.example.board.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.board.Entity.SiteUser;
import com.example.board.Etc.UserRole;
import com.example.board.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service // service���� �ݵ�� ������̼� �����ؾ� ��!

public class UserSecurityService implements UserDetailsService{
	
	private final UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // �α��� ó�� : ����ó�� ó�� ��. ȸ����ƼƼ�� �ٲ㼭 ����ϴ� ��찡 ����. 
		Optional<SiteUser> _siteUser = this.userRepository.findByusername(username); // �ش� ���������� �ִ� ��ȸ
		
			if(!_siteUser.isPresent()) { // �α��� ó��. �ش� ����ڰ� �ִ��������� ����. ����ڰ� ������ ���⼭ �ɸ�. 
				throw new UsernameNotFoundException("����ڸ� ã�� �� �����ϴ�");
			}
			SiteUser siteUser = _siteUser.get();
			List<GrantedAuthority> auth = new ArrayList<>();
			if("admin".equals(username)) { //  �α���ó��. �ش� ����ڰ� ������. ���⼭���� ó��. 
				auth.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
			}else {
				auth.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
			}
		return new User(siteUser.getUsername(), siteUser.getPassword(), auth);
	}

}
