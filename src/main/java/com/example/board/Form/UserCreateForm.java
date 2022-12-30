package com.example.board.Form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {


	@Size(min = 4, max = 20)
	@NotEmpty(message="�����ID�� �ʼ��׸��Դϴ�")
	private String username;
	@NotEmpty(message="��й�ȣ�� �ʼ��׸��Դϴ�")
	private String password1;
	@NotEmpty(message="��й�ȣ Ȯ���� �ʼ��׸��Դϴ�")
	private String password2;
	@NotEmpty(message="�̸����� �ʼ��׸��Դϴ�")
	@Email
	private String email;
	
}
