package server.dto;

public class CpuDTO extends SanPhamDTO{
	private String loaiCPU;
	private int soNhan;
	private int soLuongCPU;
	private int boNhoDem;
	
	public CpuDTO() {
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

	@Override
	public String toString() {
		return "CpuDTO [ten=" + getTenSanPham() + ", soNhan=" + soNhan + ", soLuongCPU=" + soLuongCPU + ", boNhoDem="
				+ boNhoDem + "]"+" hang"+ super.getHangSanXuat();
	}
	
	
}
