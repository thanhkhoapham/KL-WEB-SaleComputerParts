package server.repository.custom;

import java.util.List;

import server.entity.SanPham;

public interface SanPhamCustomRepository {
	List<SanPham> findAllProductPageable(int page, String category, String productType, String sort);
	List<SanPham> searchProductByNamePageable(String keyword, int page);
}
