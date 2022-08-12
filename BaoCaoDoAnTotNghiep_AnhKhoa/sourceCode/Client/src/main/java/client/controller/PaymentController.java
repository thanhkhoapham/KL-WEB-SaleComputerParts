package client.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.base.rest.PayPalRESTException;

import client.dto.GioHangDTO;
import client.dto.PaymentDTO;
import client.dto.PaymentInfoDTO;
import client.dto.UserLoginDTO;
import client.enums.PaypalPaymentIntent;
import client.enums.PaypalPaymentMethod;
import client.service.DonHangService;
import client.service.PaypalService;
import client.utils.GioHangUtils;
import client.utils.PaypalUtils;

@Controller
public class PaymentController {
	
	public static final String URL_PAYPAL_SUCCESS = "/pay/success";
	public static final String URL_PAYPAL_CANCEL = "/pay/cancel";
	
	@Autowired
	private PaypalService paypalService;	
	@Autowired
	private DonHangService donHangService;
	@Autowired
	HttpSession session;
	
	@PostMapping("/createPayment")
	public String createPayment(HttpServletRequest request,@RequestParam("price") double price ){
		//localhost:8080/pay/cancel
		String cancelUrl = PaypalUtils.getBaseURL(request) + URL_PAYPAL_CANCEL;
		//localhost:8080/pay/success
		String successUrl = PaypalUtils.getBaseURL(request) + URL_PAYPAL_SUCCESS;
		
		try {
			PaymentDTO paymentDTO = new PaymentDTO();
			paymentDTO.setTotal(price);
			paymentDTO.setCurrency("USD");
			paymentDTO.setMethod(PaypalPaymentMethod.paypal);
			paymentDTO.setIntent(PaypalPaymentIntent.sale);
			paymentDTO.setDescription("Thanh toán hóa đơn!");
			paymentDTO.setCancelUrl(cancelUrl);
			paymentDTO.setSuccessUrl(successUrl);
			
			//paymentUrl = redirect:{link trang thanh toán};
			String paymentUrl = paypalService.getPaymentUrl(paymentDTO);
//			System.err.println(paymentUrl);
			return paymentUrl;
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
	@GetMapping(URL_PAYPAL_CANCEL)
	public String cancelPay(){
		return "redirect:/checkout?error=true";
	}
	
	//Thanh toán thành công thì lưu đơn hàng vào db
	@GetMapping(URL_PAYPAL_SUCCESS)
	public String successPay(HttpServletRequest request, ModelMap modelMap, @RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
		try {
			String paymentState = paypalService.getExecuteState(paymentId, payerId);
			if (paymentState.equals("approved")) {
				//Lưu đơn hàng vào db
				GioHangDTO gioHang = GioHangUtils.layGioHang(request);
				gioHang.getDonHangForm().setPaid(true);
				
				//Lưu thông tin thanh toán
				PaymentInfoDTO paymentInfoDTO = new PaymentInfoDTO();
				paymentInfoDTO.setPayerId(payerId);
				paymentInfoDTO.setPaymentId(paymentId);
				gioHang.setPaymentInfoDTO(paymentInfoDTO);
				
				UserLoginDTO user = (UserLoginDTO) request.getSession().getAttribute("user");
				donHangService.addOrder(gioHang, user.getToken());
				
				//Xóa giỏ hàng khi đặt thành công
				GioHangUtils.xoaGioHang(request);
				
				//Chuyển sang trang thông báo thành công
				return "client/success-page";
			}
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		
		return "client/success-page";
		
	}
	
}
