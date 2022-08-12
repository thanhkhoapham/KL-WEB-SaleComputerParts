package server.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import server.entity.CT_DonHang;
import server.entity.DonHang;
import server.repository.custom.DonHangCustomRepository;

public class DonHangCustomRepositoryImpl implements DonHangCustomRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<DonHang> findOrderLimit(int limit) {
		String q = "from DonHang dh order by dh.ngayDat desc";
		Query query = entityManager.createQuery(q, DonHang.class);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	

	

}
