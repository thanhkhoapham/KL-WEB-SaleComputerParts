package client.dto;

public class SanPhamPostDTO {
	private int id;
	private String tenSanPham;
	private double gia;
	private String moTa;
	private String UrlHinhAnh;
	private String loaiSanPham;
	private String hangSanXuat;
	private int nhaCungCapId;

	// CPU
	private String loaiCPU;
	private int soNhan;
	private int soLuongCPU;
	private int boNhoDem;
	// Mainboard
	private String chipset;
	private String socket;
	private String cpuHoTro;
	private String loaiRam;
	private int soKheRam;
	private String pci;
	// RAM
	private int dungLuong;
	private int tocDo;
	// Ổ cứng
	private String kieuOCung;
	private int tocDoDoc;
	private int tocDoGhi;
	private String chuanKetNoi;
	// VGA
	private String chipDoHoa;
	private int boNho;
	private String ocMode;
	private String gamingMode;
	private int cudaCores;
	private String loaiCard;
	// Nguồn máy tính
	private String dienDauVao;
	private int congSuatToiDa;
	private int quat;
	private String loaiDayCam;

	public SanPhamPostDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTenSanPham() {
		return tenSanPham;
	}

	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}

	public double getGia() {
		return gia;
	}

	public void setGia(double gia) {
		this.gia = gia;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public String getUrlHinhAnh() {
		return UrlHinhAnh;
	}

	public void setUrlHinhAnh(String urlHinhAnh) {
		UrlHinhAnh = urlHinhAnh;
	}

	public String getLoaiSanPham() {
		return loaiSanPham;
	}

	public void setLoaiSanPham(String loaiSanPham) {
		this.loaiSanPham = loaiSanPham;
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

	public String getChipset() {
		return chipset;
	}

	public void setChipset(String chipset) {
		this.chipset = chipset;
	}

	public String getSocket() {
		return socket;
	}

	public void setSocket(String socket) {
		this.socket = socket;
	}

	public String getCpuHoTro() {
		return cpuHoTro;
	}

	public void setCpuHoTro(String cpuHoTro) {
		this.cpuHoTro = cpuHoTro;
	}

	public String getLoaiRam() {
		return loaiRam;
	}

	public void setLoaiRam(String loaiRam) {
		this.loaiRam = loaiRam;
	}

	public int getSoKheRam() {
		return soKheRam;
	}

	public void setSoKheRam(int soKheRam) {
		this.soKheRam = soKheRam;
	}

	public String getPci() {
		return pci;
	}

	public void setPci(String pci) {
		this.pci = pci;
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

	public String getKieuOCung() {
		return kieuOCung;
	}

	public void setKieuOCung(String kieuOCung) {
		this.kieuOCung = kieuOCung;
	}

	public int getTocDoDoc() {
		return tocDoDoc;
	}

	public void setTocDoDoc(int tocDoDoc) {
		this.tocDoDoc = tocDoDoc;
	}

	public int getTocDoGhi() {
		return tocDoGhi;
	}

	public void setTocDoGhi(int tocDoGhi) {
		this.tocDoGhi = tocDoGhi;
	}

	public String getChuanKetNoi() {
		return chuanKetNoi;
	}

	public void setChuanKetNoi(String chuanKetNoi) {
		this.chuanKetNoi = chuanKetNoi;
	}

	public String getChipDoHoa() {
		return chipDoHoa;
	}

	public void setChipDoHoa(String chipDoHoa) {
		this.chipDoHoa = chipDoHoa;
	}

	public int getBoNho() {
		return boNho;
	}

	public void setBoNho(int boNho) {
		this.boNho = boNho;
	}

	public String getOcMode() {
		return ocMode;
	}

	public void setOcMode(String ocMode) {
		this.ocMode = ocMode;
	}

	public String getGamingMode() {
		return gamingMode;
	}

	public void setGamingMode(String gamingMode) {
		this.gamingMode = gamingMode;
	}

	public int getCudaCores() {
		return cudaCores;
	}

	public void setCudaCores(int cudaCores) {
		this.cudaCores = cudaCores;
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

	public String getHangSanXuat() {
		return hangSanXuat;
	}

	public void setHangSanXuat(String hangSanXuat) {
		this.hangSanXuat = hangSanXuat;
	}

	public int getNhaCungCapId() {
		return nhaCungCapId;
	}

	public void setNhaCungCapId(int nhaCungCapId) {
		this.nhaCungCapId = nhaCungCapId;
	}

	public String getLoaiCard() {
		return loaiCard;
	}

	public void setLoaiCard(String loaiCard) {
		this.loaiCard = loaiCard;
	}

	@Override
	public String toString() {
		return "SanPhamPostDTO [id=" + id + ", tenSanPham=" + tenSanPham + ", gia=" + gia + ", moTa=" + moTa
				+ ", loaiSanPham=" + loaiSanPham + "]";
	}

	
}
