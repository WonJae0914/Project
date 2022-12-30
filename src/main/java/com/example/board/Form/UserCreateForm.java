package com.example.board.Form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm { 

<<<<<<< HEAD
	@Size(min = 4, max = 20) 
	@NotEmpty(message="사용자ID는 필수항목임")
=======
	//�޼��� (���� �޼���).
	@Size(min = 4, max = 20) 
	@NotEmpty(message="�����ID�� �ʼ��׸��Դϴ�")
>>>>>>> bd4ca7aca794e19e897fa4ecb2c1ac9bead8d805
	private String username;
	@NotEmpty(message="비밀번호는 필수항목임")
	private String password1;
<<<<<<< HEAD
	@NotEmpty(message="비밀번호 확인은 필수항목임")
	private String password2; 
	@NotEmpty(message="이메일은 필수항목임")
=======
	@NotEmpty(message="��й�ȣ Ȯ���� �ʼ��׸��Դϴ�")
	private String password2;
	@NotEmpty(message="�̸����� �ʼ��׸��Դϴ�")
>>>>>>> bd4ca7aca794e19e897fa4ecb2c1ac9bead8d805
	@Email
	private String email;
	
}
