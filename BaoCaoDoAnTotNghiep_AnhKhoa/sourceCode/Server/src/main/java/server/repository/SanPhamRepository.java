package server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import server.entity.SanPham;
import server.repository.custom.SanPhamCustomRepository;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer>, SanPhamCustomRepository{
	
	@Query(value = "SELECT loai_sanpham FROM san_pham WHERE id = ?1", nativeQuery = true)
	String getProductType(int id);
	
	@Query(value = "SELECT * FROM san_pham WHERE enable = 'true' order by id desc", nativeQuery = true)
	List<SanPham> findAllProduct();

	@Query(value = "SELECT * FROM san_pham WHERE loai_sanpham = ?1 and enable = 'true' order by id desc", nativeQuery = true)
	List<SanPham> findAllProductByCategory(String category);
	
	@Query(value = "SELECT * FROM san_pham WHERE ten_san_pham like %?1% and enable = 'true' order by id desc", nativeQuery = true)
	List<SanPham> findAllProductByName(String keyword);
}
