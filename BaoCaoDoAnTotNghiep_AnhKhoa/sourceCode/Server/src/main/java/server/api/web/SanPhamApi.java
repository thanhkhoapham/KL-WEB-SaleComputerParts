package server.api.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import server.dto.SanPhamDTO;
import server.dto.SanPhamPagingDTO;
import server.service.SanPhamService;

@RestController
@RequestMapping("/product")
public class SanPhamApi {
	
	@Autowired
	private SanPhamService sanPhamService;
	
	@GetMapping("")
	public List<SanPhamDTO> getAllProduct(){
		return sanPhamService.getAllProduct();
	}
	
	@GetMapping("/page/{page}")
	public SanPhamPagingDTO getAllProductPagination(@PathVariable int page){
		return sanPhamService.getAllProductByPage(page);
	}
	
	@GetMapping("/search/{keyword}/{page}")
	public SanPhamPagingDTO searchProductByName(@PathVariable("keyword") String keyword, @PathVariable("page") int page){
		return sanPhamService.searchProductByName(keyword, page);
	}
	
	@GetMapping("/{category}/{type}/{sort}/{page}")
	public SanPhamPagingDTO getAllProductByType(@PathVariable("category") String category,
												@PathVariable("type") String type,
												@PathVariable("page") int page,
												@PathVariable("sort") String sort){
		return sanPhamService.getProductsByType(category, page, type, sort);
	}
	
	@GetMapping("/{id}")
	public SanPhamDTO getProduct(@PathVariable int id){
		return sanPhamService.getProductById(id);
	}
	
	@GetMapping("/detail/{id}")
	public Object getProductDetail(@PathVariable int id){
		return sanPhamService.getProductDTO(id);
	}
	

}