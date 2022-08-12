package client.dto;

public class Report_QueryResult {
	private int sanPhamId;
	private String tenSanPham;
	private double tongThanhTien;
	private long tongSoLuong;
	
	public Report_QueryResult() {
	}
	

	public Report_QueryResult(int sanPhamId, String tenSanPham, double tongThanhTien, long tongSoLuong) {
		super();
		this.sanPhamId = sanPhamId;
		this.tenSanPham = tenSanPham;
		this.tongThanhTien = tongThanhTien;
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

	public double getTongThanhTien() {
		return tongThanhTien;
	}

	public void setTongThanhTien(double tongThanhTien) {
		this.tongThanhTien = tongThanhTien;
	}


	public long getTongSoLuong() {
		return tongSoLuong;
	}


	public void setTongSoLuong(long tongSoLuong) {
		this.tongSoLuong = tongSoLuong;
	}

	
	
	
}
