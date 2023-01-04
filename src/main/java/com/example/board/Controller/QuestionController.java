package com.example.board.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.codehaus.groovy.ast.stmt.TryCatchStatement;
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
import com.example.board.Etc.UserRole;
import com.example.board.Form.AnswerForm;
import com.example.board.Form.QuestionForm;
import com.example.board.Repository.AnswerRepository;
import com.example.board.Repository.UserRepository;
import com.example.board.Service.AnswerService;
import com.example.board.Service.QuestionService;
import com.example.board.Service.UserService;

import groovyjarjarasm.asm.commons.TryCatchBlockSorter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class QuestionController {
   
   private final QuestionService questionService; 
   private final UserService userService;
   private final AnswerRepository answerRepository;
   private final UserRepository userRepository;
   
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
   @PreAuthorize("isAuthenticated()")
   @GetMapping("/review/unvoter/{id}")
   public String reviewunvoter(@PathVariable("id") Integer id, Principal principal) {
      Question question = this.questionService.reviewGetQuestion(id);
      SiteUser siteUser = this.userService.getUser(principal.getName());
      this.questionService.reviewunVoter(question, siteUser);
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
     
      @RequestMapping(value="/questionboard/detail/{id}")   // 여기 수정됐어요
      public String questionboard_detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm, Principal principal) throws Exception {
         Question question = this.questionService.questionboard_getQuestion(id);
         if(principal.getName().equals("admin") || question.getAuthor().getUsername().equals(principal.getName())) {
             model.addAttribute("question", question); 
             return "questionboard_detail";        
             }
         else if(!question.getAuthor().getUsername().equals(principal.getName())) {
           }  
         return "redirect:/questionboard/list"; 
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
      @GetMapping("/questionboard/voter/{id}")
      public String questionboard_voter(Principal principal,@PathVariable("id") Integer id ) {
         Question question = this.questionService.questionboard_getQuestion(id);
         SiteUser siteUser = this.userService.getUser(principal.getName());
         this.questionService.questionboard_voter(question, siteUser);
         
         return String.format("redirect:/questionboard/detail/%s", id);
      }
      
      //questionboard end

		//InformationSharing start
		@GetMapping("/sharing/list")
		public String InfoList(Model model, @RequestParam(value="page", defaultValue="0") int page, 
					@RequestParam(value="kw", defaultValue="") String kw) { 
				Page<Question> paging = this.questionService.getInfoList(page, kw);
				model.addAttribute("paging", paging);
				model.addAttribute("kw", kw);
				return "informationSharing"; 
			}
		
		@RequestMapping(value="/sharing/informationdetail/{id}") 
		public String InforDetail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm, Principal principal) throws Exception { 
			Question question = this.questionService.getInformation(id);
			questionService.updateView(id);
			model.addAttribute("question", question);
			return "information_detail";

      }
      @PreAuthorize("isAuthenticated()")
      @GetMapping("/sharing/sharingform")
      public String InfoCreate(QuestionForm questionForm){
         return "information_create";
      }
      @PreAuthorize("isAuthenticated()")
      @PostMapping("/sharing/sharingform")
      public String InforCreate(@Valid QuestionForm questionForm, 
            BindingResult bindingResult, Principal principal){ 
         if(bindingResult.hasErrors()) {
            return "sharing_form";
         }
         SiteUser siteuser = this.userService.getUser(principal.getName());
            
         this.questionService.getInforCreate(
               questionForm.getSubject(), 
               questionForm.getContent(), 
               siteuser);
         return "redirect:/sharing/list";
         }
      @PreAuthorize("isAuthenticated()")
      @GetMapping("/sharing/infomodify/{id}")
      public String InfoModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal){
         Question question = this.questionService.getInformation(id);
         if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
         }
         questionForm.setSubject(question.getSubject());
         questionForm.setContent(question.getContent());
         return "information_modify";
      }
      @PreAuthorize("isAuthenticated()")
      @PostMapping("/sharing/infomodify/{id}")
      public String InfoModify(@Valid QuestionForm questionForm, @PathVariable("id") Integer id, 
            BindingResult bindingResult, Principal principal){
            
         if(bindingResult.hasErrors()) {
         return "question_form";
         }
         Question question = this.questionService.getInformation(id);
         if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다");
         }
         this.questionService.getInfoModify(question, questionForm.getSubject(), questionForm.getContent());
         
         return String.format("redirect:/sharing/informationdetail/%s", id);
      }
      @PreAuthorize("isAuthenticated()")
      @GetMapping("/sharing/infodelete/{id}")
      public String InfoDelete(@PathVariable("id") Integer id, Principal principal) {
         Question question = this.questionService.getInformation(id);
         if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다");
         }
         this.questionService.getInfoDelete(question);
         return "redirect:/sharing/list";
      }
      @PreAuthorize("isAuthenticated()")   
      @GetMapping("/sharing/infovoter/{id}")
      public String InfoVoter(@PathVariable("id") Integer id, Principal principal) {
         Question question = this.questionService.getInformation(id);
         SiteUser siteUser = this.userService.getUser(principal.getName());
         this.questionService.getInforVoter(question, siteUser);
         return String.format("redirect:/sharing/informationdetail/%s", id);
      }
      //InformationSharing end   


	

		// 221230 - add notice start - updated by kd
		@GetMapping("/notice/list")
		public String noticeList(Model model, @RequestParam(value="page", defaultValue="0") int page, 
			 @RequestParam(value="kw", defaultValue="") String kw) {
			Page<Question> paging = this.questionService.getList(page, kw);
			
			model.addAttribute("paging", paging);
			model.addAttribute("kw", kw);
			return "notice_list";
		}
		// 비로그인 조회 o 중복 o
		@RequestMapping(value="/notice/detail/{id}")
		public String noticeDetail(Model model, @PathVariable("id") Integer id, Principal principal) throws Exception {
			Question question = this.questionService.getQuestion(id);
//			this.questionService.getQuestion(id);
			model.addAttribute("question", question);
			return "notice_detail";
		}
		// 로그인 한 사람만, 중복 x
