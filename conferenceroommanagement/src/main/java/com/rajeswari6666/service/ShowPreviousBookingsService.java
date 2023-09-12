package com.rajeswari6666.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rajeswari6666.dto.Bookings;
import com.rajeswari6666.repository.Repository;

import CustomException.IrretrievableException;

@Service
public class ShowPreviousBookingsService {
	public List<Bookings> showPreviousBookings(String email) throws IrretrievableException {
		try {
		  return Repository.getInstance().showPreviousBookings(email);
		}
		catch(Exception e) {
			throw new IrretrievableException();
		}
	}
}
