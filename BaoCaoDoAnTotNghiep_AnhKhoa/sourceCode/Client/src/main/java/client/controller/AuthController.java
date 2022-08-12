package client.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import client.dto.UserLoginDTO;
import client.form.DangKyForm;
import client.form.DangNhapForm;
import client.service.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/login")
	public String showLoginForm(ModelMap modelMap) {
		modelMap.addAttribute("loginDTO", new DangNhapForm());
		return "auth/login-page";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("loginDTO") DangNhapForm form, RedirectAttributes attributes) {
		String thBao = "";
		try {
			UserLoginDTO userLoginDTO = authService.userLogin(form);
			session.setAttribute("user", userLoginDTO);
			if (userLoginDTO.getRole().equals("ADMIN") || userLoginDTO.getRole().equals("MODERATOR")) {
				return "redirect:/admin";
			}
			return "redirect:/";
		} catch (Exception e) {
			thBao = "Sai email hoặc mật khẩu!!";
			attributes.addFlashAttribute("thBao", thBao);
//			System.out.println(e.getMessage());
			return "redirect:/auth/login";
		}
	}
	
	@GetMapping("/register")
	public String showRegisterForm(ModelMap modelMap) {
		modelMap.addAttribute("registerDTO", new DangKyForm());
		return "auth/register-page";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute("registerDTO") DangKyForm form, RedirectAttributes attributes) {
		String thBao = "";
		try {
			authService.register(form);
			thBao = "Đăng ký thành công, vui lòng kiểm tra mail để hoàn tất đăng ký!!";
			attributes.addFlashAttribute("thBao", thBao);
			return "redirect:/auth/login";
		} catch (Exception e) {
			thBao = "Email này đã được đăng ký!!";
			attributes.addFlashAttribute("thBao", thBao);
			return "redirect:/auth/register";
		}
	}
	
	@GetMapping("/verify")
	public String verify(@RequestParam("code") String code, RedirectAttributes attributes) {
		String thBao = "";
		try {
			authService.verify(code);
			thBao = "Xác thực thành công, vui lòng đăng nhập để tiếp tục!!";
			attributes.addFlashAttribute("thBao", thBao);
			return "redirect:/auth/login";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/" ;
	}
}
