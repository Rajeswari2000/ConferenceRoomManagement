package com.rajeswari6666.controller;

import java.awt.print.Printable;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rajeswari6666.service.*;

import CustomException.RetrievableException;

import com.rajeswari6666.*;
import com.rajeswari6666.dto.Status;
@Controller
public class VerifyEmployee {
	
	@Autowired
	VerifyEmployeeService verifyEmployeeService;
	
	@RequestMapping(value="/verifyEmployee",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> verifyEmployee(@RequestParam String email,HttpServletResponse response, HttpServletRequest request, HttpSession session){
	    
		System.out.println("inside verify employee");
		HashMap<String, Object> map = new HashMap<>();
		
		final Logger logger = Logger.getLogger(BookRoom.class);

		boolean flag=false;
		logger.info(email);
		
		
		try {
			flag = verifyEmployeeService.verifyEmployee(email);
			System.out.println(flag);
		}
		catch (RetrievableException e) {
			 logger.error("sql exception verify employee");
				
			e.printStackTrace();
			ResponseEntity.status(500);
			map.put(Constant.STATUS, new Status(500,Constant.IRRETRIEVABLE_ERROR,Constant.APPLICATION_ERROR));
			map.put(Constant.BODY, new String(Constant.ERROR));
			return ResponseEntity.ok(map);
		}
		catch(Exception e) {
			
		    logger.error("unexpected error");
			
			e.printStackTrace();
			ResponseEntity.status(500);
			map.put(Constant.STATUS, new Status(500,Constant.IRRETRIEVABLE_ERROR,Constant.UNEXPECTED_SERVER_ERROR));
			map.put(Constant.BODY, new String(Constant.ERROR));
			return ResponseEntity.ok(map);
		}
		
		if(!flag) {
			System.out.println("hi unauthorized user");
			ResponseEntity.status(403);
			map.put(Constant.STATUS, new Status(403,Constant.NO_ERROR,"unauthorized user"));
			map.put(Constant.BODY, new String("false"));
			return ResponseEntity.ok(map);
		}
		else {
		
		session.setAttribute("email", email);
		ResponseEntity.status(200);
		map.put(Constant.STATUS, new Status(200,Constant.NO_ERROR,"authorized user"));
		map.put(Constant.BODY, new String("true"));
		return ResponseEntity.ok(map);
		}
	}
	 
}