//		@RequestMapping(value="/notice/detail/{id}")
//		public String noticeDetail(Model model, @PathVariable("id") Integer id, Principal principal) throws Exception {
//			Question question = this.questionService.getQuestion(id);
//			SiteUser siteUser = this.userService.getUser(principal.getName());
//			this.questionService.numOfView(question, siteUser);
//			model.addAttribute("question", question);
//			return "notice_detail";
//		}
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		@GetMapping("/notice/create")
		public String noticeCreate(QuestionForm questionForm) {
			return "notice_create";
		}
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		@PostMapping("/notice/create")
		public String noticeCreate(@Valid QuestionForm questionForm, 
				BindingResult bindingResult, Principal principal) {
			if(bindingResult.hasErrors()) {
				return "notice_form";
			}
			SiteUser siteuser = this.userService.getUser(principal.getName());
			this.questionService.create(
					questionForm.getSubject(), 
					questionForm.getContent(),
					siteuser);
			return "redirect:/notice/list";
		}
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		@GetMapping("/notice/modify/{id}")
		public String noticeModify(QuestionForm questionForm, 
				@PathVariable("id") Integer id, Principal principal) {
			Question q = this.questionService.getQuestion(id);
			if(!q.getAuthor().getUsername().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
			}
			questionForm.setSubject(q.getSubject());
			questionForm.setContent(q.getContent());
			return "notice_modify";
		}
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		@PostMapping("/notice/modify/{id}")
		public String noticeModify(@Valid QuestionForm questionForm, @PathVariable("id") Integer id, 
				BindingResult bindingResult, Principal principal) {
			if(bindingResult.hasErrors()) {
				return "notice_form";
			}
			Question question = this.questionService.getQuestion(id);
			if(!question.getAuthor().getUsername().equals(principal.getName())){
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
			}
			this.questionService.modify(question, 
					questionForm.getSubject(), questionForm.getContent());
			return String.format("redirect:/notice/detail/%s", id);
		}
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		@GetMapping("/notice/delete/{id}")
		public String noticeDelete(@PathVariable("id") Integer id, Principal principal) {
			Question question = this.questionService.getQuestion(id);
			if(!question.getAuthor().getUsername().equals(principal.getName())){
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
			}
			this.questionService.delete(question);
			return "redirect:/notice/list";
		}
		@PreAuthorize("isAuthenticated()")
		@GetMapping("/notice/voter/{id}")
		public String noticeVoter(@PathVariable("id") Integer id, Principal principal) {
			Question question = this.questionService.getQuestion(id);
			SiteUser siteUser = this.userService.getUser(principal.getName());
			this.questionService.voter(question, siteUser);
			return String.format("redirect:/notice/detail/%s", id);
		}	
		// 221230 - add notice end - updated by kd
    
    
		// qna start
		@GetMapping("/qna/list")
		public String qna_List(Model model, @RequestParam(value="page", defaultValue="0") int page,
				@RequestParam(value="kw", defaultValue="") String kw) {
			Page<Question> qnapaging = this.questionService.qnaGetList(page, kw);
			model.addAttribute("qnapaging", qnapaging);
			model.addAttribute("kw", kw);
			return "qna_list";
		}
				
		@RequestMapping(value="/qna/detail/{id}") 
		public String qna_Detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm, Principal principal) throws Exception {
			Question qnaquestion = this.questionService.qnaGetQuestion(id);
			model.addAttribute("qnaquestion", qnaquestion);
			return "qna_detail";
		}
				
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		@GetMapping(value="/qna/create")
		public String qna_Create(QuestionForm questionForm) {
			return "qna_create";
		}
			
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		@PostMapping(value="/qna/create")
		public String qna_Create(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
			if(bindingResult.hasErrors()) {
				return "qna_list";
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
