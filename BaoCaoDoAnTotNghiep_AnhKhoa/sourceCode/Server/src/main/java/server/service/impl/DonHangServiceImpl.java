package server.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import server.converter.SanPhamConverter;
import server.dto.CT_GioHangDTO;
import server.dto.DonHangDTO;
import server.dto.GioHangDTO;
import server.dto.OrderReportDTO;
import server.dto.PaymentInfoDTO;
import server.dto.SanPhamDTO;
import server.dto.UserDTO;
import server.entity.CT_DonHang;
import server.entity.DonHang;
import server.entity.NguoiDung;
import server.entity.SanPham;
import server.enums.OrderStatus;
import server.repository.DonHangRepository;
import server.repository.NguoiDungRepository;
import server.repository.SanPhamRepository;
import server.repository.query_result.CT_DonHang_QueryResult;
import server.service.DonHangService;

@Service
public class DonHangServiceImpl implements DonHangService{

	@Autowired
	private DonHangRepository donHangRepository;
	@Autowired
	private NguoiDungRepository nguoiDungRepository;
	@Autowired
	private SanPhamRepository sanPhamRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private SanPhamConverter sanPhamConverter;
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void addOrder(GioHangDTO gioHangDTO) {
		DonHang donHang = new DonHang();
		
		
		Date ngayDat = new Date();
		donHang.setNgayDat(ngayDat);
		
		//Ngày giao sau ngày đặt 7 ngày
		Date ngayGiao = new Date(ngayDat.getTime() + (1000 * 60 * 60 * 24 * 7));
		donHang.setNgayGiao(ngayGiao);
		
		//Thông tin khách hàng
		donHang.setTenNguoiDung(gioHangDTO.getDonHangForm().getTenNguoiDung());
		donHang.setDiaChi(gioHangDTO.getDonHangForm().getDiaChi());
		donHang.setSoDienThoai(gioHangDTO.getDonHangForm().getSoDienThoai());
		NguoiDung nguoiDung = nguoiDungRepository.findById(gioHangDTO.getDonHangForm().getNguoiDungId()).orElse(null);
		donHang.setNguoiDung(nguoiDung);
		
		donHang.setPhuongThuc(gioHangDTO.getDonHangForm().getPhuongThuc());
		donHang.setTrangThai(OrderStatus.cho_xu_ly.toString());
		
		if (gioHangDTO.getPaymentInfoDTO() != null) {
			donHang.setMaTaiKhoan(gioHangDTO.getPaymentInfoDTO().getPayerId());
			donHang.setMaThanhToan(gioHangDTO.getPaymentInfoDTO().getPaymentId());
		}
		donHang.setPaid(gioHangDTO.getDonHangForm().isPaid());
		
		donHangRepository.saveAndFlush(donHang);
		addOrderDetails(donHang.getId(), gioHangDTO);
		
		
	}
	
	@Override
	public void sendOrderSuccessEmail(UserDTO dto) throws UnsupportedEncodingException, MessagingException {
		String subject = "[...] Thông báo đặt hàng thành công";
		String senderName = "Shop";
		String mailContent = "<p>Chào " + dto.getTenNguoiDung() + ",</p>";
		mailContent += "<p>Cảm ơn bạn vì đã sử dụng dịch vụ của chúng tôi. Đơn hàng của bạn sẽ được xử lý trong thời gian sớm nhất. </p>";
		mailContent += "<p>Chi tiết đơn hàng vui lòng xem trong 'Đơn hàng của tôi'. <br>";
		mailContent += "<p>Mọi thắc mắc vui lòng liên hệ qua email: thanhkhoapham03@gmail.com <br>";
		mailContent += "<p>Trân trọng. <br>";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setFrom("giaanh7140@gmail.com", senderName);
		helper.setTo(dto.getEmail());
		helper.setSubject(subject);
		helper.setText(mailContent, true);

		mailSender.send(message);
	}
	
