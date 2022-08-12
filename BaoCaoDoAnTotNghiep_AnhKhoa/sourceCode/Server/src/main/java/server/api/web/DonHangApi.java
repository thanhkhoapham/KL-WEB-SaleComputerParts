package server.api.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server.dto.DonHangDTO;
import server.dto.GioHangDTO;
import server.dto.UserDTO;
import server.entity.NguoiDung;
import server.repository.NguoiDungRepository;
import server.service.DonHangService;

@RestController
@RequestMapping("/order")
public class DonHangApi {
	
	@Autowired
	private DonHangService donHangService;
	@Autowired
	private NguoiDungRepository nguoiDungRepository;
	
	@PostMapping("/save")
	public Object saveOrder(@RequestBody GioHangDTO gioHangDTO) {
		try {
			donHangService.addOrder(gioHangDTO);
			
			NguoiDung nguoiDung = nguoiDungRepository.findById(gioHangDTO.getDonHangForm().getNguoiDungId()).orElse(null);
			UserDTO dto = new UserDTO();
			dto.setTenNguoiDung(nguoiDung.getTenNguoiDung());
			dto.setEmail(nguoiDung.getEmail());
			donHangService.sendOrderSuccessEmail(dto);
			return new ResponseEntity<Object>("Order Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Order Failed", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/list/{id}")
	public Object getOrderListByUserId(@PathVariable("id")int userId) {
		try {
			return new ResponseEntity<List<DonHangDTO>>(donHangService.getOrderListByUserId(userId), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Order Failed", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}")
	public Object getOrderById(@PathVariable("id")int orderId) {
		try {
			return new ResponseEntity<DonHangDTO>(donHangService.getByOrderId(orderId), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Get Order Failed", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/cancel/{id}")
	public void cancelOrder(@PathVariable("id")int orderId) {
		try {
			donHangService.changeOrderStatus(orderId, "da_huy");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
