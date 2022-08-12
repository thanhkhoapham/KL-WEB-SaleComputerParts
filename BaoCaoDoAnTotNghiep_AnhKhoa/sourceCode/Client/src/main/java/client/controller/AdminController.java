package client.controller;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;

import client.dto.CpuDTO;
import client.dto.DonHangDTO;
import client.dto.MainboardDTO;
import client.dto.NguonMayTinhDTO;
import client.dto.NhaCungCapDTO;
import client.dto.OCungDTO;
import client.dto.OrderReportDTO;
import client.dto.RamDTO;
import client.dto.ReportDTO;
import client.dto.Report_QueryResult;
import client.dto.SanPhamDTO;
import client.dto.SanPhamPostDTO;
import client.dto.UserDTO;
import client.dto.UserLoginDTO;
import client.dto.VgaDTO;
import client.enums.ProductType;
import client.form.SanPhamForm;
import client.service.DonHangService;
import client.service.NguoiDungService;
import client.service.NhaCungCapService;
import client.service.ReportService;
import client.service.SanPhamService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private NguoiDungService nguoiDungService;	
	@Autowired
	private SanPhamService sanPhamService;	
	@Autowired
	private NhaCungCapService nhaCungCapService;
	@Autowired
	private DonHangService donHangService;	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ReportService reportService;
	@Autowired
	HttpSession session;
	
	@GetMapping("")
	public String loadAdminPage(ModelMap map, HttpServletRequest request) {
		try {
			UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");
			System.err.println(user.getRole());
			List<DonHangDTO> top10Orders = donHangService.getTop10(user.getToken());
			ReportDTO reportDTO = reportService.createReport(user.getToken());
			List<Report_QueryResult> bestseller = reportService.getBestSeller(user.getToken());
			map.addAttribute("top10Orders", top10Orders);
			map.addAttribute("reportDTO", reportDTO);
			map.addAttribute("bestseller", bestseller);
			return "admin/admin-page";
		} catch (NullPointerException e) {
			return "redirect:/auth/login";
		} 
		
	}
	
	/*
	 * Quản lý sản phẩm
	 */
	@GetMapping("/product")
	public String showProductPage(ModelMap modelMap, HttpServletRequest request) {
		try {
			List<SanPhamDTO> products = sanPhamService.getAllProduct();
			modelMap.addAttribute("products", products);
			return "admin/admin-product";
		} catch (Exception e) {
			return "redirect:/auth/login";
		} 
	}
	
	@GetMapping("/product/add/{productType}")
	public String showAddProductPage(ModelMap modelMap, @PathVariable("productType") String loaisp) {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");	
		
		Object dto = null;
		switch (loaisp) {
			case "cpu":
				dto = new CpuDTO();
				break;
			case "mainboard":
				dto = new MainboardDTO();
				break;
			case "ram":
				dto = new RamDTO();
				break;
			case "ocung":
				dto = new OCungDTO();
				break;
			case "vga":
				dto = new VgaDTO();
				break;
			case "nguonmaytinh":
				dto = new NguonMayTinhDTO();
				break;
			default:
				break;
		}
		((SanPhamDTO) dto).setId(0);
		((SanPhamDTO) dto).setLoaiSanPham(ProductType.valueOf(loaisp));
		List<NhaCungCapDTO> dsNCC = nhaCungCapService.getAllSupplier(user.getToken());
		
		modelMap.addAttribute("product", dto);
		modelMap.addAttribute("dsNCC", dsNCC);
		return "admin/admin-product-form";
	}
	
	@PostMapping("/product/add")
	public String addProduct(@ModelAttribute("productForm") SanPhamForm productForm) throws IOException {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");
		SanPhamPostDTO postDto = modelMapper.map(productForm, SanPhamPostDTO.class);
		
		if (!productForm.getHinhAnh().isEmpty()) {
			postDto.setUrlHinhAnh(sanPhamService.uploadImage(productForm.getHinhAnh(), user.getToken()));
		}else {
			//nếu không có ảnh thì set đường dẫn ảnh mặc định
			postDto.setUrlHinhAnh("images/product_default.png");
		}
		
		sanPhamService.addProduct(postDto, user.getToken());
			
		return "redirect:/admin/product";
	}
	
	@GetMapping("/product/edit/{productType}/{sanpham_id}")
	public String showUpdateProductPage(ModelMap modelMap, @PathVariable("productType") String loaisp, @PathVariable("sanpham_id") int sanpham_id) {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");	
		Object sanPham = sanPhamService.getProductDetail(sanpham_id);
		Object dto = null;
		switch (loaisp) {
			case "cpu":
				dto = modelMapper.map(sanPham, CpuDTO.class);
				break;
			case "mainboard":
				dto = modelMapper.map(sanPham, MainboardDTO.class);
				break;
			case "ram":
				dto = modelMapper.map(sanPham, RamDTO.class);
				break;
			case "ocung":
				dto = modelMapper.map(sanPham, OCungDTO.class);
				break;
			case "vga":
				dto = modelMapper.map(sanPham, VgaDTO.class);
				break;
			case "nguonmaytinh":
				dto = modelMapper.map(sanPham, NguonMayTinhDTO.class);
				break;
			default:
				break;
		}
		
		List<NhaCungCapDTO> dsNCC = nhaCungCapService.getAllSupplier(user.getToken());
		
		modelMap.addAttribute("product", dto);
		modelMap.addAttribute("dsNCC", dsNCC);
		return "admin/admin-product-form";
	}
	
	@PostMapping("/product/edit")
	public String editProduct(@ModelAttribute("productForm") SanPhamForm productForm) throws IOException {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");	
		SanPhamPostDTO postDto = modelMapper.map(productForm, SanPhamPostDTO.class);
		if (!productForm.getHinhAnh().isEmpty()) {
			postDto.setUrlHinhAnh(sanPhamService.uploadImage(productForm.getHinhAnh(), user.getToken()));
		}else {
			postDto.setUrlHinhAnh("");
		}
			
		sanPhamService.editProduct(postDto, user.getToken());
			
		return "redirect:/admin/product";
	}
	
	@GetMapping("/product/delete/{id}")
	public String deleteProduct(@PathVariable("id") int id) {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");
		sanPhamService.deleteProduct(id, user.getToken());
		return "redirect:/admin/product";
	}
	
	/*
	 * Quản lý nhà cung cấp
	 */
	@GetMapping("/supplier")
	public String loadAdminSupplierPage(ModelMap map) {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");	
		map.addAttribute("suppliers", nhaCungCapService.getAllSupplier(user.getToken()));
		return "admin/admin-supplier";
	}

	@GetMapping("/supplier/add")
	public String showFormAddSupplier(ModelMap map) {
		NhaCungCapDTO dto = new NhaCungCapDTO();
		dto.setId(0);
		map.addAttribute("supplier", dto);
		return "admin/admin-supplier-form";
	}

	@PostMapping("/supplier/add-supplier")
	public String addSupplier(ModelMap map, @ModelAttribute("supplier") NhaCungCapDTO dto) throws JsonProcessingException {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");
		nhaCungCapService.addSupplier(dto, user.getToken());
		return "redirect:/admin/supplier";
	}

	@GetMapping("/supplier/delete/{id}")
	public String deleteSupplier(@PathVariable("id") int id) {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");
		nhaCungCapService.deleteSupplier(id, user.getToken());
		return "redirect:/admin/supplier";
	}

	@GetMapping("/supplier/update/{id}")
	public String showFormUpdateSupplier(@PathVariable("id") int id, ModelMap map) {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");
		map.addAttribute("supplier", nhaCungCapService.getSupplierById(id, user.getToken()));
		return "admin/admin-supplier-form";
	}

	@PostMapping("/supplier/update/update-supplier")
	public String updateSupplier(@ModelAttribute("supplier") NhaCungCapDTO capDTO) throws JsonProcessingException {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");
		nhaCungCapService.updateSupplier(capDTO.getId(), capDTO, user.getToken());
		return "redirect:/admin/supplier";
	}
	
	/*
	 * Quản lý đơn hàng
	 */
	@GetMapping("/orders")
	public String loadAdminOrderPage(ModelMap map) {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");
		map.addAttribute("orders", donHangService.getAllOrder(user.getToken()));
		return "admin/admin-order";
	}
	
	@GetMapping("/order")
	public String showOrderDetailPage(ModelMap modelMap, @RequestParam("id") int donhang_id) {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");
		DonHangDTO order = donHangService.getByOrderId(donhang_id, user.getToken());
		modelMap.addAttribute("order", order);
		return "admin/admin-order-detail";
	}
	
	@GetMapping("/order/changeStatus")
	public String changeOrderStatus(ModelMap modelMap, @RequestParam("id") int donhang_id, @RequestParam("stt") String status) {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");
		donHangService.changeOrderStatus(donhang_id, status, user.getToken());
		return "redirect:/admin/orders";
	}
	
	/*
	 * Quản lý người dùng
	 */
	@GetMapping("/user")
	public String loadAdminClientPage(ModelMap map) {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");
		map.addAttribute("users", nguoiDungService.getListUser(user.getToken()));
		return "admin/admin-client";
	}

	@GetMapping("/user/add")
	public String showFormAddUser(ModelMap map) {
		UserDTO dto = new UserDTO();
		dto.setId(0);
		map.addAttribute("user", dto);
		return "admin/admin-client-form";
	}

	@PostMapping("/user/add-user")
	public String addUser(ModelMap map, @ModelAttribute("user") UserDTO dto) throws JsonProcessingException {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");
		nguoiDungService.addUser(dto, user.getToken());
		return "redirect:/admin/user";
	}

	@GetMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable("id") int id) {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");
		nguoiDungService.deleteUser(id, user.getToken());
		return "redirect:/admin/user";
	}

	@GetMapping("/user/update/{id}")
	public String showFormUpdateUser(@PathVariable("id") int id, ModelMap map) {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");
		map.addAttribute("user", nguoiDungService.getUserById(id, user.getToken()));
		return "admin/admin-client-form";
	}

	@PostMapping("/user/update/update-user")
	public String updateUser(@ModelAttribute("user") UserDTO capDTO) throws JsonProcessingException {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");
		nguoiDungService.updateUserAdmin(capDTO.getId(), capDTO, user.getToken());
		return "redirect:/admin/user";
	}
	
	/*
	 * Thống kê
	 */
	@GetMapping("/report")
	public String showReportPage(ModelMap map, @RequestParam("stt") String status) {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");
		List<DonHangDTO> orders = donHangService.getAllOrderByStatus(status, user.getToken());
		map.addAttribute("orders", orders);
		return "admin/admin-report";
	}
	
}
