package client.dto;

import java.util.List;

public class SanPhamPagingDTO {
	private List<SanPhamDTO> content;
    private int pageNo;
    private int totalPages;
    
    public SanPhamPagingDTO() {
	}
    
	public List<SanPhamDTO> getContent() {
		return content;
	}
	
	public void setContent(List<SanPhamDTO> content) {
		this.content = content;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public int getTotalPages() {
		return totalPages;
	}
	
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

    
}
