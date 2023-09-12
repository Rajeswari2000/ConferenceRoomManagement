package com.rajeswari6666.controller;

import java.sql.Time;
import java.time.LocalTime;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.rajeswari6666.dto.*;
import com.rajeswari6666.service.BookRoomService;

import CustomException.InvalidInputException;
import CustomException.IrretrievableException;

import com.rajeswari6666.*;
import org.apache.log4j.Logger;
@RestController
public class BookRoom {
	
	@Autowired
	BookRoomService bookRoomService;

	@RequestMapping(value="/bookRooom", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> bookRoom(@RequestParam String roomId, @RequestParam String startTime, @RequestParam String endTime, @RequestParam String floor, HttpSession session) {
		final Logger logger = Logger.getLogger(BookRoom.class);

		logger.info("inside book room  class");
			
		String status="";
		
		HashMap<String, Object> map = new HashMap<>();
		
	try {
		
	 try {	
		 
		
		String email = (String)session.getAttribute(Constant.EMAIL_VARIABLE);
	    String [] start = startTime.split(":");
	    String [] end = endTime.split(":");
	  
	    int startHour = Integer.parseInt(start[0]);
	    
	    int startMinute = Integer.parseInt(start[1]);
	    
	    int endHour  = Integer.parseInt(end[0]);
	    
	    int endMinute = Integer.parseInt(end[1]);
		
		LocalTime localstartTime = LocalTime.of(startHour,startMinute,0);

		Time sqlStartTime = Time.valueOf(localstartTime);
		
		LocalTime localEndTime = LocalTime.of(endHour,endMinute,0);

		Time sqlEndTime = Time.valueOf(localEndTime);
		
		status = bookRoomService.bookRoom(Integer.valueOf(roomId), email, sqlStartTime, sqlEndTime, floor);
		
	}
	 catch (NumberFormatException e) {
			throw new InvalidInputException();
	}
	 catch (IrretrievableException e) {

			 logger.error("sql exception book room");
			
			 e.printStackTrace();
			 ResponseEntity.status(500);
			 map.put(Constant.STATUS, new Status(500,Constant.IRRETRIEVABLE_ERROR,Constant.APPLICATION_ERROR));
			 map.put(Constant.BODY, new String(Constant.ERROR));
			 return ResponseEntity.ok(map);
		}
	 
	}
	catch (InvalidInputException e) {
		
		logger.error("invalid input exception");
		
		 e.printStackTrace();
		 ResponseEntity.status(500);
		 map.put(Constant.STATUS, new Status(403,Constant.INVALID_INPUT_ERROR,""));
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


	 if(status.equals(Constant.SUCCESS)) {
		 ResponseEntity.status(200);
		 map.put(Constant.STATUS, new Status(200,Constant.NO_ERROR,"booking successful"));
		 map.put(Constant.BODY, new String("booking successful"));
		 
		 return ResponseEntity.ok(map);
	 }
	 else if(status.equals(Constant.FAILURE)){
		 ResponseEntity.status(500);
		 map.put(Constant.STATUS,new Status(500,Constant.IRRETRIEVABLE_ERROR,"booking failure due to internal server error"));
		 map.put(Constant.BODY, new String("booking failure"));
		 return ResponseEntity.ok(map);
	 }
	 
	 return null;
	}
	
	
}
