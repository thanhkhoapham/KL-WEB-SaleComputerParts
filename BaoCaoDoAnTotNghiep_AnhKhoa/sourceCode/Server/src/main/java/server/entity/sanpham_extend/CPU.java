package server.entity.sanpham_extend;

import javax.persistence.Entity;

import server.entity.SanPham;

@Entity(name = "cpu")
public class CPU extends SanPham{
	/**
	 * 
	 */
	private static final long serialVersionUID = -219093390822971793L;
	
	private String loaiCPU;
	private int soNhan;
	private int soLuongCPU;
	private int boNhoDem;
	
	public CPU() {
	}

	public String getLoaiCPU() {
		return loaiCPU;
	}

	public void setLoaiCPU(String loaiCPU) {
		this.loaiCPU = loaiCPU;
	}

	public int getSoNhan() {
		return soNhan;
	}

	public void setSoNhan(int soNhan) {
		this.soNhan = soNhan;
	}

	public int getSoLuongCPU() {
		return soLuongCPU;
	}

	public void setSoLuongCPU(int soLuongCPU) {
		this.soLuongCPU = soLuongCPU;
	}

	public int getBoNhoDem() {
		return boNhoDem;
	}

	public void setBoNhoDem(int boNhoDem) {
		this.boNhoDem = boNhoDem;
	}
	
	
}
