package com.rajeswari6666.filter;



import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rajeswari6666.*;




@WebFilter(urlPatterns = { "/conferenceroommanagement-0.0.1-SNAPSHOT/bookRoomView","/conferenceroommanagement-0.0.1-SNAPSHOT/showoptions","/conferenceroommanagement-0.0.1-SNAPSHOT/showAvailableRooms","/conferenceroommanagement-0.0.1-SNAPSHOT/bookRooom"})
public class GeneralAuthFilter implements Filter {
   
	private static final long serialVersionUID = 1L;


	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		 System.out.println("inside filter");
			
//		
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
//        
//		HttpSession session = req.getSession();
//		String email = (String) session.getAttribute(Constant.EMAIL_VARIABLE);
//		
//		
//		if(email==null || email.isEmpty()) {
//			res.setStatus(401);
//			response.getWriter().print("{ status: 401, description : unauthorized user}");
//		}
//		else {
//			chain.doFilter(request, response);
//		}
	   
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
	
	
	}


}
