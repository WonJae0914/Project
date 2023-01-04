package com.example.board.Controller;

import java.security.Principal;

import javax.validation.Valid;

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
import org.springframework.web.server.ResponseStatusException;

import com.example.board.Entity.Answer;
import com.example.board.Entity.Question;
import com.example.board.Entity.SiteUser;
import com.example.board.Form.AnswerForm;
import com.example.board.Security.DataNotFoundException;
import com.example.board.Service.AnswerService;
import com.example.board.Service.QuestionService;
import com.example.board.Service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AnswerController {

	private final QuestionService questionService;
	private final AnswerService answerService;
	private final UserService userService;
	
	// review answerController Start!!!
		@PreAuthorize("isAuthenticated()")
		@PostMapping("/reviewanswer/create/{id}")
		public String reviewCreateAnswer(Model model, @PathVariable("id") Integer id, 
				@RequestParam String content ,@Valid AnswerForm answerForm, 
				BindingResult bindingResult, Principal principal) throws DataNotFoundException {
				Question review = this.questionService.reviewGetQuestion(id);
				SiteUser siteuser = this.userService.getUser(principal.getName());
				if(bindingResult.hasErrors()) {
					model.addAttribute("review", review);
					return "review_detail";
				}
				this.answerService.reviewCreate(review, answerForm.getContent(), siteuser);	
			return String.format("redirect:/review/detail/%s", id);}
		@PreAuthorize("isAuthenticated()")
		@GetMapping("/reviewanswer/modify/{id}")
		public String reviewAnswerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
			Answer answer = this.answerService.reviewGetAnswer(id);
			if(!answer.getAuthor().getUsername().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한없음");
			}
			answerForm.setContent(answer.getContent());
			return "review_answer_form";
		}
		@PreAuthorize("isAuthenticated()")
		@PostMapping("/reviewanswer/modify/{id}")
		public String reviewAnswerModify(AnswerForm answerForm, @PathVariable("id") Integer id,
				Principal principal, BindingResult bindingResult) {
			if(bindingResult.hasErrors()) {
				return "review_answer_form";
			}
			Answer answer = this.answerService.reviewGetAnswer(id);
			if(!answer.getAuthor().getUsername().equals(principal.getName())){
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한 없음");
			}
			this.answerService.reviewModify(answer, answerForm.getContent());
			return String.format("redirect:/review/detail/%s", answer.getQuestion().getId());
		}
		@PreAuthorize("isAuthenticated()")
		@GetMapping("/reviewanswer/delete/{id}")
		public String reviewAnswerDelete(@PathVariable("id") Integer id, Principal principal) {
			Answer answer = this.answerService.reviewGetAnswer(id);
			if(!answer.getAuthor().getUsername().equals(principal.getName())){
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
			}
			this.answerService.reviewDelete(answer);
			return String.format("redirect:/review/detail/%s", answer.getQuestion().getId());
		}
		@PreAuthorize("isAuthenticated()")
		@GetMapping("/reviewanswer/voter/{id}")
		public String reviewAnswerVoter(@PathVariable("id") Integer id, Principal principal) {
			Answer answer = this.answerService.reviewGetAnswer(id);
			SiteUser siteUser = this.userService.getUser(principal.getName());
			this.answerService.reviewVoter(answer, siteUser);
			
			return String.format("redirect:/review/detail/%s", answer.getQuestion().getId());
			
		}
		// review answerController END!!!
		
		//qustionboard_answer start
		
		@PreAuthorize("isAuthenticated()")
		@PostMapping("/questionboard/answer/create/{id}")
		public String questionboard_createAnswer(Model model, @PathVariable("id") Integer id, 
				@RequestParam String content, @Valid AnswerForm answerForm,
				BindingResult bindingResult, Principal principal) throws DataNotFoundException {
			Question question = this.questionService.questionboard_getQuestion(id);
			SiteUser siteuser = this.userService.getUser(principal.getName());
			if(bindingResult.hasErrors()) {
				model.addAttribute("question", question);
				return "questionboard_detail";
			}
			this.answerService.questionboard_create(question, answerForm.getContent(), siteuser);	
		
		return String.format("redirect:/questionboard/detail/%s", id); 
		}
		
		@PreAuthorize("isAuthenticated()")
		@GetMapping("/questionboard/answer/modify/{id}")
		public String questionboard_answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
			
			Answer answer = this.answerService.questionboard_getAnswer(id);
			if(!answer.getAuthor().getUsername().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"권한 없음");	
			}
			answerForm.setContent(answer.getContent());
			
			return "questionboard_answer_form";
		}
		
		@PreAuthorize("isAuthenticated()")
		@PostMapping("/questionboard/answer/modify/{id}")
		public String questionboard_answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, BindingResult bindingResult, Principal principal) {
			if(bindingResult.hasErrors()) {
				return "questionboard_answer_form";
			}
			Answer answer = this.answerService.questionboard_getAnswer(id);
			if(!answer.getAuthor().getUsername().equals(principal.getName())) { 
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한 없음");	
			}
			this.answerService.questionboard_modify(answer, answerForm.getContent());
			return String.format("redirect:/questionboard/detail/%s", answer.getQuestion().getId());
			
		}
		@PreAuthorize("isAuthenticated()")
		@GetMapping("/questionboard/answer/delete/{id}")
		public String questionboard_answerdelete(@PathVariable("id") Integer id, Principal principal) {

			Answer answer = this.answerService.questionboard_getAnswer(id);
			if(!answer.getAuthor().getUsername().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다.");
			}
			this.answerService.questionboard_delete(answer);
			return String.format("redirect:/questionboard/detail/%s", answer.getQuestion().getId());
		}
		
		@PreAuthorize("isAuthenticated()")
		@GetMapping("/questionboard/answer/voter/{id}")
		public String questionboard_answerVoter(Principal principal,@PathVariable("id") Integer id ) {
			Answer answer = this.answerService.questionboard_getAnswer(id);
			SiteUser siteUser = this.userService.getUser(principal.getName());
			this.answerService.questionboard_voter(answer, siteUser);
			
			return String.format("redirect:/questionboard/detail/%s", answer.getQuestion().getId());
		}
		@PreAuthorize("isAuthenticated()")
		@GetMapping("/questionboard/answer/unvoter/{id}")
		public String questionboard_answerUnVoter(Principal principal,@PathVariable("id") Integer id ) {
			Answer answer = this.answerService.questionboard_getAnswer(id);
			SiteUser siteUser = this.userService.getUser(principal.getName());
			this.answerService.questionboard_Unvoter(answer, siteUser);
			
			return String.format("redirect:/questionboard/detail/%s", answer.getQuestion().getId());
		}
	//qustionboard_answer end
		
		
	//information answer start
	@PostMapping("/answer/informationdetail/{id}") 
	public String InfoAnswer(Model model, @PathVariable("id") Integer id, 
			@RequestParam String content, @Valid AnswerForm answerForm,
			BindingResult bindingResult, 
			Principal principal) throws DataNotFoundException {
		
			Question question = this.questionService.getInformation(id); 
			SiteUser siteUser = this.userService.getUser(principal.getName());
			if(bindingResult.hasErrors()) {
				model.addAttribute("question", question);
				return "information_detail";
			}
			
			this.answerService.InfoAnswerCreate(question, answerForm.getContent(), siteUser);
		 
		return String.format("redirect:/sharing/informationdetail/%s", id); 
		
	}
	@GetMapping("/answer/infomodify/{id}") 
	public String InfoAnswerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
		Answer answer = this.answerService.InfoAnswer(id);
		if(!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다");
		}
		answerForm.setContent(answer.getContent());
		return "informationanswer_form";
	}
	
	@PostMapping("/answer/infomodify/{id}")
	public String InfoAnswerModify(@Valid AnswerForm answerForm, @PathVariable("id") Integer id, BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
			return "informationanswer_form";
			}
			Answer answer = this.answerService.InfoAnswer(id);
			if(!answer.getAuthor().getUsername().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다");
			}
			this.answerService.InfoAnswerModify(answer, answerForm.getContent());
			
			return String.format("redirect:/sharing/informationdetail/%s", answer.getQuestion().getId()); 
	}
	
	@GetMapping("/answer/infodelete/{id}")
	public String InfoAnswerDelete(@PathVariable("id") Integer id, Principal principal) {
		Answer answer = this.answerService.InfoAnswer(id);
		if(!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다");
		}
		this.answerService.InfoAnswerDelete(answer);
		return String.format("redirect:/sharing/informationdetail/%s", answer.getQuestion().getId());
	}
	
	@GetMapping("/answer/infovoter/{id}")
	public String InfoAnswerVoter(@PathVariable("id") Integer id, Principal principal) {
		Answer answer = this.answerService.InfoAnswer(id);
		SiteUser siteUser = this.userService.getUser(principal.getName()); 
		this.answerService.InforAnswerVoter(answer, siteUser);
		return String.format("redirect:/sharing/informationdetail/%s", answer.getQuestion().getId());
	}
		//information answer end
		
	
}
