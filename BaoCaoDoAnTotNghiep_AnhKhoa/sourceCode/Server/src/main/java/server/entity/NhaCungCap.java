package server.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "nha_cung_cap")
public class NhaCungCap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1276966843752398200L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(columnDefinition = "nvarchar(255)")
	private String tenNhaCungCap;
	@Column(columnDefinition = "nvarchar(255)")
	private String diaChi;
	private String soDienThoai;
	private boolean enable;

	@OneToMany(mappedBy = "nhaCungCap", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<SanPham> dsSanPham;

	public NhaCungCap() {
	}

	public NhaCungCap(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTenNhaCungCap() {
		return tenNhaCungCap;
	}

	public void setTenNhaCungCap(String tenNhaCungCap) {
		this.tenNhaCungCap = tenNhaCungCap;
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

	public List<SanPham> getDsSanPham() {
		return dsSanPham;
	}

	public void setDsSanPham(List<SanPham> dsSanPham) {
		this.dsSanPham = dsSanPham;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	
}
