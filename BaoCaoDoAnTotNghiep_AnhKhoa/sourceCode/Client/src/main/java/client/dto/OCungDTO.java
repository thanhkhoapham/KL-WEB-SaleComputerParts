package client.dto;

public class OCungDTO extends SanPhamDTO{
	private String kieuOCung;
	private int dungLuong;
	private int tocDoDoc;
	private int tocDoGhi;
	private String chuanKetNoi;
	
	public OCungDTO() {
	}

	public String getKieuOCung() {
		return kieuOCung;
	}

	public void setKieuOCung(String kieuOCung) {
		this.kieuOCung = kieuOCung;
	}

	public int getDungLuong() {
		return dungLuong;
	}

	public void setDungLuong(int dungLuong) {
		this.dungLuong = dungLuong;
	}

	public int getTocDoDoc() {
		return tocDoDoc;
	}

	public void setTocDoDoc(int tocDoDoc) {
		this.tocDoDoc = tocDoDoc;
	}

	public int getTocDoGhi() {
		return tocDoGhi;
	}

	public void setTocDoGhi(int tocDoGhi) {
		this.tocDoGhi = tocDoGhi;
	}

	public String getChuanKetNoi() {
		return chuanKetNoi;
	}

	public void setChuanKetNoi(String chuanKetNoi) {
		this.chuanKetNoi = chuanKetNoi;
	}
	
	
}
