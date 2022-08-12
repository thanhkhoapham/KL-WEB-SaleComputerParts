package server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.dto.ReportDTO;
import server.repository.DonHangRepository;
import server.repository.NguoiDungRepository;
import server.repository.query_result.Report_QueryResult;
import server.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService{

	@Autowired
	private DonHangRepository donHangRepository;
	@Autowired
	private NguoiDungRepository nguoiDungRepository;
	
	@Override
	public ReportDTO createReport() {
		ReportDTO dto = new ReportDTO();
		
		dto.setSoHoaDonMoi(donHangRepository.countNewOrders());
		dto.setTongHoaDon(donHangRepository.countOrders());
		dto.setTongNguoiDung(nguoiDungRepository.countUsers());
		dto.setTongDoanhThu(donHangRepository.tinhTongDoanhThu());
		return dto;
	}

	@Override
	public List<Report_QueryResult> getBestSeller() {
		List<Report_QueryResult> qr = donHangRepository.thongKeSanPhamBanChay();
		
		return qr;
	}

}
