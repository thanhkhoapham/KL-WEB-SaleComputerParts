package server.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import server.dto.SanPhamDTO;
import server.entity.SanPham;
import server.repository.SanPhamRepository;

@Component
public class SanPhamConverter {
	
	@Autowired
	private SanPhamRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public SanPhamDTO toDto(SanPham sanPham) {
		SanPhamDTO dto = modelMapper.map(sanPham, SanPhamDTO.class);
		dto.setLoaiSanPham(repository.getProductType(sanPham.getId()));
		dto.setHangSanXuat(sanPham.getHangSanXuat());
		return dto;
	}
	

}
