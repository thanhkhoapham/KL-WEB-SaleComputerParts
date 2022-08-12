package client.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import client.dto.NhaCungCapDTO;


public interface NhaCungCapService {
	List<NhaCungCapDTO> getAllSupplier(String token);

	NhaCungCapDTO getSupplierById(int id, String token);

	void addSupplier(NhaCungCapDTO capDTO, String token) throws JsonProcessingException;

	void deleteSupplier(int id, String token);

	void updateSupplier(int id, NhaCungCapDTO newSupp, String token) throws JsonProcessingException;

}
