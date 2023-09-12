package com.rajeswari6666.controller;

import java.sql.Time;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rajeswari6666.service.ShowAvailableRoomsService;

import CustomException.InvalidInputException;
import CustomException.RetrievableException;

import com.rajeswari6666.Constant;
import com.rajeswari6666.dto.*;

@RestController
public class ShowAvailableRooms {

	@Autowired
	ShowAvailableRoomsService service;

	@RequestMapping(value = "/showAvailableRooms", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> fetchAvailableRooms(HttpServletRequest request, @RequestParam String startTime,
			@RequestParam String endTime, HttpSession session) {
		final Logger logger = Logger.getLogger(BookRoom.class);

		List<Room> listOfRooms = null;

		HashMap<String, Object> map = new HashMap<>();
	
		logger.info("inside fetchAvailableRooms class");
		

		try {
			try {
				String email = (String)session.getAttribute(Constant.EMAIL_VARIABLE);

				String[] start = startTime.split(":");
				String[] end = endTime.split(":");

				int startHour = Integer.parseInt(start[0]);

				int startMinute = Integer.parseInt(start[1]);

				int endHour = Integer.parseInt(end[0]);

				int endMinute = Integer.parseInt(end[1]);

				LocalTime localstartTime = LocalTime.of(startHour, startMinute, 0);

				//sqlStartTime
				Time sqlStartTime = Time.valueOf(localstartTime);
				
				//System.out.print(Constant.START_TIME);
				LocalTime localEndTime = LocalTime.of(endHour, endMinute, 0);

				//sqlEndTime
				Time sqlEndTime = Time.valueOf(localEndTime);

				listOfRooms = service.fetchAvailableRooms(sqlStartTime, sqlEndTime, email);

				if (listOfRooms.size() == 0) {
					ResponseEntity.status(200);
					map.put(Constant.STATUS, new Status(200,Constant.NO_ERROR,"noRoomsAvailable"));
					map.put(Constant.BODY, listOfRooms);
					return ResponseEntity.ok(map);
				}
				
			} 
			catch (NumberFormatException e) {
				throw new InvalidInputException();
			} 
		    catch (RetrievableException e) {
			 e.printStackTrace();
			 logger.error("sql exception book room");
				
			 ResponseEntity.status(500);
			 map.put(Constant.STATUS, new Status(500,Constant.RETRIEVABLE_ERROR, Constant.APPLICATION_ERROR));
			 map.put(Constant.BODY, new String(Constant.ERROR));
			 return ResponseEntity.ok(map);
		}
			
		} catch (InvalidInputException e) {
			e.printStackTrace();
			logger.error("invalid input exception");
			
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

		ResponseEntity.status(200);
		map.put(Constant.STATUS, new Status(200,Constant.NO_ERROR,"roomsAvailable"));
		map.put(Constant.BODY, listOfRooms);
		return ResponseEntity.ok(map);
	}

}
