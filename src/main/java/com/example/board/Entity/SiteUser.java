package com.example.board.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {
	
	//id(Ű������ ���), user-name, password, e-mail
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true) // unique = true : �ݵ�� �Է� �޾ƾ� �� �� ���
	private String username;
	
	private String password;
	
	@Column(unique = true)
	private String email;
	

}
