package client.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import client.dto.UserDTO;
import client.dto.UserDetailDTO;
import client.dto.UserInfoDTO;

public interface NguoiDungService {
	public UserInfoDTO getUserInfo(int id, String token);
	public UserDetailDTO getUserDetailByEmail(String email);
	
	public List<UserDTO> getListUser(String token);
	UserDTO getUserById(int id, String token);
	void addUser(UserDTO dto, String token)throws JsonProcessingException;
	void deleteUser(int id, String token);
	void updateUser(int id, UserDTO newUser);
	void updateUserAdmin(int id, UserDTO newUser, String token)throws JsonProcessingException;
}
