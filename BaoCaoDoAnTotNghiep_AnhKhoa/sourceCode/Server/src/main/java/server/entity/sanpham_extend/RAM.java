package server.entity.sanpham_extend;

import javax.persistence.Entity;

import server.entity.SanPham;

@Entity(name = "ram")
public class RAM extends SanPham{

	/**
	 * 
	 */
	private static final long serialVersionUID = 155903217028610215L;
	
	private String loaiRam;
	private int dungLuong;
	private int tocDo;
	
	public RAM() {
	}

	public String getLoaiRam() {
		return loaiRam;
	}

	public void setLoaiRam(String loaiRam) {
		this.loaiRam = loaiRam;
	}

	public int getDungLuong() {
		return dungLuong;
	}

	public void setDungLuong(int dungLuong) {
		this.dungLuong = dungLuong;
	}

	public int getTocDo() {
		return tocDo;
	}

	public void setTocDo(int tocDo) {
		this.tocDo = tocDo;
	}
	
	
}
