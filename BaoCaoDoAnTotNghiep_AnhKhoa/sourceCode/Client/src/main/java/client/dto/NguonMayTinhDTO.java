package client.dto;

public class NguonMayTinhDTO extends SanPhamDTO{
	private String dienDauVao;
	private int congSuatToiDa;
	private int quat;
	private String loaiDayCam;
	
	public NguonMayTinhDTO() {
	}

	public String getDienDauVao() {
		return dienDauVao;
	}

	public void setDienDauVao(String dienDauVao) {
		this.dienDauVao = dienDauVao;
	}

	public int getCongSuatToiDa() {
		return congSuatToiDa;
	}

	public void setCongSuatToiDa(int congSuatToiDa) {
		this.congSuatToiDa = congSuatToiDa;
	}

	public int getQuat() {
		return quat;
	}

	public void setQuat(int quat) {
		this.quat = quat;
	}

	public String getLoaiDayCam() {
		return loaiDayCam;
	}

	public void setLoaiDayCam(String loaiDayCam) {
		this.loaiDayCam = loaiDayCam;
	}
	
	
}
