package com.example.board.Controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.example.board.Entity.Question;
import com.example.board.Entity.SiteUser;
import com.example.board.Form.AnswerForm;
import com.example.board.Form.QuestionForm;
import com.example.board.Service.QuestionService;
import com.example.board.Service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class QuestionController {
	
	@Autowired
	private final QuestionService questionService; 
	private final UserService userService;
	
	//main start
	@GetMapping("/home") 
	public String home() {
		return "index";
	} 
	//main end
	
	// review Controller Start!!!
	@GetMapping("/review/list")
	public String reviewlist(Model model, @RequestParam(value="page", defaultValue="0") int page, 
		 @RequestParam(value="kw", defaultValue="") String kw) {
		Page<Question> paging = this.questionService.reviewGetList(page, kw);
		
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		return "review_list";
	}
	@RequestMapping(value="/review/detail/{id}")
	public String reviewdetail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) throws Exception {
		Question question = this.questionService.reviewGetQuestion(id);
		model.addAttribute("question", question);
		return "review_detail";
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/review/create")
	public String reviewcreate(QuestionForm questionForm) {
		return "review_create";
	}
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/review/create")
	public String reviewcreate(@Valid QuestionForm questionForm, 
			BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		SiteUser siteuser = this.userService.getUser(principal.getName());
		this.questionService.reviewCreate(
				questionForm.getSubject(), 
				questionForm.getContent(),
				siteuser);
		return "redirect:/review/list";
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/review/modify/{id}")
	public String reviewmodify(QuestionForm questionForm, 
			@PathVariable("id") Integer id, Principal principal) {
		Question q = this.questionService.reviewGetQuestion(id);
		if(!q.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		questionForm.setSubject(q.getSubject());
		questionForm.setContent(q.getContent());
		return "review_modify";
	}
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/review/modify/{id}")
	public String reviewmodify(@Valid QuestionForm questionForm, @PathVariable("id") Integer id, 
			BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		Question question = this.questionService.reviewGetQuestion(id);
		if(!question.getAuthor().getUsername().equals(principal.getName())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		this.questionService.reviewModify(question, 
				questionForm.getSubject(), questionForm.getContent());
		return String.format("redirect:/review/detail/%s", id);
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/review/delete/{id}")
	public String reviewdelete(@PathVariable("id") Integer id, Principal principal) {
		Question question = this.questionService.reviewGetQuestion(id);
		if(!question.getAuthor().getUsername().equals(principal.getName())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
		}
		this.questionService.reviewDelete(question);
		return "redirect:/review/list";
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/review/voter/{id}")
	public String reviewvoter(@PathVariable("id") Integer id, Principal principal) {
		Question question = this.questionService.reviewGetQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.questionService.reviewVoter(question, siteUser);
		return String.format("redirect:/review/detail/%s", id);
	}
	
	//  review Controller End!!!
	
	//questionboard start		
		
		@GetMapping("/questionboard/list")
		public String questionboard_list(Model model, @RequestParam(value="page", defaultValue="0") int page,   
			@RequestParam(value="kw", defaultValue="") String kw) {
			Page<Question> paging = this.questionService.questionboard_getList(page, kw);
			model.addAttribute("paging",paging); 
			model.addAttribute("kw",kw);
			return "questionboard_list";
		}
		@RequestMapping(value="/questionboard/detail/{id}")  
		public String questionboard_detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) throws Exception {
			Question question = this.questionService.questionboard_getQuestion(id);
			model.addAttribute("question", question); 
			return "questionboard_detail";
		}
		
		@PreAuthorize("isAuthenticated()") 
		@GetMapping("/questionboard/create")
		public String questionboard_create(QuestionForm questionForm) {
			
			return "questionboard_create";
		}
		@PreAuthorize("isAuthenticated()")
		@PostMapping("/questionboard/create")
		public String questionboard_create(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
			if(bindingResult.hasErrors()) {
				return "questionboard_form";
			}
			SiteUser siteUser = this.userService.getUser(principal.getName());
				this.questionService.questionboard_create(questionForm.getSubject(), questionForm.getContent(), siteUser);
			return "redirect:/questionboard/list";
		}
		
		@PreAuthorize("isAuthenticated()")
		@GetMapping("/questionboard/modify/{id}")
		public String questionboard_modify(QuestionForm questionForm,
				@PathVariable("id") Integer id, Principal principal) {
			Question gunchim = this.questionService.questionboard_getQuestion(id);
			if(!gunchim.getAuthor().getUsername().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
			}
			questionForm.setSubject(gunchim.getSubject());
			questionForm.setContent(gunchim.getContent());

		
			return "questionboard_modify";
		}
		@PreAuthorize("isAuthenticated()")
		@PostMapping("/questionboard/modify/{id}")
		public String questionboard_modify(@Valid  QuestionForm questionForm, @PathVariable("id") Integer id,
				BindingResult bindingResult , Principal principal) {
			if(bindingResult.hasErrors()) {
				return "questionboard_form";
			}
			Question question = this.questionService.questionboard_getQuestion(id);
			if(!question.getAuthor().getUsername().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
			}
			this.questionService.questionboard_modify(question, questionForm.getSubject(), questionForm.getContent());
			
			return String.format("redirect:/questionboard/detail/%s", id);
		}
		
		@PreAuthorize("isAuthenticated()")
		@GetMapping("/questionboard/delete/{id}")
		public String questionboard_delete(@PathVariable("id") Integer id, Principal principal) {
			Question question = this.questionService.questionboard_getQuestion(id);
			if(!question.getAuthor().getUsername().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다.");
			}
			this.questionService.questionboard_delete(question);
			return "redirect:/questionboard/list";
		}
		@PreAuthorize("isAuthenticated()")
		public String questionboard_voter(Principal principal,@PathVariable("id") Integer id ) {
			Question question = this.questionService.questionboard_getQuestion(id);
			SiteUser siteUser = this.userService.getUser(principal.getName());
			this.questionService.questionboard_voter(question, siteUser);
			
			return String.format("redirect:/questionboard/detail/%s", id);
		}
		
		//questionboard end
		
		// qna start
		
		@GetMapping("/qna/list")
		public String qna_List(Model model, @RequestParam(value="page", defaultValue="0") int page) {
			Page<Question> qnapaging = this.questionService.qnaGetList(page);
			model.addAttribute("qnapaging", qnapaging);
			return "qna_list";
		}
		
		@RequestMapping(value="/qna/detail/{id}") 
		public String qna_Detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) throws Exception {
			Question qnaquestion = this.questionService.qnaGetQuestion(id);
			model.addAttribute("qnaquestion", qnaquestion);
			return "qna_detail";
		}
		
		@PreAuthorize("isAuthenticated()")
		@GetMapping(value="/qna/create")
		public String qna_Create(QuestionForm questionForm) {
			return "qna_create";
		}
	
		@PreAuthorize("isAuthenticated()")
		@PostMapping(value="/qna/create")
		public String qna_Create(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
			if(bindingResult.hasErrors()) {
				return "qna_form";
			}
			SiteUser siteuser = this.userService.getUser(principal.getName());
			this.questionService.qnaCreate(questionForm.getSubject(), questionForm.getContent(), siteuser);
			return "redirect:/qna/list";
		}
		
		@PreAuthorize("isAuthenticated()")
		@GetMapping("/qna/modify/{id}")
		public String qna_Modify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
			Question gunchim = this.questionService.qnaGetQuestion(id);
			if(!gunchim.getAuthor().getUsername().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
			}
			questionForm.setSubject(gunchim.getSubject());
			questionForm.setContent(gunchim.getContent());
			return "qna_modify";
		}
		
		@PreAuthorize("isAuthenticated()")
		@PostMapping("/qnamodify/{id}")
		public String qna_Modify(@Valid QuestionForm questionForm, @PathVariable("id") Integer id, BindingResult bindingResult, Principal principal) {
			if (bindingResult.hasErrors()) {
				return "qna_form";
			}
			Question question = this.questionService.qnaGetQuestion(id);
			if (!question.getAuthor().getUsername().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
			}
			this.questionService.qnaModify(question, questionForm.getSubject(), questionForm.getContent());
			return String.format("redirect:/qna/detail/%s", id);
		}
		
		@PreAuthorize("isAuthenticated()")
		@GetMapping("/qna/delete/{id}")
		public String qna_Delete(@PathVariable("id") Integer id, Principal principal) {
			Question question = this.questionService.qnaGetQuestion(id);
			if (!question.getAuthor().getUsername().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다");
			}
			this.questionService.qnaDelete(question);
			return "redirect:/qna/list";
		}
		
		@PreAuthorize("isAuthenticated()")
		@GetMapping("/qna/voter/{id}")
		public String qna_Voter(@PathVariable("id") Integer id, Principal principal) {
			Question question = this.questionService.qnaGetQuestion(id);
			SiteUser siteUser = this.userService.getUser(principal.getName());
			this.questionService.qnaVoter(question, siteUser);
			return String.format("redirect:/qna/detail/%s", id);
		}
		
		// qna end
		
		
	

}
