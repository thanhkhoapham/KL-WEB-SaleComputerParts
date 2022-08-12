package client.enums;

public enum ProductType {
    cpu("CPU"), 
    mainboard("Mainboard"),
    ram("RAM"),
    ocung("Ổ cứng"),
    vga("VGA"),
    nguonmaytinh("Nguồn máy tính");
    
    private final String displayValue;
    
    private ProductType(String displayValue) {
        this.displayValue = displayValue;
    }
    
    public String getDisplayValue() {
        return displayValue;
    }
}