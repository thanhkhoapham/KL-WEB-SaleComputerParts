package server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import server.converter.NguoiDungConverter;
import server.dto.UserDTO;
import server.dto.UserDetailDTO;
import server.dto.UserInfoDTO;
import server.entity.NguoiDung;
import server.entity.Role;
import server.repository.NguoiDungRepository;
import server.service.NguoiDungService;

@Service
public class NguoiDungServiceImpl implements NguoiDungService{

	@Autowired
	private NguoiDungRepository repository;
	@Autowired
	private NguoiDungConverter converter;
	
	@Override
	public UserDTO getUserById(int id) {
		NguoiDung dung = repository.getById(id);
		UserDTO dto = converter.toDto(dung);
		return dto;
	}

	@Override
	public UserInfoDTO getUserInfo(NguoiDung nguoiDung) {
		UserInfoDTO dto = new UserInfoDTO();
		dto.setId(nguoiDung.getId());
		dto.setTenNguoiDung(nguoiDung.getTenNguoiDung());
		dto.setDiaChi(nguoiDung.getDiaChi());
		dto.setEmail(nguoiDung.getEmail());
		dto.setSoDienThoai(nguoiDung.getSoDienThoai());
		dto.setRole(nguoiDung.getRole().getTenRole());
		return dto;
	}

	@Override
	public UserInfoDTO getUserInfoById(int id) {
		NguoiDung nguoiDung = repository.findById(id).orElse(null);
		if (nguoiDung != null) {
			return getUserInfo(nguoiDung);
		}
		return null;
	}
	
	@Override
	public List<UserDTO> getListUser() {
		List<NguoiDung> dungs = repository.findAll();
		List<UserDTO> list = new ArrayList<UserDTO>();
		for (NguoiDung o : dungs) {
			//Chỉ lấy danh sách người dùng có role là USER
			if (o.getRole().getId() == 3) {
				list.add(new UserDTO(o.getId(), o.getTenNguoiDung(), o.getDiaChi(), o.getSoDienThoai(), o.getEmail(),
						o.getMatKhau(), o.isEnable()));
			}
		}
		return list;
	}
	
	@Override
	public void addUser(UserDTO dto) {
		String hashedPassword = BCrypt.hashpw(dto.getMatKhau(), BCrypt.gensalt());
		NguoiDung nguoiDung = new NguoiDung();
		nguoiDung.setEmail(dto.getEmail());
		nguoiDung.setMatKhau(hashedPassword);
		nguoiDung.setRole(new Role(3));
		nguoiDung.setEnable(true);
		nguoiDung.setTenNguoiDung(dto.getTenNguoiDung());
		nguoiDung.setSoDienThoai(dto.getSoDienThoai());
		nguoiDung.setDiaChi(dto.getDiaChi());
		nguoiDung.setMaXacThuc(null);
		nguoiDung.setNgayTao(new Date());
		repository.save(nguoiDung);
	}

	@Override
	public void deleteUser(int id) {
		NguoiDung dung = repository.getById(id);
		dung.setEnable(false);
		repository.save(dung);
	}

	@Override
	public void updateUser(int id, UserDTO value) {
		NguoiDung dung = repository.getById(id);
		dung.setDiaChi(value.getDiaChi());
		if (value.getMatKhau() != null && !value.getMatKhau().trim().equals("")) {
			String hashedPassword = BCrypt.hashpw(value.getMatKhau(), BCrypt.gensalt());
			dung.setMatKhau(hashedPassword);
		}
		
		dung.setSoDienThoai(value.getSoDienThoai());
		dung.setTenNguoiDung(value.getTenNguoiDung());
		repository.save(dung);
	}

	@Override
	public UserDetailDTO getUserDetailByEmail(String email) {
		NguoiDung nguoiDung = repository.findUserByEmail(email);
		if (nguoiDung != null) {
			UserDetailDTO dto = new UserDetailDTO();
			dto.setEmail(nguoiDung.getEmail());
			dto.setMatKhau(nguoiDung.getMatKhau());
			dto.setRole(nguoiDung.getRole().getTenRole());
			return dto;
		}
		return null;
	}
}
