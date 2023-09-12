package com.rajeswari6666.service;

import java.sql.Time;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rajeswari6666.dto.Room;
import com.rajeswari6666.repository.Repository;

import CustomException.RetrievableException;

import com.rajeswari6666.*;

@Service
public class ShowAvailableRoomsService {

	public List<Room> fetchAvailableRooms(Time starTime, Time endTime, String email) throws RetrievableException {
		try {
			return Repository.getInstance().fetchListOfAllRooms(starTime, endTime, email);
		} catch (Exception e) {
			throw new RetrievableException();
		}
	}

}
