package server.entity.sanpham_extend;

import javax.persistence.Entity;

import server.entity.SanPham;

@Entity(name = "ocung")
public class OCung extends SanPham{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6580893009281270262L;

	private String kieuOCung;
	private int dungLuong;
	private int tocDoDoc;
	private int tocDoGhi;
	private String chuanKetNoi;
	
	public OCung() {
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
