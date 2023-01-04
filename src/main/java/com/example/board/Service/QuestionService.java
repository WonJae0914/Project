package com.example.board.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.board.Entity.Question;
import com.example.board.Entity.SiteUser;
import com.example.board.Form.AnswerForm;
import com.example.board.Form.QuestionForm;

import com.example.board.Repository.InformationQuestionRepository;
import com.example.board.Repository.NoticeRepository;
import com.example.board.Repository.QnaRepository;
import com.example.board.Repository.QuestionRepository;
import com.example.board.Repository.QuestionboardRepository;
import com.example.board.Repository.ReviewQuestionRepository;
import com.example.board.Security.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	
	private final QuestionRepository q; 
	private final ReviewQuestionRepository rq;
	private final QuestionboardRepository qbr; 
	private final InformationQuestionRepository informationRepository;

	private final NoticeRepository nr;
	private final UserService userService;
	private final QnaRepository qnaRepository;
										
	// review QuestionService Start	!!!
	
	public Page<Question> reviewGetList(int page, String kw){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.rq.findAllByKeyword(kw, pageable);
	}
	public Question reviewGetQuestion(Integer id) throws DataNotFoundException {
		Optional<Question> question = this.rq.findById(id);
		if(question.isPresent()) {
			return question.get();
		}else {
			throw new DataNotFoundException("그런거 없음");
		}
	}
	public void reviewCreate(String subject, String content, SiteUser user) {
		Question q1 = new Question();
		q1.setSubject(subject);
		q1.setContent(content);
		q1.setCreateDate(LocalDateTime.now());
		q1.setAuthor(user);
		this.rq.save(q1);		
	}
	public void reviewModify(Question question, String subject, String content) {
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		this.rq.save(question);
	}
	public void reviewDelete(Question question) {
		this.rq.delete(question);
	}
	public void reviewVoter(Question question, SiteUser siteUser) {
		question.getVoter().add(siteUser);
		this.rq.save(question);
	}
	// review QuestionService end!!!

	//questionboard questionservice start
		public Page<Question> questionboard_getList(int page, String kw){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); 
		return this.qbr.findAllByKeyword(kw, pageable);
		}

	public Question questionboard_getQuestion(Integer id) throws DataNotFoundException {
		Optional<Question> question = this.qbr.findById(id);
		if(question.isPresent()) {
			return question.get();
		}else {
			throw new DataNotFoundException("question not found");
		}
	}
		public void questionboard_create(String subject, String content, SiteUser user) {
			Question q1 = new Question();
			q1.setSubject(subject);
			q1.setContent(content);
			q1.setCreateDate(LocalDateTime.now());
			q1.setAuthor(user);
			this.qbr.save(q1);
			
		}
		public void questionboard_modify(Question question, String subject, String content) {
			question.setSubject(subject);
			question.setContent(content);
			question.setModifyDate(LocalDateTime.now());
			this.qbr.save(question);
			
		}
	public void questionboard_delete(Question question) {
		this.qbr.delete(question);
	}

	public void questionboard_voter(Question question, SiteUser siteUser) {
		question.getVoter().add(siteUser);
		this.qbr.save(question);
	}
	//questionboard questionservice end
		
	//information sharing start
	public Page<Question> getInfoList(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));		
		return this.informationRepository.findAllByKeyword(kw, pageable);	
		}
	
	public void getInforCreate(String subject, String content, SiteUser user) {
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setCreateDate(LocalDateTime.now());
		question.setAuthor(user);
		this.informationRepository.save(question);
	}
		
	public Question getInformation(Integer id) throws DataNotFoundException {
		Optional<Question> question = this.informationRepository.findById(id);
		if(question.isPresent()) {
			return question.get();
		}else {
			throw new DataNotFoundException("질문이 없습니다");
		}
	}
		
	public void getInfoModify(Question question, String subject, String content) {
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());			
		this.q.save(question);
	}

	public void getInfoDelete(Question question) {
		this.q.delete(question); 
	}

	public void getInforVoter(Question question, SiteUser siteUser) {
		question.getVoter().add(siteUser);
		this.q.save(question);
	}
	//informationSharing end
	
			

			
		
		// qna start
		
		public Page<Question> qnaGetList(int page) {
			List<Sort.Order> sorts = new ArrayList<>();
			sorts.add(Sort.Order.desc("createDate"));
			Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
			return this.qnaRepository.findAll(pageable);
		}
				
		public Question qnaGetQuestion(Integer id) throws DataNotFoundException {
			Optional<Question> question = this.qnaRepository.findById(id);
			if(question.isPresent()) {
				return question.get();
			} else {
				throw new DataNotFoundException("자주묻는질문이 없습니다");
			}
		}

		public void qnaCreate(String subject, String content) {
//		, SiteUser user{
			Question question = new Question();
			question.setSubject(subject);
			question.setContent(content);
			question.setCreateDate(LocalDateTime.now());
//			question.setAuthor(user);
			this.qnaRepository.save(question);
		}
				
		public void qnaModify(Question question, String subject, String content) {
			question.setSubject(subject);
			question.setContent(content);
			question.setModifyDate(LocalDateTime.now());
			this.qnaRepository.save(question);
		}

		public void qnaDelete(Question question) {
			this.qnaRepository.delete(question);
		}

		public void qnaVoter(Question question, SiteUser siteUser) {
			question.getVoter().add(siteUser);
			this.qnaRepository.save(question);
		}
				
		// qna end	
		
		// 221230 - add notice start - updated by kd
		
		public Page<Question> getList(int page, String kw){
			List<Sort.Order> sorts = new ArrayList<>();
			sorts.add(Sort.Order.desc("createDate"));
			Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
			return this.nr.findAllByKeyword(kw, pageable);
		}
		public Question getQuestion(Integer id) throws DataNotFoundException {
			Optional<Question> question = this.nr.findById(id);
			if(question.isPresent()) {
				return question.get();
			}else {
				throw new DataNotFoundException("그런거 없음");
			}
		}
		public void create(String subject, String content, SiteUser user) {
			Question q1 = new Question();
			q1.setSubject(subject);
			q1.setContent(content);
			q1.setCreateDate(LocalDateTime.now());
			q1.setAuthor(user);
			this.nr.save(q1);		
		}
		public void modify(Question question, String subject, String content) {
			question.setSubject(subject);
			question.setContent(content);
			question.setModifyDate(LocalDateTime.now());
			this.nr.save(question);
		}
		public void delete(Question question) {
			this.nr.delete(question);
		}
		public void voter(Question question, SiteUser siteUser) {
			question.getVoter().add(siteUser);
			this.nr.save(question);
		}
		
		// 221230 - add notice end - updated by kd
}
		
	

