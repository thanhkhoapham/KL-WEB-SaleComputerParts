package client.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import client.dto.DonHangDTO;
import client.dto.GioHangDTO;
import client.dto.OrderReportDTO;

public interface DonHangService {
	public String addOrder(GioHangDTO gioHangDTO, String token);
	public List<DonHangDTO> getTop10(String token);
	public List<DonHangDTO> getAllOrder(String token);
	public List<DonHangDTO> getListByUserId(int userId, String token);
	public DonHangDTO getByOrderId(int orderId, String token);
	public void changeOrderStatus(int orderId, String newStatus, String token);
	public List<DonHangDTO> getAllOrderByStatus(String status, String token);
}
