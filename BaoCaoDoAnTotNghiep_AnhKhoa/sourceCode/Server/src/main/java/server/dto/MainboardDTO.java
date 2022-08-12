package server.dto;

public class MainboardDTO extends SanPhamDTO{
	private String chipset;
	private String socket;
	private String cpuHoTro;
	private String loaiRam;
	private int soKheRam;
	private String pci;
	
	public MainboardDTO() {
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
	
	
}
