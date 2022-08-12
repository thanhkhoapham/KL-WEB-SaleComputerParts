package client.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import client.dto.DonHangDTO;
import client.dto.GioHangDTO;
import client.dto.SanPhamDTO;
import client.dto.SanPhamPagingDTO;
import client.dto.UserDTO;
import client.dto.UserInfoDTO;
import client.dto.UserLoginDTO;
import client.enums.ProductType;
import client.form.DonHangForm;
import client.service.DonHangService;
import client.service.NguoiDungService;
import client.service.SanPhamService;
import client.utils.GioHangUtils;

@Controller
@RequestMapping("/")
public class HomeController {

	private final double oneDollar = 22870;
	
	@Autowired
	private SanPhamService sanPhamService;
	@Autowired
	private DonHangService donHangService;
	@Autowired
	private NguoiDungService nguoiDungService;
	@Autowired
	HttpSession session;
	
	@GetMapping("")
	public String showHomePage(ModelMap modelMap, @RequestParam("page") Optional<Integer> page) {
		int evalPage = (page.orElse(0) < 1) ? 1 : page.get();
		SanPhamPagingDTO products = sanPhamService.getAllProductByPage(evalPage);
		int totalPage = products.getTotalPages();
		modelMap.addAttribute("products", products);
		modelMap.addAttribute("currentPage", evalPage);
		modelMap.addAttribute("totalPage", totalPage);
		if (totalPage > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage)
                .boxed()
                .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
		return "client/home-page";
	}
	
	@GetMapping("/info")
	public String showInfoPage() {
		return "client/info-page";
	}
	
	@GetMapping("/category/{category}")
	public String showProductPageByType(ModelMap modelMap,
									@PathVariable("category") String loaiSanPham,
									@RequestParam("type") Optional<String> type,
									@RequestParam("page") Optional<Integer> page,
									@RequestParam("sort") Optional<String> sort) {
		int evalPage = (page.orElse(0) < 1) ? 1 : page.get();
		String t = (type.orElse("").equals("")) ? null : type.get();
		String s = (sort.orElse("").equals("")) ? null : sort.get();
		SanPhamPagingDTO products = sanPhamService.getProductsByType(loaiSanPham, evalPage, t, s);
		
		int totalPage = products.getTotalPages();
		modelMap.addAttribute("products", products);
		modelMap.addAttribute("currentPage", evalPage);
		modelMap.addAttribute("totalPage", totalPage);
		if (totalPage > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage)
                .boxed()
                .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
		
		ProductType productType = ProductType.valueOf(loaiSanPham);
		modelMap.addAttribute("productType", productType);
		return "client/product-list-page";
	}
	
	@GetMapping("/search")
	public String searchProductPage(ModelMap modelMap, @RequestParam("s") String keyword, @RequestParam("page") Optional<Integer> page) {
		if (keyword.trim().equals("")) {
			return "redirect:/";
		}
		int evalPage = (page.orElse(0) < 1) ? 1 : page.get();
		SanPhamPagingDTO products = sanPhamService.searchProductByName(keyword, evalPage);
		
		int totalPage = products.getTotalPages();
		modelMap.addAttribute("products", products);
		modelMap.addAttribute("currentPage", evalPage);
		modelMap.addAttribute("totalPage", totalPage);
		if (totalPage > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage)
                .boxed()
                .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
		modelMap.addAttribute("searchKey", keyword);
		return "client/product-search-page";
	}
	
	@GetMapping("/product/{sanPham_id}")
	public String showProductPage(ModelMap modelMap, @PathVariable("sanPham_id") int sanpham_id) {
		Object sanPham = sanPhamService.getProductDetail(sanpham_id);
//		System.err.println(sanPham);
		modelMap.addAttribute("product", sanPham);
		return "client/product-page";
	}
	
	/**
	 * Giỏ hàng
	 **/
	@GetMapping("/cart")
	public String showCartPage(HttpServletRequest request, ModelMap modelMap) {
		GioHangDTO gioHang = GioHangUtils.layGioHang(request);
		double tongTien = gioHang.tinhTongThanhTien();
		modelMap.addAttribute("gioHang", gioHang);
		modelMap.addAttribute("tongTien", tongTien);
		return "client/cart-page";
	}
	
	@GetMapping("/addToCart")
	public String addToCart(HttpServletRequest request, ModelMap modelMap, @RequestParam("id") int sanpham_id) {
		SanPhamDTO sanPham = sanPhamService.getProductById(sanpham_id);
		
		GioHangDTO gioHang = GioHangUtils.layGioHang(request);
		gioHang.themSanPham(sanPham, 1);
		return "redirect:/cart";
	}
	
	@GetMapping("/deleteFromCart")
	public String deleteFromCart(HttpServletRequest request, ModelMap modelMap, @RequestParam("id") int sanpham_id) {
		SanPhamDTO sanPham = sanPhamService.getProductById(sanpham_id);
		GioHangDTO gioHang = GioHangUtils.layGioHang(request);
		gioHang.xoaSanPham(sanPham);
		return "redirect:/cart";
	}
	
