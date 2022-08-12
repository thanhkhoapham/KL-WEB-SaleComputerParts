package server.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server.dto.UserRegisterDTO;
import server.form.DangKyForm;
import server.form.DangNhapForm;
import server.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthApi {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/register")
	public Object postRegister(@RequestBody DangKyForm form){
		try {
			UserRegisterDTO dtoRegister = authService.register(form);
			authService.sendVerificationEmail(dtoRegister);
			return new ResponseEntity<Object>(dtoRegister, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/verify/{code}")
	public Object verifyAccount(@PathVariable("code") String code) {
		try {
			authService.verify(code);
			return new ResponseEntity<Object>("Verify Success", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Error"+e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/login")
	public Object postLogin(@RequestBody DangNhapForm form) {
		try {
			return new ResponseEntity<Object>(authService.userLogin(form), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Error"+e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
}
