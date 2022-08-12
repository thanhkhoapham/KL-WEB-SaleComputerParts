package server.dto;

import java.util.Date;
import java.util.List;

import server.enums.OrderStatus;

public class DonHangDTO {
	private int id;
	private Date ngayDat;
	private Date ngayGiao;
	private OrderStatus trangThai;
	private String phuongThuc;
	private boolean isPaid;
	private String tenNguoiDung;
	private String diaChi;
	private String soDienThoai;
	private double tongTien;
	
	//DS chi tiết đơn hàng
	private List<CT_GioHangDTO> dsCT;
	
	public DonHangDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getNgayDat() {
		return ngayDat;
	}

	public void setNgayDat(Date ngayDat) {
		this.ngayDat = ngayDat;
	}

	public Date getNgayGiao() {
		return ngayGiao;
	}

	public void setNgayGiao(Date ngayGiao) {
		this.ngayGiao = ngayGiao;
	}

	public OrderStatus getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(OrderStatus trangThai) {
		this.trangThai = trangThai;
	}

	public String getPhuongThuc() {
		return phuongThuc;
	}

	public void setPhuongThuc(String phuongThuc) {
		this.phuongThuc = phuongThuc;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public String getTenNguoiDung() {
		return tenNguoiDung;
	}

	public void setTenNguoiDung(String tenNguoiDung) {
		this.tenNguoiDung = tenNguoiDung;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public double getTongTien() {
		return tongTien;
	}

	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}

	public List<CT_GioHangDTO> getDsCT() {
		return dsCT;
	}

	public void setDsCT(List<CT_GioHangDTO> dsCT) {
		this.dsCT = dsCT;
	}

	
	
	
}
