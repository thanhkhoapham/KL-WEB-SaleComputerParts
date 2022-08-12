package server.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import server.entity.DonHang;
import server.repository.custom.DonHangCustomRepository;
import server.repository.query_result.CT_DonHang_QueryResult;
import server.repository.query_result.Report_QueryResult;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Integer>, DonHangCustomRepository{

	@Query(value = "SELECT * FROM don_hang WHERE nguoidung_id = ?1", nativeQuery = true)
	List<DonHang> findAllByUserId(int userId);
	
	@Query("select NEW server.repository.query_result.CT_DonHang_QueryResult(ct.soLuong, ct.sanPham.id) from CT_DonHang as ct where ct.donHang.id = ?1")
	List<CT_DonHang_QueryResult> getOrderDetail(int orderId);
	
	List<DonHang> findAll(Sort sort);
	
	@Query(value = "select count(*) from don_hang where (trang_thai = 'dang_giao_hang' or trang_thai = 'cho_xu_ly') and CONVERT(date, getdate()) = CONVERT(date, ngay_dat)"
			, nativeQuery = true)
	int countNewOrders();
	
	@Query(value = "select count(*) from don_hang", nativeQuery = true)
	int countOrders();
	
	@Query(value = "select sum(ct.so_luong * sp.gia) from don_hang dh join ct_don_hang ct on dh.id = ct.donhang_id join san_pham sp on ct.sanpham_id = sp.id where is_paid = 'true'", nativeQuery = true)
	double tinhTongDoanhThu();
	
	@Query(value = "select NEW server.repository.query_result.Report_QueryResult(ct.sanPham.id, ct.sanPham.tenSanPham, SUM(ct.soLuong))"
			+ " from CT_DonHang ct"
			+ " GROUP BY ct.sanPham.id, ct.sanPham.tenSanPham"
			+ " order by SUM(ct.soLuong) desc")
	List<Report_QueryResult> thongKeSanPhamBanChay();
	
	@Query(value = "SELECT * FROM don_hang WHERE trang_thai = ?1", nativeQuery = true)
	List<DonHang> findAllByStatus(String status);
	
	@Query(value = "SELECT * FROM don_hang WHERE CONVERT(date, ?1) = CONVERT(date, ngay_dat) and trang_thai = ?2", nativeQuery = true)
	List<DonHang> findAllByStatus(String date, String status);
	
	@Query(value = "SELECT * FROM don_hang WHERE CONVERT(date, ?1) = CONVERT(date, ngay_dat)", nativeQuery = true)
	List<DonHang> findAllByDate(String date);
}
