package server.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "nguoi_dung")
public class NguoiDung implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6356283277193176289L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(columnDefinition = "nvarchar(255)")
	private String tenNguoiDung;
	@Column(columnDefinition = "nvarchar(255)")
	private String diaChi;
	private String soDienThoai;

	@Column(unique = true)
	private String email;
	private String matKhau;
	private Date ngayTao;
	private boolean enable;
	private String maXacThuc;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	@OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<DonHang> dsDonHang;

	public NguoiDung() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTenNguoiDung() {
		return tenNguoiDung;
	}

	public void setTenNguoiDung(String tenNguoiDung) {
		this.tenNguoiDung = tenNguoiDung;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public Date getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(Date ngayTao) {
		this.ngayTao = ngayTao;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getMaXacThuc() {
		return maXacThuc;
	}

	public void setMaXacThuc(String maXacThuc) {
		this.maXacThuc = maXacThuc;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<DonHang> getDsDonHang() {
		return dsDonHang;
	}

	public void setDsDonHang(List<DonHang> dsDonHang) {
		this.dsDonHang = dsDonHang;
	}

}
