package com.example.board;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.board.Service.QuestionService;


@SpringBootTest
class BoardApplicationTests {
	
	@Autowired
//	private QuestionRepository q;
	private QuestionService q;

//	@Test
//	void contextLoads() {
////		Question q1 = new Question();
////		q1.setSubject("���ѹα�vs��������");
////		q1.setContent("���ѹα��¸�");
////		q1.setCreateDate(LocalDateTime.now());
////		this.q.save(q1);
////		
////		Question q2 = new Question();
////		q2.setSubject("���ѹα�vs�����");
////		q2.setContent("������¸�");
////		q2.setCreateDate(LocalDateTime.now());
////		this.q.save(q2);
//		
//		//findAll : ������ȸ. 
//		List<Question> all = this.q.findAll();
//		//assertEquals : (��밪, ������)
//		
//		assertEquals(2, all.size());
//	
//	}
	
	@Test
	void jpaTest() {
		// findbyId : id���� �������� �����͸� ��ȸ.
		// id���� ��ȸ�ϱ� ���� 2���� �ν��Ͻ��� �ʿ�. 
		// 1. ��ȸ�� ����� �޾� �� �ν��Ͻ�
		// 2. ������ �����͸� �Է� ���� �ν��Ͻ�
		//  -> Null ó�� ������ Optional �̶�� Ŭ������ ����ϱ� ����
		
//	Optional<Question> oq = this.q.findById(1);
//	if(oq.isPresent()){
//		Question q = oq.get();
//		assertEquals("�������", q.getSubject());
//	}
//		Question q = new Question();
//		for(int i=1; i<=300; i++) {
//			String subject = String.format("����Ʈ �������Դϴ�:[%03d]",i);
//			String content = "���빰";
//			this.q.create(subject,content);		
//		}
	}
}