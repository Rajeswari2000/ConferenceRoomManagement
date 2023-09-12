package com.rajeswari6666.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rajeswari6666.Constant;
import com.rajeswari6666.dto.Bookings;
import com.rajeswari6666.dto.Status;
import com.rajeswari6666.service.ShowPreviousBookingsService;

import CustomException.IrretrievableException;

@RestController
public class ShowPreviousBookings {
	

	@Autowired
	ShowPreviousBookingsService showPreviousBookingsService;
	
	@RequestMapping(value="/showPreviousBookings", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> showPreviousBookings(HttpSession session) {
		final Logger logger = Logger.getLogger(BookRoom.class);

		List<Bookings> listOfBookings = new ArrayList<Bookings>();	
	
		String email = (String)session.getAttribute(Constant.EMAIL_VARIABLE);
		HashMap<String, Object> map = new HashMap<>();
		
	 try {		
		listOfBookings = showPreviousBookingsService.showPreviousBookings(email);
	
	}
	 catch (IrretrievableException e) {
		 logger.error("sql exception book room");
			
			e.printStackTrace();
			ResponseEntity.status(500);
			 map.put(Constant.STATUS, new Status(500,Constant.IRRETRIEVABLE_ERROR, Constant.APPLICATION_ERROR));
			 map.put(Constant.BODY, new String(Constant.ERROR));
			 return ResponseEntity.ok(map);
	}
	catch (Exception e) {
		logger.error("unexpected error");
		
		e.printStackTrace();
		ResponseEntity.status(500);
		map.put(Constant.STATUS, new Status(500,Constant.IRRETRIEVABLE_ERROR,Constant.UNEXPECTED_SERVER_ERROR));
		map.put(Constant.BODY, new String(Constant.ERROR));
		return ResponseEntity.ok(map);
	}


	 if(listOfBookings.size()!=0) {
		 ResponseEntity.status(200);
		 map.put(Constant.STATUS, new Status(200,Constant.NO_ERROR,"cancellation successful"));
		 map.put(Constant.BODY, listOfBookings);
		 
		 return ResponseEntity.ok(map);
	 }
	 else {
		 ResponseEntity.status(403);
		 map.put(Constant.STATUS,new Status(403,Constant.RETRIEVABLE_ERROR,"no previous bookings"));
		 map.put(Constant.BODY, new String("no previous bookings"));
		 return ResponseEntity.ok(map);
	 }
		

		
	}
	

}
