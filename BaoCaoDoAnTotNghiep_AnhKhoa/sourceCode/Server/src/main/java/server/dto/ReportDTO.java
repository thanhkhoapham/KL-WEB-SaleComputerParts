package server.dto;

public class ReportDTO {
	private int tongHoaDon;
	private double tongDoanhThu;
	private int tongNguoiDung;
	private int soHoaDonMoi;
	
	public ReportDTO() {
	}

	public int getTongHoaDon() {
		return tongHoaDon;
	}

	public void setTongHoaDon(int tongHoaDon) {
		this.tongHoaDon = tongHoaDon;
	}

	public double getTongDoanhThu() {
		return tongDoanhThu;
	}

	public void setTongDoanhThu(double tongDoanhThu) {
		this.tongDoanhThu = tongDoanhThu;
	}

	public int getTongNguoiDung() {
		return tongNguoiDung;
	}

	public void setTongNguoiDung(int tongNguoiDung) {
		this.tongNguoiDung = tongNguoiDung;
	}

	public int getSoHoaDonMoi() {
		return soHoaDonMoi;
	}

	public void setSoHoaDonMoi(int soHoaDonMoi) {
		this.soHoaDonMoi = soHoaDonMoi;
	}
	
	
}