	@PostMapping("/updateCart")
	public String updateCart(HttpServletRequest request, ModelMap modelMap,
			@ModelAttribute("gioHang") GioHangDTO gioHangNew) {
		GioHangDTO gioHang = GioHangUtils.layGioHang(request);
		gioHang.capNhatSoLuong(gioHangNew);
		return "redirect:/cart";
	}
	
	@GetMapping("/checkout")
	public String showCheckoutPage(HttpServletRequest request, ModelMap modelMap, RedirectAttributes attributes) {
		GioHangDTO gioHang = GioHangUtils.layGioHang(request);
		String thBao = "";
		
		//Kiểm tra user đã đăng nhập hay chưa, nếu chưa thì redirect sang trang login
		UserLoginDTO user = (UserLoginDTO) request.getSession().getAttribute("user");
		if (user == null) {
			thBao = "Vui lòng đăng nhập để tiếp tục!!";
			attributes.addFlashAttribute("thBao", thBao);
			return "redirect:/auth/login";
		}
		//Nếu trong giỏ chưa có sản phẩm thì redirect ra trang chủ
		else if(gioHang.getDsCT().size() <= 0) {
			return "redirect:/";
		}
		else {
			UserInfoDTO nguoiDung = nguoiDungService.getUserInfo(user.getUserId(), user.getToken());
			double tongTien = gioHang.tinhTongThanhTien();
			
			//Chuyển thành tiền từ VND sang USD
			double formate = tongTien/oneDollar;
			double tongTien_dollar = Math.round(formate * 100) / 100.0;
			
			DonHangForm dhf = new DonHangForm();
			dhf.setPaid(false);
			
			modelMap.addAttribute("gioHang", gioHang);
			modelMap.addAttribute("nguoiDung", nguoiDung);
			modelMap.addAttribute("tongTien", tongTien);
			modelMap.addAttribute("tongTien_dollar", tongTien_dollar);
			modelMap.addAttribute("donHangForm", dhf);
		}
		return "client/checkout-page";
	}
	
	@PostMapping("/checkout")
	public String confirmOrder(HttpServletRequest request, 
							@ModelAttribute("donHangForm") DonHangForm donHangForm,
							ModelMap modelMap) {
		
		GioHangDTO gioHang = GioHangUtils.layGioHang(request);
		gioHang.setDonHangForm(donHangForm);
		
		double tongTien = gioHang.tinhTongThanhTien();
		
		//Chuyển thành tiền từ VND sang USD
		double formate = tongTien/oneDollar;
		double tongTien_dollar = Math.round(formate * 100) / 100.0;
		
		modelMap.addAttribute("tongTien_dollar", tongTien_dollar);
		modelMap.addAttribute("gioHang", gioHang);
		return "client/checkout-confirm-page";
	}
	
	@PostMapping("/order")
	public String sendOrder(HttpServletRequest request) {
		GioHangDTO gioHang = GioHangUtils.layGioHang(request);
		UserLoginDTO user = (UserLoginDTO) request.getSession().getAttribute("user");
		donHangService.addOrder(gioHang, user.getToken());
		
		//Xóa giỏ hàng khi đặt thành công
		GioHangUtils.xoaGioHang(request);
		
		//redirect ra trang đặt hàng thành công
		return "client/success-page";
	}
	
	@GetMapping("/orderList")
	public String showOrderList(ModelMap modelMap, @RequestParam("uid") int nguoidung_id, RedirectAttributes attributes) {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");	
		if (user == null) {
			String thBao = "Vui lòng đăng nhập để tiếp tục!!";
			attributes.addFlashAttribute("thBao", thBao);
			return "redirect:/auth/login";
		}
		List<DonHangDTO> orderList = donHangService.getListByUserId(nguoidung_id, user.getToken());
		modelMap.addAttribute("orderList", orderList);
		return "client/order-list-page";
	}
	
	@GetMapping("/order")
	public String showOrderDetailPage(ModelMap modelMap, @RequestParam("id") int donhang_id, RedirectAttributes attributes) {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");	
		if (user == null) {
			String thBao = "Vui lòng đăng nhập để tiếp tục!!";
			attributes.addFlashAttribute("thBao", thBao);
			return "redirect:/auth/login";
		}
		DonHangDTO order = donHangService.getByOrderId(donhang_id, user.getToken());
		modelMap.addAttribute("order", order);
		return "client/order-detail-page";
	}
	
	/**
	 * Thông tin người dùng
	 **/
	@GetMapping("/userInfo")
	public String showUserInfoPage(HttpServletRequest request, ModelMap modelMap, @RequestParam("id") int nguoidung_id, RedirectAttributes attributes) {
		UserLoginDTO user = (UserLoginDTO) session.getAttribute("user");	
		if (user == null) {
			String thBao = "Vui lòng đăng nhập để tiếp tục!!";
			attributes.addFlashAttribute("thBao", thBao);
			return "redirect:/auth/login";
		}
		UserInfoDTO nguoiDung = nguoiDungService.getUserInfo(nguoidung_id, user.getToken());
		modelMap.addAttribute("user", nguoiDung);
		return "client/user-info-page";
	}
	
	@PostMapping("/editUser")
	public String updateUser(@ModelAttribute("user") UserDTO userDTO) {
		nguoiDungService.updateUser(userDTO.getId(), userDTO);
		return "redirect:/";
	}
}
