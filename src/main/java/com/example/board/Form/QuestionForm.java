package com.example.board.Form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
	
<<<<<<< HEAD
	@NotEmpty(message="제목은 필수 항목입니다.")
	@Size(max=200)
	private String subject;
	
	@NotEmpty(message="내용은 필수 항목입니다")
=======
	@NotEmpty(message="제목을 입력을 해주세요")
	@Size(max=200)
	private String subject;
	
	@NotEmpty(message="내용을 입력해주세요")
>>>>>>> bd4ca7aca794e19e897fa4ecb2c1ac9bead8d805
	private String content;

}
