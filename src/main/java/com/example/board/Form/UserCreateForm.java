package com.example.board.Form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm { 

	@Size(min = 2, max = 16) 
	@NotEmpty(message="사용자 ID는 필수항목입니다")
	private String username;

	@Size(min = 7, max = 17)
	@NotEmpty(message="비밀번호는 필수항목입니다")
	private String password1;
	
	@NotEmpty(message="비밀번호 확인은 필수항목입니다")
	private String password2;
	
	@Size(min = 1, max = 6)
	@NotEmpty(message="이름은 필수항목입니다")
	private String name;	
	
	
	@NotEmpty(message="전화번호는 필수 항목입니다.")
	private String phone;
	
	@NotEmpty(message="생년원일은 필수 항목입니다.")
	private String birth;
}
