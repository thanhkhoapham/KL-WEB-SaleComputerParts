package client.service.impl;

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

import client.dto.UserLoginDTO;
import client.dto.UserRegisterDTO;
import client.form.DangKyForm;
import client.form.DangNhapForm;
import client.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
	
	private RestTemplate restTemplate;
	private String restUrl;

	public AuthServiceImpl(RestTemplate restTemplate, @Value("${crm.rest.url}") String restUrl) {
		this.restTemplate = restTemplate;
		this.restUrl = restUrl +"auth";
	}

	@Override
	public UserRegisterDTO register(DangKyForm form) {
		ResponseEntity<UserRegisterDTO> responseEntity = restTemplate.postForEntity(restUrl+"/register", form, UserRegisterDTO.class);
		return responseEntity.getBody();
	}

	@Override
	public void verify(String verifiticationCode) {
		restTemplate.exchange(restUrl+"/verify/"+verifiticationCode,HttpMethod.GET,
				null,new ParameterizedTypeReference<String>() {});
	}

	@Override
	public UserLoginDTO userLogin(DangNhapForm form) {		
		UserLoginDTO userLoginDTO = new UserLoginDTO();
		try {
			String authenticationBody = getBody(form);
			HttpHeaders authenticationHeaders = getHeaders();

			HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody, authenticationHeaders);

			ResponseEntity<UserLoginDTO> responseEntity = restTemplate.exchange(
					restUrl+"/login", HttpMethod.POST, authenticationEntity,
					new ParameterizedTypeReference<UserLoginDTO>() {
					});

			userLoginDTO.setToken(responseEntity.getBody().getToken());
			userLoginDTO.setUserId(responseEntity.getBody().getUserId());
			userLoginDTO.setUserName(responseEntity.getBody().getUserName());
			userLoginDTO.setRole(responseEntity.getBody().getRole());

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return userLoginDTO;
	}
	
	private String getBody(final DangNhapForm user) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(user);
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}
}
