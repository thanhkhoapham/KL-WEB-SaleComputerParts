package server.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CT_DonHang implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "donhang_id")
	private DonHang donHang;
	
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "sanpham_id")
	private SanPham sanPham;
	
	private int soLuong;
	
	public CT_DonHang() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DonHang getDonHang() {
		return donHang;
	}

	public void setDonHang(DonHang donHang) {
		this.donHang = donHang;
	}

	public SanPham getSanPham() {
		return sanPham;
	}

	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((donHang == null) ? 0 : donHang.hashCode());
		result = prime * result + id;
		result = prime * result + ((sanPham == null) ? 0 : sanPham.hashCode());
		result = prime * result + soLuong;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CT_DonHang other = (CT_DonHang) obj;
		if (donHang == null) {
			if (other.donHang != null)
				return false;
		} else if (!donHang.equals(other.donHang))
			return false;
		if (id != other.id)
			return false;
		if (sanPham == null) {
			if (other.sanPham != null)
				return false;
		} else if (!sanPham.equals(other.sanPham))
			return false;
		if (soLuong != other.soLuong)
			return false;
		return true;
	}
}
