package client.handler;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

@Controller
public class MyErrorController implements ErrorController{
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	    	
	        if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {	        	
	            return "error/error-page";
	        }
	    }
	    return "error/error-page";
	}
}
