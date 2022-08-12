package client.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import client.dto.SanPhamDTO;
import client.dto.SanPhamPagingDTO;
import client.dto.SanPhamPostDTO;

public interface SanPhamService {
	public List<SanPhamDTO> getAllProduct();
	public SanPhamPagingDTO getAllProductByPage(int page);
	public SanPhamPagingDTO getProductsByType(String category, int page, String type, String sort);
	public SanPhamDTO getProductById(int id);
	public Object getProductDetail(int id);
	public void addProduct(SanPhamPostDTO postDto, String token)throws JsonProcessingException;
	public void editProduct(SanPhamPostDTO postDto, String token)throws JsonProcessingException;
	public void deleteProduct(int id, String token);
	public String uploadImage(MultipartFile image, String token) throws IOException;
	public SanPhamPagingDTO searchProductByName(String keyword, int page);
}
