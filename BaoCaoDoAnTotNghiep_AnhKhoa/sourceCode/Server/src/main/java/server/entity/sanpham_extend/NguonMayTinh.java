package server.entity.sanpham_extend;

import javax.persistence.Column;
import javax.persistence.Entity;

import server.entity.SanPham;

@Entity(name = "nguonmaytinh")
public class NguonMayTinh extends SanPham{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5406344560351815545L;
	
	private String dienDauVao;
	private int congSuatToiDa;
	private int quat;
	@Column(columnDefinition = "nvarchar(255)")
	private String loaiDayCam;
	
	public NguonMayTinh() {
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
