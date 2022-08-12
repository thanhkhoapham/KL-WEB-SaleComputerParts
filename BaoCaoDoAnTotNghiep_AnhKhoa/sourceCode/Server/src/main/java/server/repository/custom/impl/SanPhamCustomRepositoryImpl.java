package server.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import server.entity.SanPham;
import server.repository.custom.SanPhamCustomRepository;

public class SanPhamCustomRepositoryImpl implements SanPhamCustomRepository{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private final int maxPageSize = 12;
	

	@Override
	public List<SanPham> findAllProductPageable(int page, String category, String productType, String sort) {
		if (page==0) {
			page = 1;
		}
		int pageSize = maxPageSize;
		int firstResult = (page-1) * pageSize;
		
		String q = "from SanPham where enable = 'true'";
		if (category != null) {
			q += " and loai_sanpham = :category";
			if (productType != null) {
				switch (category) {
					case "cpu": case "mainboard":
						if (productType.trim().equals("intel")) {
							q += " and lower(hang_san_xuat) like '%intel%'";
						}else if (productType.trim().equals("amd")){
							q += " and lower(hang_san_xuat) like '%amd%'";
						}
						break;
					case "ram":
						if (productType.trim().equals("ddr3")) {
							q += " and loai_ram = 'DDR3'";
						}else if (productType.trim().equals("ddr4")){
							q += " and loai_ram = 'DDR4'";
						}else if (productType.trim().equals("ddr5")){
							q += " and loai_ram = 'DDR5'";
						}
						break;
					case "ocung":
						if (productType.trim().equals("ssd")) {
							q += " and kieuocung = 'SSD'";
						}else if (productType.trim().equals("hdd")){
							q += " and kieuocung = 'HDD'";
						}
						break;
					case "vga":
						if (productType.trim().equals("amd")) {
							q += " and loai_card = 'AMD'";
						}else if (productType.trim().equals("nvidia")){
							q += " and loai_card = 'NVIDIA'";
						}
						break;
					default:
						break;
				}
			}
		}
		if (sort != null) {
//			q += " and gia > :from and gia < :to";
			switch (sort) {
				case "gia-giam-dan":
					q += " order by gia desc";
					break;
				case "gia-tang-dan":
					q += " order by gia";
					break;
			default:
				break;
			}
		}else {
			q += " order by id desc";
		}
		
		System.err.println(q);
		
		Query query = entityManager.createQuery(q, SanPham.class);
		if (category != null) {
			query.setParameter("category", category);
		}
//		if (sortDTO != null) {
//			query.setParameter("from", sortDTO.getFrom());
//			query.setParameter("to", sortDTO.getTo());
//		}
		query.setFirstResult(firstResult);
		query.setMaxResults(pageSize);
		List<SanPham> dssp = query.getResultList();
		return dssp;
	}


	@Override
	public List<SanPham> searchProductByNamePageable(String keyword, int page) {
		if (page==0) {
			page = 1;
		}
		int pageSize = maxPageSize;
		int firstResult = (page-1) * pageSize;
		
		String q = "from SanPham where enable = 'true' and lower(tenSanPham) like :tenSP order by id desc";
		Query query = entityManager.createQuery(q, SanPham.class);
		query.setParameter("tenSP",  "%" + keyword.toLowerCase() + "%");		
		query.setFirstResult(firstResult);
		query.setMaxResults(pageSize);
		List<SanPham> dssp = query.getResultList();
		return dssp;
	}

}
