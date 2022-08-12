package server.repository.custom;

import java.util.List;

import server.entity.DonHang;

public interface DonHangCustomRepository {
	List<DonHang> findOrderLimit(int limit);
}