	@Override
	public void addOrderDetails(int donHangId, GioHangDTO gioHangDTO) {
		List<CT_GioHangDTO> items = gioHangDTO.getDsCT();
		List<CT_DonHang> dsCT = new ArrayList<CT_DonHang>();
		DonHang dh = donHangRepository.findById(donHangId).orElse(null);
		for (CT_GioHangDTO ct_GioHangDTO : items) {
			CT_DonHang ct_DonHang = new CT_DonHang();
			SanPham sp = sanPhamRepository.findById(ct_GioHangDTO.getSanPham().getId()).orElse(null);
			ct_DonHang.setSanPham(sp);
			ct_DonHang.setDonHang(dh);
			ct_DonHang.setSoLuong(ct_GioHangDTO.getSoLuong());
			
			dsCT.add(ct_DonHang);
		}
		dh.setDsChiTiet(dsCT);
		donHangRepository.save(dh);
		
	}

	@Override
	public void changeOrderStatus(int donHangID, String trThaiNew) {
		DonHang donHangNew = donHangRepository.findById(donHangID).orElse(null);
		donHangNew.setTrangThai(trThaiNew);
		donHangRepository.save(donHangNew);
	}

	@Override
	public List<DonHangDTO> getTop10NewestOrder() {
		List<DonHang> orders = donHangRepository.findOrderLimit(10);
		List<DonHangDTO> dsDTO = new ArrayList<DonHangDTO>();
		for (DonHang order : orders) {			
			DonHangDTO dto = modelMapper.map(order, DonHangDTO.class);
			dto.setTongTien(order.tinhTongTien());
			dsDTO.add(dto);
		}
		return dsDTO;
	}

	@Override
	public List<DonHangDTO> getOrderListByUserId(int userId) {
		List<DonHang> orders = donHangRepository.findAllByUserId(userId);
		List<DonHangDTO> dsDTO = new ArrayList<DonHangDTO>();
		for (DonHang order : orders) {			
			DonHangDTO dto = modelMapper.map(order, DonHangDTO.class);
			dto.setTongTien(order.tinhTongTien());
			dsDTO.add(dto);
		}
		return dsDTO;
	}

	@Override
	public DonHangDTO getByOrderId(int orderId) {
		DonHang order = donHangRepository.getById(orderId);
		DonHangDTO dto = modelMapper.map(order, DonHangDTO.class);
		List<CT_DonHang_QueryResult> dsct = donHangRepository.getOrderDetail(orderId);
		List<CT_GioHangDTO> dsDTO = new ArrayList<CT_GioHangDTO>();
		
		for (CT_DonHang_QueryResult queryResult : dsct) {
			CT_GioHangDTO ctdto = new CT_GioHangDTO();
			SanPham sp = sanPhamRepository.getById(queryResult.getSanPhamId());
			SanPhamDTO spdto = sanPhamConverter.toDto(sp);
			ctdto.setSanPham(spdto);
			
			ctdto.setSoLuong(queryResult.getSoLuong());
			
			double thanhTien = queryResult.getSoLuong() * sp.getGia();
			ctdto.setThanhTien(thanhTien);
			dsDTO.add(ctdto);
		}
		
		dto.setTongTien(order.tinhTongTien());
		dto.setDsCT(dsDTO);
		return dto;
	}

	@Override
	public List<DonHangDTO> getAllOrder() {
		List<DonHang> orders = donHangRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		List<DonHangDTO> dsDTO = new ArrayList<DonHangDTO>();
		for (DonHang order : orders) {			
			DonHangDTO dto = modelMapper.map(order, DonHangDTO.class);
			dto.setTongTien(order.tinhTongTien());
			dsDTO.add(dto);
		}
		return dsDTO;
	}

	@Override
	public List<DonHangDTO> getAllOrderByStatus(String status) {
		List<DonHang> orders;
		if (status.trim().equals("all")) {
			orders = donHangRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		}else {
			orders = donHangRepository.findAllByStatus(status);
		}
		
		List<DonHangDTO> dsDTO = new ArrayList<DonHangDTO>();
		for (DonHang order : orders) {			
			DonHangDTO dto = modelMapper.map(order, DonHangDTO.class);
			dto.setTongTien(order.tinhTongTien());
			dsDTO.add(dto);
		}
		return dsDTO;
	}

	

}
