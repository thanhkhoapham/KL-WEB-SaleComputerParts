package client.dto;

public class VgaDTO extends SanPhamDTO{
	private String chipDoHoa;
	private int boNho;
	private String ocMode;
	private String gamingMode;
	private int cudaCores;
	private String loaiCard;
	
	public VgaDTO() {
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

	public String getLoaiCard() {
		return loaiCard;
	}

	public void setLoaiCard(String loaiCard) {
		this.loaiCard = loaiCard;
	}
	
	
}
