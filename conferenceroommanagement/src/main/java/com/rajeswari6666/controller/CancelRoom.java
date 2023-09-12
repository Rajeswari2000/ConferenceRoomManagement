package com.rajeswari6666.controller;

import java.util.HashMap;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rajeswari6666.Constant;
import com.rajeswari6666.dto.Status;
import com.rajeswari6666.service.CancelRoomService;

import CustomException.IrretrievableException;

@RestController
public class CancelRoom {

	@Autowired
	CancelRoomService cancelRoomService;

	@RequestMapping(value = "/cancelRoom", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> cancelRoom(@RequestParam String bookingId) {

		final Logger logger = Logger.getLogger(CancelRoom.class);

		String status = "";

		HashMap<String, Object> map = new HashMap<>();

		try {
			status = cancelRoomService.cancelRoom(Integer.valueOf(bookingId));

		}
		catch (IrretrievableException e) {
			e.printStackTrace();
			logger.error("log: Irretrievable Exception occurred", e);
			ResponseEntity.status(500);
			map.put(Constant.STATUS, new Status(500,Constant.IRRETRIEVABLE_ERROR, Constant.APPLICATION_ERROR));
			map.put(Constant.BODY, new String(Constant.ERROR));
			return ResponseEntity.ok(map);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("log: Unexpected Exception occurred", e);
			ResponseEntity.status(500);
			map.put(Constant.STATUS, new Status(500, Constant.IRRETRIEVABLE_ERROR,Constant.UNEXPECTED_SERVER_ERROR));
			map.put(Constant.BODY, new String(Constant.ERROR));
			return ResponseEntity.ok(map);
		}

		if (status.equals(Constant.SUCCESS)) {
			ResponseEntity.status(200);
			map.put(Constant.STATUS, new Status(200, Constant.NO_ERROR, "cancellation successful"));
			map.put(Constant.BODY, new String("cancellation successful"));

			return ResponseEntity.ok(map);
			
		} 
		else if (status.equals(Constant.FAILURE)) {
			ResponseEntity.status(500);
			map.put(Constant.STATUS,
					new Status(500, Constant.IRRETRIEVABLE_ERROR, "cancellation failure due to internal server error"));
			map.put(Constant.BODY, new String("cancellation failure"));
			return ResponseEntity.ok(map);
		}

		return null;

	}
}
