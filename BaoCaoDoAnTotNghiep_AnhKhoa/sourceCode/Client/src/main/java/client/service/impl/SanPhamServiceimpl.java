package client.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import client.dto.SanPhamDTO;
import client.dto.SanPhamPagingDTO;
import client.dto.SanPhamPostDTO;
import client.form.DangNhapForm;
import client.service.SanPhamService;

@Service
public class SanPhamServiceimpl implements SanPhamService{
	private RestTemplate restTemplate;
	private String restUrl;
	
	
	@Autowired
	public SanPhamServiceimpl(RestTemplate restTemplate, @Value("${crm.rest.url}") String restUrl) {
		this.restTemplate = restTemplate;
		this.restUrl = restUrl;
	}

	//	/product
	@Override
	public List<SanPhamDTO> getAllProduct() {
		ResponseEntity<List<SanPhamDTO>> responseEntity = restTemplate.exchange(restUrl  +"product", HttpMethod.GET, null, new ParameterizedTypeReference<List<SanPhamDTO>>() {});
		return responseEntity.getBody();
	}

	@Override
	public SanPhamPagingDTO getAllProductByPage(int page) {
		ResponseEntity<SanPhamPagingDTO> responseEntity = restTemplate.exchange(restUrl  +"product/page/" + page, HttpMethod.GET, null, new ParameterizedTypeReference<SanPhamPagingDTO>() {});
		return responseEntity.getBody();
	}
	
	//	/product/id
	@Override
	public SanPhamDTO getProductById(int id) {
		ResponseEntity<SanPhamDTO> responseEntity = restTemplate.exchange(restUrl+"product/"+id, HttpMethod.GET, null, new ParameterizedTypeReference<SanPhamDTO>() {});
		return responseEntity.getBody();
	}

	@Override
	public void addProduct(SanPhamPostDTO postDto, String token) throws JsonProcessingException {
		String authenticationBody = getBody(postDto);
		HttpHeaders authenticationHeaders = getHeaders(token);

		HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody, authenticationHeaders);
		restTemplate.postForEntity(restUrl +"admin/product/add", authenticationEntity, String.class);
	}
	
	@Override
	public void editProduct(SanPhamPostDTO postDto, String token) throws JsonProcessingException{
		String authenticationBody = getBody(postDto);
		HttpHeaders authenticationHeaders = getHeaders(token);

		HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody, authenticationHeaders);
		restTemplate.postForEntity(restUrl +"admin/product/edit", authenticationEntity, String.class);
	}
	
	@Override
	public void deleteProduct(int id, String token) {
		HttpHeaders authenticationHeaders = getHeaders(token);
		HttpEntity<String> authenticationEntity = new HttpEntity<String>(null, authenticationHeaders);
		restTemplate.exchange(restUrl+"admin/product/delete/"+id, HttpMethod.GET, authenticationEntity, new ParameterizedTypeReference<Object>() {});
	}

//	/admin/product/image
//	Trả về tên file được upload
	@Override
	public String uploadImage(MultipartFile image, String token) throws IOException {
		MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
		String fileSuffix = "." + FilenameUtils.getExtension(image.getOriginalFilename());
		bodyMap.add("image", getFileResource(image, fileSuffix));
		
	    HttpHeaders headers = getMultipartHeaders(token);
	    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
		
	    System.err.println(FilenameUtils.getExtension(image.getOriginalFilename()));
	    ResponseEntity<String> responseEntity = restTemplate.exchange(restUrl+"admin/product/image", HttpMethod.POST, requestEntity, String.class);	
	    return responseEntity.getBody();
	}
	
	public static Resource getFileResource(MultipartFile file, String fileSuffix) throws IOException {
	      Path tempFile = Files.createTempFile("product", fileSuffix);
	      Files.write(tempFile, file.getBytes());
	      File nfile = tempFile.toFile();
	      return new FileSystemResource(nfile);
	  }

	@Override
	public Object getProductDetail(int id) {
		ResponseEntity<Object> responseEntity = restTemplate.exchange(restUrl+"product/detail/"+id, HttpMethod.GET, null, new ParameterizedTypeReference<Object>() {});
		return responseEntity.getBody();
	}

	@Override
	public SanPhamPagingDTO getProductsByType(String category, int page, String type, String sort) {
		ResponseEntity<SanPhamPagingDTO> responseEntity = restTemplate.exchange(restUrl  +"product/"+category+"/"+type+"/"+sort+"/"+ page, HttpMethod.GET, null, new ParameterizedTypeReference<SanPhamPagingDTO>() {});
		return responseEntity.getBody();
	}

	@Override
	public SanPhamPagingDTO searchProductByName(String keyword, int page) {
		ResponseEntity<SanPhamPagingDTO> responseEntity = restTemplate.exchange(restUrl  +"product/search/" + keyword + "/" + page, HttpMethod.GET, null, new ParameterizedTypeReference<SanPhamPagingDTO>() {});
		return responseEntity.getBody();
	}

	private String getBody(final SanPhamPostDTO postDto) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(postDto);
	}

	private HttpHeaders getHeaders(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Bearer " + token);
		return headers;
	}

	private HttpHeaders getMultipartHeaders(String token) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Bearer " + token);
		return headers;
	}

	
}
