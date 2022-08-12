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

import client.dto.NhaCungCapDTO;
import client.dto.SanPhamPostDTO;
import client.service.NhaCungCapService;

@Service
public class NhaCungCapServiceImpl implements NhaCungCapService {

	private String restUrl;
	private RestTemplate restTemplate;

	public NhaCungCapServiceImpl(@Value("${crm.rest.url}") String restUrl, RestTemplate restTemplate) {
		this.restUrl = restUrl;
		this.restTemplate = restTemplate;
	}

	@Override
	public List<NhaCungCapDTO> getAllSupplier(String token) {
		HttpHeaders authenticationHeaders = getHeaders(token);
		HttpEntity<String> authenticationEntity = new HttpEntity<String>(null,
				authenticationHeaders);
		ResponseEntity<List<NhaCungCapDTO>> entity = restTemplate.exchange(restUrl + "/admin/supplier", HttpMethod.GET,
				authenticationEntity, new ParameterizedTypeReference<List<NhaCungCapDTO>>() {
				});
		return entity.getBody();
	}

	@Override
	public NhaCungCapDTO getSupplierById(int id, String token) {
		HttpHeaders authenticationHeaders = getHeaders(token);
		HttpEntity<String> authenticationEntity = new HttpEntity<String>(null,
				authenticationHeaders);
		ResponseEntity<NhaCungCapDTO> entity = restTemplate.exchange(restUrl + "/admin/supplier/" + id, HttpMethod.GET,
				authenticationEntity, new ParameterizedTypeReference<NhaCungCapDTO>() {
				});
		return entity.getBody();
	}

	@Override
	public void addSupplier(NhaCungCapDTO capDTO, String token) throws JsonProcessingException {
		String authenticationBody = getBody(capDTO);
		HttpHeaders authenticationHeaders = getHeaders(token);

		HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody, authenticationHeaders);
		restTemplate.postForEntity(restUrl + "/admin/supplier", authenticationEntity, NhaCungCapDTO.class);
	}

	@Override
	public void deleteSupplier(int id, String token) {
		HttpHeaders authenticationHeaders = getHeaders(token);
		HttpEntity<String> authenticationEntity = new HttpEntity<String>(null,
				authenticationHeaders);
		// Only set enable to false for supplier
//		restTemplate.getForEntity(restUrl + "/admin/supplier/delete/" + id, null);
		restTemplate.exchange(restUrl+"/admin/supplier/delete/"+id, HttpMethod.GET, authenticationEntity, String.class);
	}

	@Override
	public void updateSupplier(int id, NhaCungCapDTO newSupp, String token) throws JsonProcessingException {
		String authenticationBody = getBody(newSupp);
		HttpHeaders authenticationHeaders = getHeaders(token);

		HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody, authenticationHeaders);
		restTemplate.put(restUrl + "/admin/supplier/" + id, authenticationEntity, NhaCungCapDTO.class);
	}

	private HttpHeaders getHeaders(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Bearer " + accessToken);
		return headers;
	}
	
	private String getBody(final NhaCungCapDTO nccDto) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(nccDto);
	}
}
