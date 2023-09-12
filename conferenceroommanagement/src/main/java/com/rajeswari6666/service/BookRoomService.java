package com.rajeswari6666.service;

import java.sql.Time;
import com.rajeswari6666.*;
import org.springframework.stereotype.Service;

import com.rajeswari6666.repository.Repository;

import CustomException.IrretrievableException;
import CustomException.RetrievableException;

@Service
public class BookRoomService{
	
	public String bookRoom(int roomId, String email , Time starTime, Time endTime, String floor) throws IrretrievableException {
		try {
		  return Repository.getInstance().bookRoom(roomId,email,starTime,endTime,floor);
		}
		catch(Exception e) {
			throw new IrretrievableException();
		}
	}

}
