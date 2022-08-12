package server.dto;

public class OrderReportDTO {
	private String ngay;
	private String trangThai;

	public OrderReportDTO() {
	}

	public OrderReportDTO(String ngay, String trangThai) {
		super();
		this.ngay = ngay;
		this.trangThai = trangThai;
	}

	public String getNgay() {
		return ngay;
	}

	public void setNgay(String ngay) {
		this.ngay = ngay;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	@Override
	public String toString() {
		return "OrderReportDTO [ngay=" + ngay + ", trangThai=" + trangThai + "]";
	}

}
