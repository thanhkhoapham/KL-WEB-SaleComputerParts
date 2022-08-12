package server.service;

import java.util.List;

import server.dto.UserDTO;
import server.dto.UserDetailDTO;
import server.dto.UserInfoDTO;
import server.entity.NguoiDung;

public interface NguoiDungService {
	public UserDTO getUserById(int id);
	public UserDetailDTO getUserDetailByEmail(String email);
	public UserInfoDTO getUserInfoById(int id);
	
	public UserInfoDTO getUserInfo(NguoiDung nguoiDung);
	public List<UserDTO> getListUser();
	
	void deleteUser(int id);
	void updateUser(int id, UserDTO value);
	void addUser(UserDTO dto);
}
