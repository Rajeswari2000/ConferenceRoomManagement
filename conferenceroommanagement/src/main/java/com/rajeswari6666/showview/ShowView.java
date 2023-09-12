package com.rajeswari6666.showview;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.rajeswari6666.*;
@Controller
public class ShowView {
	
	@RequestMapping(value="/index",method = RequestMethod.GET)
	public String showView() {
		System.out.println("inside first page");
		return "index";
	}
	
	@RequestMapping(value="/showoptions",method = RequestMethod.GET)
	public String showOption() {
		return "showoptions";
	}
	
	@RequestMapping(value="/bookRoomView",method = RequestMethod.GET)
	public String bookRoomView() {
		return "bookroom";
	}
	@RequestMapping(value="/cancelRoomView",method = RequestMethod.GET)
	public String cancelRoomView() {
		return "cancel";
	}

}
