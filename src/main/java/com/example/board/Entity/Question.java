package com.example.board.Entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.domain.Sort.Order;

import lombok.Getter;
import lombok.Setter;

// �������ÿ�ƼƼ(TABLE)
@Getter
@Setter
@Entity
public class Question {
	
	@Id // Ű ���� �ο�. 1���� ���� 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; 
	
	@Column(length = 200)
	private String subject;
	
	@Column(columnDefinition="TEXT")
	private String content;
	
	private LocalDateTime createDate;
	
	@OneToMany(mappedBy="question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList; // �÷����� ���� : �����迭�̱� ���� . ������ �ν��Ͻ��� ������ŭ �ҷ��� �� �ֵ��� �ϱ� ���� 
	
	@ManyToOne
	private SiteUser author; 
	
	private LocalDateTime modifyDate; // �ۼ��� �� ���� �� �����ð� �Է�. ó�� ���� �η� �ؾ� ��. �ۼ����� �����ð��� ����ϸ� �ȵǱ� ����
	
	@ManyToMany
	Set<SiteUser> voter;



}
