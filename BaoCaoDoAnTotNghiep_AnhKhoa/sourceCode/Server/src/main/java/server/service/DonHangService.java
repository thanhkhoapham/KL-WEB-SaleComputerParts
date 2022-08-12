package server.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import server.dto.DonHangDTO;
import server.dto.GioHangDTO;
import server.dto.OrderReportDTO;
import server.dto.PaymentInfoDTO;
import server.dto.UserDTO;

public interface DonHangService {
	
	public void addOrder(GioHangDTO gioHangDTO);
	public void changeOrderStatus(int donHangID, String trThaiNew);
	public void addOrderDetails(int donHangId, GioHangDTO gioHangDTO);
	public List<DonHangDTO> getTop10NewestOrder();
	public List<DonHangDTO> getOrderListByUserId(int userId);
	public DonHangDTO getByOrderId(int orderId);
	public List<DonHangDTO> getAllOrder();
	public void sendOrderSuccessEmail(UserDTO dto) throws UnsupportedEncodingException, MessagingException;
	public List<DonHangDTO> getAllOrderByStatus(String status);
}
