package client.dto;

import client.enums.ProductType;

public class SanPhamDTO {

	private int id;
	private String tenSanPham;
	private double gia;
	private String moTa;
	private ProductType loaiSanPham;
	private String hangSanXuat;
	private String urlHinhAnh;
	private int nhaCungCapId;

	public SanPhamDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTenSanPham() {
		return tenSanPham;
	}

	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}

	public double getGia() {
		return gia;
	}

	public void setGia(double gia) {
		this.gia = gia;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public String getHangSanXuat() {
		return hangSanXuat;
	}

	public void setHangSanXuat(String hangSanXuat) {
		this.hangSanXuat = hangSanXuat;
	}

	public String getUrlHinhAnh() {
		return urlHinhAnh;
	}

	public void setUrlHinhAnh(String urlHinhAnh) {
		this.urlHinhAnh = urlHinhAnh;
	}

	public int getNhaCungCapId() {
		return nhaCungCapId;
	}

	public void setNhaCungCapId(int nhaCungCapId) {
		this.nhaCungCapId = nhaCungCapId;
	}

	public ProductType getLoaiSanPham() {
		return loaiSanPham;
	}

	public void setLoaiSanPham(ProductType loaiSanPham) {
		this.loaiSanPham = loaiSanPham;
	}

	

}
