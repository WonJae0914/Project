package com.example.board.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.board.Entity.Answer;
import com.example.board.Entity.Question;
import com.example.board.Entity.SiteUser;
import com.example.board.Repository.AnswerRepository;
import com.example.board.Repository.Questionboard_AnswerRepository;
import com.example.board.Repository.ReviewAnswerRepository;
import com.example.board.Security.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {

	private final AnswerRepository answerRepository;
	
	//review AnswerService start!!!
	private final ReviewAnswerRepository reviewAnswerRepository;
	public Answer reviewCreate(Question question, String content, SiteUser author) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setAuthor(author);
		this.reviewAnswerRepository.save(answer);
		return answer;
	}
	public Answer reviewGetAnswer(Integer id) {
		Optional<Answer> answer = this.reviewAnswerRepository.findById(id);
		if(answer.isPresent()) {
			return answer.get();
		}else {
			throw new DataNotFoundException("그런거 없음");
		}
	}
	public void reviewModify(Answer answer, String content) {
		answer.setContent(content);
		answer.setModifyDate(LocalDateTime.now());
		this.reviewAnswerRepository.save(answer);
	}
	public void reviewDelete(Answer answer) {
			this.reviewAnswerRepository.delete(answer);
	}
	public void reviewVoter(Answer answer, SiteUser siteUser) {
		answer.getVoter().add(siteUser);
		this.reviewAnswerRepository.save(answer);
	}
	//review AnswerService end!!!
	
	//questionboard answerservice start

		private final Questionboard_AnswerRepository qsr;
		
		public Answer questionboard_create(Question question, String content, SiteUser author) { 

			Answer answer = new Answer();
			answer.setContent(content);
			answer.setCreateDate(LocalDateTime.now());
			answer.setQuestion(question); 
			answer.setAuthor(author);
			this.qsr.save(answer);
			return answer;
		}
		public Answer questionboard_getAnswer(Integer id) {
			Optional<Answer> answer = this.qsr.findById(id);
			if(answer.isPresent()) {
				return answer.get();
			}
			else {
				throw new DataNotFoundException("그런거 없음 ㅇㅇ");
			}
		}
		public void questionboard_modify(Answer answer, String content) {
			answer.setContent(content);
			answer.setModifyDate(LocalDateTime.now()); 
			this.qsr.save(answer);
		}
		public void questionboard_delete(Answer answer) {
			// TODO Auto-generated method stub
			this.qsr.delete(answer);
		}
		public void questionboard_voter(Answer answer, SiteUser siteUser) {
			// TODO Auto-generated method stub
			answer.getVoter().add(siteUser);
			this.qsr.save(answer);
		}
	//questionboard answerservice end
	

}
