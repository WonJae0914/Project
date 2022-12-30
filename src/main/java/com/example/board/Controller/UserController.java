package com.example.board.Controller;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.board.Form.UserCreateForm;
import com.example.board.Service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")

public class UserController {
	private final UserService userService;
	
	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "sign_up";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "sign_up";
		}
	
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			
			bindingResult.rejectValue("password2","passwordIncorrerct", "패스워드가 일치하지 않습니다");
			return "sign_up";
		}
		try {
		this.userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());	
		} catch(DataIntegrityViolationException dive) {
			dive.printStackTrace();
			bindingResult.reject("회원가입 실패", "이미 등록되어 있는 사용자입니다.");
			return "sign_up";
		}
		
		catch(Exception e) {
			e.printStackTrace();
			bindingResult.reject("회원가입 실패", e.getMessage());
			return "sign_up";
		}
		return "redirect:/";
	}
		@GetMapping("/login")
		public String login() {
			return "login_form";
			
		}
		
		
		

		
		
		
		
		

}
