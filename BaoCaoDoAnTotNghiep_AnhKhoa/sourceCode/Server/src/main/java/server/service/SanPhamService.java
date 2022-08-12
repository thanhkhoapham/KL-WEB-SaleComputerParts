package server.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import server.dto.SanPhamDTO;
import server.dto.SanPhamPagingDTO;
import server.dto.SanPhamPostDTO;

public interface SanPhamService {
	public List<SanPhamDTO> getAllProduct();
	public SanPhamPagingDTO getAllProductByPage(int pageNo);
	public SanPhamDTO getProductById(int id);
	public Object getProductDTO(int id);
	public void addProduct(SanPhamPostDTO postDto);
	public void editProduct(SanPhamPostDTO postDto);
	public void deleteProduct(int id);	
	public String uploadImage(MultipartFile image) throws IOException;
	public SanPhamPagingDTO getProductsByType(String category, int pageNo, String type, String sort);
	public SanPhamPagingDTO searchProductByName(String keyword, int page);

}
