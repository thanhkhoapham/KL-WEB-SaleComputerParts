package server.repository.query_result;

public class CT_DonHang_QueryResult {
	private int soLuong;
	private int sanPhamId;
	
	public CT_DonHang_QueryResult() {
	}

	

	public CT_DonHang_QueryResult(int soLuong, int sanPhamId) {
		super();
		this.soLuong = soLuong;
		this.sanPhamId = sanPhamId;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public int getSanPhamId() {
		return sanPhamId;
	}

	public void setSanPhamId(int sanPhamId) {
		this.sanPhamId = sanPhamId;
	}

}
