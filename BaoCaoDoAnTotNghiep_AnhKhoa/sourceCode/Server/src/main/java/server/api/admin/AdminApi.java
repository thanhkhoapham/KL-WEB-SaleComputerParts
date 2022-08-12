package server.api.admin;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import server.dto.DonHangDTO;
import server.dto.NhaCungCapDTO;
import server.dto.OrderReportDTO;
import server.dto.ReportDTO;
import server.dto.SanPhamPostDTO;
import server.dto.UserDTO;
import server.repository.query_result.Report_QueryResult;
import server.service.DonHangService;
import server.service.NguoiDungService;
import server.service.NhaCungCapService;
import server.service.ReportService;
import server.service.SanPhamService;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
public class AdminApi {
	
	@Autowired
	private NguoiDungService nguoiDungService;
	@Autowired
	private NhaCungCapService nhaCungCapService;	
	@Autowired
	private SanPhamService sanPhamService;	
	@Autowired
	private DonHangService donHangService;
	@Autowired
	private ReportService reportService;
	
	// ========> Sản phẩm
	@PostMapping("/product/add")
	public Object addProduct(@RequestBody SanPhamPostDTO postDto) {
		try {
			sanPhamService.addProduct(postDto);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/product/edit")
	public Object editProduct(@RequestBody SanPhamPostDTO postDto) {
		try {
			sanPhamService.editProduct(postDto);	
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/product/image")
	public Object uploadImage(@RequestPart("image") MultipartFile image) {
		try {
			return new ResponseEntity<String>(sanPhamService.uploadImage(image), HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/product/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Object deleteproduct(@PathVariable("id") int id) {
		try {
			sanPhamService.deleteProduct(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}
	
	// ========> Người dùng
	@GetMapping("/user")
	public Object getListUser() {
		try {
			return new ResponseEntity<List<UserDTO>>(nguoiDungService.getListUser(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/user/{id}")
	public Object getUserById(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<UserDTO>(nguoiDungService.getUserById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/user")
	public Object addUser(@RequestBody UserDTO capDTO) {
		try {
			capDTO.setVerificationCode(null);
			nguoiDungService.addUser(capDTO);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/user/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Object deleteUser(@PathVariable("id") int id) {
		try {
			nguoiDungService.deleteUser(id);
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/user/{id}")
	public Object updateUser(@PathVariable("id") int id, @RequestBody UserDTO newS) {
		try {
			nguoiDungService.updateUser(id, newS);
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}
	
	// ========> Đơn hàng
	@GetMapping("/order/top10")
	public Object get10NewestOrder() {
		try {
			return new ResponseEntity<List<DonHangDTO>>(donHangService.getTop10NewestOrder(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/order")
	public Object getAllOrder() {
		try {
			return new ResponseEntity<List<DonHangDTO>>(donHangService.getAllOrder(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/order/changeStatus/{id}/{status}")
	public Object changeOrderStatus(@PathVariable("id") int donhang_id, @PathVariable("status") String status) {
		try {
			donHangService.changeOrderStatus(donhang_id, status);
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	// ========> Thống kê
	@GetMapping("/report")
	public Object getReport() {
		try {
			return new ResponseEntity<ReportDTO>(reportService.createReport(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/report/bestseller")
	public Object getBestSeller() {
		try {
			return new ResponseEntity<List<Report_QueryResult>>(reportService.getBestSeller(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/report/s/{status}")
	public Object getOrderReportByStatus(@PathVariable("status") String status) {
		try {
			return new ResponseEntity<List<DonHangDTO>>(donHangService.getAllOrderByStatus(status), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	// ========> Nhà cung cấp
	@GetMapping("/supplier")
	public Object getListSupplier() {
		try {
			return new ResponseEntity<List<NhaCungCapDTO>>(nhaCungCapService.getAllSupplier(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/supplier/{id}")
	public Object getSupplierById(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<NhaCungCapDTO>(nhaCungCapService.getSupplierById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/supplier")
	public Object addSupplier(@RequestBody NhaCungCapDTO capDTO) {
		try {
			nhaCungCapService.addSupplier(capDTO);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/supplier/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Object deleteSupplier(@PathVariable("id") int id) {
		try {
			nhaCungCapService.deleteSupplier(id);
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/supplier/{id}")
	public Object updateSupplier(@PathVariable("id") int id, @RequestBody NhaCungCapDTO newS) {
		try {
			nhaCungCapService.updateSupplier(id, newS);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}
}
