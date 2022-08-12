package client.service;

import client.dto.UserLoginDTO;
import client.dto.UserRegisterDTO;
import client.form.DangKyForm;
import client.form.DangNhapForm;

public interface AuthService {
	
	public UserLoginDTO userLogin(DangNhapForm form);
	public UserRegisterDTO register(DangKyForm form);
	public void verify(String verifiticationCode);
}
