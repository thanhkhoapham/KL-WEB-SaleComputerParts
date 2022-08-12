package server.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server.dto.UserDTO;
import server.dto.UserDetailDTO;
import server.dto.UserInfoDTO;
import server.service.NguoiDungService;

@RestController
@RequestMapping("/user")
public class NguoiDungApi {
	
	@Autowired
	private NguoiDungService nguoiDungService;
	
	@GetMapping("/info/{id}")
//	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
	public UserInfoDTO getUserInfo(@PathVariable int id){
		return nguoiDungService.getUserInfoById(id);
	}
	
	@GetMapping("/info/email/{email}")
	public UserDetailDTO getUserInfoByEmail(@PathVariable String email){
		return nguoiDungService.getUserDetailByEmail(email);
	}
	
	@PutMapping("/update/{id}")
	public Object updateUser(@PathVariable("id") int id, @RequestBody UserDTO newS) {
		try {
			nguoiDungService.updateUser(id, newS);
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}
}
