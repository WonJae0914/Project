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
import com.example.board.Repository.QuestionRepository;
import com.example.board.Repository.QuestionboardRepository;
import com.example.board.Repository.ReviewQuestionRepository;
import com.example.board.Security.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	
	private final QuestionRepository q; 
										
	// review QuestionService Start	!!!
	private final ReviewQuestionRepository rq;
	private final QuestionboardRepository qbr; 
	private final InformationQuestionRepository informationRepository;
	private final QuestionService questionService;
	private final UserService userService;
	
	
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
			// 파라미터 3개의 이유
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
		
		//fassion inpomation start
		
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
		
		public Question getInfoDetail(Integer id) throws DataNotFoundException {
			Optional<Question> question = this.informationRepository.findById(id);
			if(question.isPresent()) {
				return question.get();
			}else {
				throw new DataNotFoundException("질문이 없습니다");
			}
		}
		//fassion inpomation end	
		
		// InformationSharing start
		@GetMapping("/sharing")
		public String InfoList(Model model, @RequestParam(value="page", defaultValue="0") int page, 
					@RequestParam(value="kw", defaultValue="") String kw) { 
				Page<Question> paging = this.questionService.getInfoList(page, kw);
				model.addAttribute("paging", paging);
				model.addAttribute("kw", kw);
				return "informationSharing"; 
			}
		
		@RequestMapping(value="/Informationdetail/{id}") // value 를 적은 이유 : id를 파라미터로 받기 위해  
		public String InforDetail(Model model, @PathVariable("id") Integer id, AnswerForm answerform) throws Exception { // id : 게시글 분류 기준. 게시글을 분류하기 위해 id값 부여한 것
			Question question = this.questionService.getInfoDetail(id);  // model 클래스 : 뷰에다가 요청한 내용을 던져주기 위해 사용한 클래스 
			model.addAttribute("Information", question);
			return "sharing_detail";
		}
		
		@GetMapping("/sharingform")
		public String InforCreate(QuestionForm questionForm){
			return "information_create";
		}
		
		@PostMapping("/sharingform")
		public String InforCreate(@Valid QuestionForm questionForm, 
				BindingResult bindingResult, Principal principal){ // principal : 로그인한 사용자 정보 가지고 오는 것. 
			if(bindingResult.hasErrors()) {
				return "sharing_form";
			}
			SiteUser siteuser = this.userService.getUser(principal.getName());
			
			this.questionService.getInforCreate(
					questionForm.getSubject(), 
					questionForm.getContent(), 
					siteuser);
			return "redirect:/sharing";
		}
		
		
	
		//InformationSharing end
		
		
}
