package com.rajeswari6666.service;

import java.sql.Time;

import org.springframework.stereotype.Service;

import com.rajeswari6666.repository.Repository;

import CustomException.IrretrievableException;

@Service
public class CancelRoomService {

	public String cancelRoom(int bookingId) throws IrretrievableException {
		try {
		  return Repository.getInstance().cancelRoom(bookingId);
		}
		catch(Exception e) {
			throw new IrretrievableException();
		}
	}

	
}
