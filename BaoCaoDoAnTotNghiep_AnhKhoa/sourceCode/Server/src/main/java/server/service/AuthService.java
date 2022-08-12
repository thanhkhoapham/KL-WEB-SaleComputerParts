package server.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import server.dto.UserLoginDTO;
import server.dto.UserRegisterDTO;
import server.form.DangKyForm;
import server.form.DangNhapForm;

public interface AuthService {
	
	public UserLoginDTO userLogin(DangNhapForm form);
	public UserRegisterDTO register(DangKyForm form);
	public void sendVerificationEmail(UserRegisterDTO dto) throws UnsupportedEncodingException, MessagingException;
	public boolean verify(String verifiticationCode);
	public void updateUserStatus(int id);
	public boolean checkIfUserExist(String email);
}
