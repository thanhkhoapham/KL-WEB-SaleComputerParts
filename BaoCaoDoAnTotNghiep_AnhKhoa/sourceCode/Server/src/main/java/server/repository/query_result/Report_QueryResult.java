package server.repository.query_result;

public class Report_QueryResult {
	private int sanPhamId;
	private String tenSanPham;
	private long tongSoLuong;
	
	public Report_QueryResult() {
	}
	

	public Report_QueryResult(int sanPhamId, String tenSanPham, long tongSoLuong) {
		super();
		this.sanPhamId = sanPhamId;
		this.tenSanPham = tenSanPham;
		this.tongSoLuong = tongSoLuong;
	}

	public int getSanPhamId() {
		return sanPhamId;
	}

	public void setSanPhamId(int sanPhamId) {
		this.sanPhamId = sanPhamId;
	}

	public String getTenSanPham() {
		return tenSanPham;
	}

	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}

	public long getTongSoLuong() {
		return tongSoLuong;
	}


	public void setTongSoLuong(long tongSoLuong) {
		this.tongSoLuong = tongSoLuong;
	}

	
	
	
}
