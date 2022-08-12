package client.dto;

public class RamDTO extends SanPhamDTO{
	private String loaiRam;
	private int dungLuong;
	private int tocDo;
	
	public RamDTO() {
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
