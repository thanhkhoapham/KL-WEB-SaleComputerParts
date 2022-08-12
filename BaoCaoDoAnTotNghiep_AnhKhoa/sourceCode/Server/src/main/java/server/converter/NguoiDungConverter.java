package server.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import server.dto.UserDTO;
import server.entity.NguoiDung;

@Component
public class NguoiDungConverter {

	@Autowired
	private ModelMapper mapper;

	public NguoiDung toEntity(UserDTO dto) {
		NguoiDung dung = mapper.map(dto, NguoiDung.class);
		return dung;
	}

	public UserDTO toDto(NguoiDung nguoiDung) {
		UserDTO dto = mapper.map(nguoiDung, UserDTO.class);
		return dto;
	}
}
