package com.rajeswari6666.service;

import org.springframework.stereotype.Service;
import com.rajeswari6666.repository.*;

import CustomException.RetrievableException;

import com.rajeswari6666.*;
@Service
public class VerifyEmployeeService {
	
	public boolean verifyEmployee(String email) throws RetrievableException {
		try {
			return Repository.getInstance().verifyEmployee(email);
		}
		catch(Exception e) {
			throw new RetrievableException();
		}
	}
}
