package server.enums;

public enum OrderStatus {
	cho_xu_ly("Chờ xử lý"),
	dang_giao_hang("Đang giao hàng"),
	da_giao_hang("Đã giao"), 
	da_huy("Đã hủy");
	
	private final String displayValue;
	
	private OrderStatus(String displayValue) {
		this.displayValue = displayValue;
	}
	    
    public String getDisplayValue() {
        return displayValue;
    }
}
