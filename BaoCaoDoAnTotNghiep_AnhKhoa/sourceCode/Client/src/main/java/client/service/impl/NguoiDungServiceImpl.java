package client.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import client.dto.UserDTO;
import client.dto.UserDetailDTO;
import client.dto.UserInfoDTO;
import client.service.NguoiDungService;

@Service
public class NguoiDungServiceImpl implements NguoiDungService{

	private String restUrl;
	private RestTemplate restTemplate;
		
	public NguoiDungServiceImpl(@Value("${crm.rest.url}") String restUrl, RestTemplate restTemplate) {
		this.restUrl = restUrl;
		this.restTemplate = restTemplate;
	}

	@Override
	public UserInfoDTO getUserInfo(int id, String token) {
		HttpHeaders authenticationHeaders = getHeaders(token);
		HttpEntity<String> authenticationEntity = new HttpEntity<String>(null,
				authenticationHeaders);
		ResponseEntity<UserInfoDTO> responseEntity = restTemplate.exchange(restUrl+"/user/info/"+id, HttpMethod.GET, authenticationEntity, new ParameterizedTypeReference<UserInfoDTO>() {});
		return responseEntity.getBody();
	}

	@Override
	public List<UserDTO> getListUser(String token) {
		HttpHeaders authenticationHeaders = getHeaders(token);
		HttpEntity<String> authenticationEntity = new HttpEntity<String>(null,
				authenticationHeaders);
		ResponseEntity<List<UserDTO>> responseEntity = restTemplate.exchange(restUrl + "/admin/user", HttpMethod.GET,
				authenticationEntity, new ParameterizedTypeReference<List<UserDTO>>() {
				});
		return responseEntity.getBody();
	}
	
	@Override
	public UserDTO getUserById(int id, String token) {
		HttpHeaders authenticationHeaders = getHeaders(token);
		HttpEntity<String> authenticationEntity = new HttpEntity<String>(null,
				authenticationHeaders);
		ResponseEntity<UserDTO> entity = restTemplate.exchange(restUrl + "/admin/user/" + id, HttpMethod.GET, authenticationEntity,
				new ParameterizedTypeReference<UserDTO>() {
				});
		return entity.getBody();
	}

	@Override
	public void addUser(UserDTO dto, String token) throws JsonProcessingException {
		String authenticationBody = getBody(dto);
		HttpHeaders authenticationHeaders = getHeaders(token);

		HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody, authenticationHeaders);
		restTemplate.postForEntity(restUrl + "/admin/user", authenticationEntity, UserDTO.class);

	}

	@Override
	public void deleteUser(int id, String token) {
		HttpHeaders authenticationHeaders = getHeaders(token);
		HttpEntity<String> authenticationEntity = new HttpEntity<String>(null,
				authenticationHeaders);
		// Only set enable to false for user
//		restTemplate.getForEntity(restUrl + "/admin/user/delete/" + id, null);
		restTemplate.exchange(restUrl + "/admin/user/delete/" + id, HttpMethod.GET, authenticationEntity,
				String.class);

	}

	@Override
	public void updateUserAdmin(int id, UserDTO newUser, String token) throws JsonProcessingException {
		String authenticationBody = getBody(newUser);
		HttpHeaders authenticationHeaders = getHeaders(token);

		HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody, authenticationHeaders);
		restTemplate.put(restUrl + "/admin/user/" + id, authenticationEntity, UserDTO.class);
	}
	
	@Override
	public void updateUser(int id, UserDTO newUser) {
		restTemplate.put(restUrl + "/user/update/" + id, newUser, UserDTO.class);
	}

	@Override
	public UserDetailDTO getUserDetailByEmail(String email) {
		ResponseEntity<UserDetailDTO> responseEntity = restTemplate.exchange(restUrl+"/user/info/email/"+email, HttpMethod.GET, null, new ParameterizedTypeReference<UserDetailDTO>() {});
		return responseEntity.getBody();
	}
	
	
	private String getBody(final UserDTO userDTO) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(userDTO);
	}

	private HttpHeaders getHeaders(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Bearer " + accessToken);
		return headers;
	}
}
