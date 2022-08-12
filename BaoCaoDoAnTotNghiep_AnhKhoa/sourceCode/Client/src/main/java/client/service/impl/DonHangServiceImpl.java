package client.service.impl;

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

import client.dto.DonHangDTO;
import client.dto.GioHangDTO;
import client.dto.OrderReportDTO;
import client.dto.UserInfoDTO;
import client.form.DangNhapForm;
import client.service.DonHangService;

@Service
public class DonHangServiceImpl implements DonHangService{

	private RestTemplate restTemplate;
	private String restUrl;
	
	public DonHangServiceImpl(@Value("${crm.rest.url}") String restUrl, RestTemplate restTemplate) {
		this.restUrl = restUrl;
		this.restTemplate = restTemplate;
	}

	@Override
	public String addOrder(GioHangDTO gioHangDTO, String token) {	
		try {
			HttpHeaders authenticationHeaders = getHeaders(token);
			String authenticationBody = getBody(gioHangDTO);
			HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody, authenticationHeaders);
			ResponseEntity<String> responseEntity = restTemplate.postForEntity(restUrl+"order/save", authenticationEntity, String.class);
			return responseEntity.getBody();
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<DonHangDTO> getTop10(String token) {
		HttpHeaders authenticationHeaders = getHeaders(token);

		HttpEntity<String> authenticationEntity = new HttpEntity<String>(null, authenticationHeaders);
		ResponseEntity<List<DonHangDTO>> responseEntity = restTemplate.exchange(restUrl+"admin/order/top10", HttpMethod.GET, authenticationEntity, new ParameterizedTypeReference<List<DonHangDTO>>() {});
		return responseEntity.getBody();
	}

	@Override
	public List<DonHangDTO> getListByUserId(int userId, String token) {
		HttpHeaders authenticationHeaders = getHeaders(token);

		HttpEntity<String> authenticationEntity = new HttpEntity<String>(null, authenticationHeaders);
		
		ResponseEntity<List<DonHangDTO>> responseEntity = restTemplate.exchange(restUrl+"order/list/"+userId, HttpMethod.GET, authenticationEntity, new ParameterizedTypeReference<List<DonHangDTO>>() {});
		return responseEntity.getBody();
	}

	@Override
	public DonHangDTO getByOrderId(int orderId, String token) {
		HttpHeaders authenticationHeaders = getHeaders(token);

		HttpEntity<String> authenticationEntity = new HttpEntity<String>(null, authenticationHeaders);
		
		ResponseEntity<DonHangDTO> responseEntity = restTemplate.exchange(restUrl+"order/"+orderId, HttpMethod.GET, authenticationEntity, new ParameterizedTypeReference<DonHangDTO>() {});
		return responseEntity.getBody();
	}

	@Override
	public List<DonHangDTO> getAllOrder(String token) {
		HttpHeaders authenticationHeaders = getHeaders(token);

		HttpEntity<String> authenticationEntity = new HttpEntity<String>(null, authenticationHeaders);
		ResponseEntity<List<DonHangDTO>> responseEntity = restTemplate.exchange(restUrl+"admin/order", HttpMethod.GET, authenticationEntity, new ParameterizedTypeReference<List<DonHangDTO>>() {});
		return responseEntity.getBody();
	}

	@Override
	public void changeOrderStatus(int orderId, String newStatus, String token) {
		HttpHeaders authenticationHeaders = getHeaders(token);

		HttpEntity<String> authenticationEntity = new HttpEntity<String>(null, authenticationHeaders);
		restTemplate.exchange(restUrl+"admin/order/changeStatus/"+orderId+"/"+newStatus, HttpMethod.GET, authenticationEntity, new ParameterizedTypeReference<String>() {});
	}
	
	@Override
	public List<DonHangDTO> getAllOrderByStatus(String status, String token) {
		HttpHeaders authenticationHeaders = getHeaders(token);

		HttpEntity<String> authenticationEntity = new HttpEntity<String>(null, authenticationHeaders);
		ResponseEntity<List<DonHangDTO>> responseEntity = restTemplate.exchange(restUrl+"admin/report/s/"+status, HttpMethod.GET, authenticationEntity, new ParameterizedTypeReference<List<DonHangDTO>>() {});
		return responseEntity.getBody();
	}

	private String getBody(final GioHangDTO dto) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(dto);
	}
	
	private String getBody(final OrderReportDTO dto) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(dto);
	}
	
	private HttpHeaders getHeaders(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Bearer " + accessToken);
		return headers;
	}

	
}
