package com.example.board.Form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {

	@NotEmpty(message="제목은 필수 입력사항입니다.")
	@Size(max=200)
	private String subject;
	
	@NotEmpty(message="내용을 입력해 주세요.")
	private String content;
}