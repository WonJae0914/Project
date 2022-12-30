package com.example.board.Security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason = "entity not found") // �������� ����.
public class DataNotFoundException extends RuntimeException{
	private static final long serialVersionUID= 1L; // ����ȭ��� ���� : �ڹ��� ��ü�� ����Ʈ �迭�� ��ȯ�Ͽ� ����, �޸�, DB���� �����ϴ� ����. ���� üũ�� ���� ���
	public DataNotFoundException(String message) {
		super(message);
	}
	
}