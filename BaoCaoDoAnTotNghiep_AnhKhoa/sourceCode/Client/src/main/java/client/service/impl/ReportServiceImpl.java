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

import client.dto.ReportDTO;
import client.dto.Report_QueryResult;
import client.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService{

	private RestTemplate restTemplate;
	private String restUrl;
	
	public ReportServiceImpl(@Value("${crm.rest.url}") String restUrl, RestTemplate restTemplate) {
		this.restUrl = restUrl;
		this.restTemplate = restTemplate;
	}
	
	@Override
	public ReportDTO createReport(String token) {
		HttpHeaders authenticationHeaders = getHeaders(token);
		HttpEntity<String> authenticationEntity = new HttpEntity<String>(null,
				authenticationHeaders);
		ResponseEntity<ReportDTO> responseEntity = restTemplate.exchange(restUrl+"admin/report", HttpMethod.GET, authenticationEntity, new ParameterizedTypeReference<ReportDTO>() {});
		return responseEntity.getBody();
	}

	@Override
	public List<Report_QueryResult> getBestSeller(String token) {
		HttpHeaders authenticationHeaders = getHeaders(token);
		HttpEntity<String> authenticationEntity = new HttpEntity<String>(null,
				authenticationHeaders);
		ResponseEntity<List<Report_QueryResult>> responseEntity = restTemplate.exchange(restUrl+"admin/report/bestseller", HttpMethod.GET, authenticationEntity, new ParameterizedTypeReference<List<Report_QueryResult>>() {});
		return responseEntity.getBody();
	}

	private HttpHeaders getHeaders(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Bearer " + accessToken);
		return headers;
	}
}
