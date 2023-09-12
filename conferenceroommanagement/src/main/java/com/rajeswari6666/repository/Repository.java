package com.rajeswari6666.repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.jsp.tagext.TryCatchFinally;

import org.apache.log4j.Logger;

import com.rajeswari6666.dto.Bookings;
import com.rajeswari6666.dto.Room;

import CustomException.IrretrievableException;
import CustomException.RetrievableException;

import java.sql.PreparedStatement;
import com.rajeswari6666.*;
import com.rajeswari6666.controller.BookRoom;
public class Repository {

	/*******************************
	 * Repository set up
	 ******************************/

	private Repository() {

	}

	public static Connection connection;
	private static Repository repository;
	
	
	

	public static Repository getInstance() {
		
		try {
			if (repository == null) {
				repository = new Repository();
				
				String url="";
				String userName = "";
				String password  = "";
				
				try {
				
					Properties properties = new Properties();
					
				FileInputStream fileInputStream = new FileInputStream("/Users/rajes-19097/Documents/ZOHO/eclipse/practise/conferenceroommanagement/src/main/resources/conference.properties");
					   
					
				   // FileInputStream fileInputStream = new FileInputStream("../webapps/conferenceroommanagement-0.0.1-SNAPSHOT/WEB-INF/classes/conference.properties");
				   
				    try {
						properties.load(fileInputStream);
						System.out.println("inside property load");
						url = properties.getProperty("url");
						userName = properties.getProperty("userName");
						password = properties.getProperty("password");
					} 
				    catch (IOException e) {
						e.printStackTrace();
					}
				    
				}
				catch(FileNotFoundException e){
					e.printStackTrace();
				}
			
				try {
					Class.forName("org.postgresql.Driver");
				} catch (ClassNotFoundException e) {
					System.out.println("inside driver loading catch");
					e.printStackTrace();
				}
				
				connection = DriverManager.getConnection(url, userName, password);
			}
		} catch (SQLException e) {
			System.out.println("inside connection catch");
			e.printStackTrace();
		}
		return repository;
	}

	public boolean verifyEmployee(String email) throws RetrievableException {
		
		boolean flag = false;

		try {
			String query = "SELECT * FROM employee WHERE email=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				flag = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL exception inside verifyEmployee");
			throw new RetrievableException();
		}
		
		return flag;

	}

	public List<Room> fetchListOfAllRooms(Time startTime, Time endTime, String email) throws RetrievableException {
		
		final Logger logger = Logger.getLogger(BookRoom.class);
		logger.info("inside fetchListOfAllRooms class db");
		
		List<Room> availableRooms = new ArrayList<>();
       
		String floor = "";

		
		try {
			String findFloorString = "SELECT floor from employee where email=?";
			PreparedStatement preparedStatement = connection.prepareStatement(findFloorString);
			preparedStatement.setString(1, email);
			
			ResultSet floorResult = preparedStatement.executeQuery();
			
			if (floorResult.next()) {
				floor = floorResult.getString(Constant.FLOOR);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info(e);
			System.out.println("exception inside fetchListOfAllRooms in finding floor");
			
			throw new RetrievableException();
		}
		
		String query = "SELECT * FROM room WHERE floor = ? AND roomId NOT IN "
				+ "(SELECT roomId FROM bookings WHERE floor = ? AND "
				+ "(? BETWEEN startTime AND endTime OR ? BETWEEN startTime AND endTime OR "
				+ "startTime BETWEEN ? AND ?))";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, floor);
			preparedStatement.setString(2, floor);
			preparedStatement.setTime(3, startTime);
			preparedStatement.setTime(4, endTime);
			preparedStatement.setTime(5, startTime);
			preparedStatement.setTime(6, endTime);

			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				int roomId = resultSet.getInt(Constant.ROOM_ID);
				String roomFloor = resultSet.getString(Constant.FLOOR);
				int capacity = resultSet.getInt(Constant.CAPACITY);
				Room room = new Room(roomId, roomFloor, capacity);
				availableRooms.add(room);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			
			System.out.println("SQL exception inside fetchListOfAllRooms");
			throw new RetrievableException();
		}

		
		return availableRooms;
	}
	

	public String bookRoom(int roomId, String email, Time startTime, Time endTime, String floor) throws IrretrievableException {
		
		 try {
	            String insertQuery = "INSERT INTO bookings (roomId, email, startTime, endTime, floor) VALUES (?, ?, ?, ?, ?)";
	            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

	            preparedStatement.setInt(1, roomId);
	            preparedStatement.setString(2, email);
	            preparedStatement.setTime(3, startTime);
	            preparedStatement.setTime(4, endTime);
	            preparedStatement.setString(5, floor);

	            int rowsInserted = preparedStatement.executeUpdate();

	            if (rowsInserted > 0) {
	            	return Constant.SUCCESS;
	            } 
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("sql exception inside bookRoom");
	            throw new IrretrievableException();
	        }
		
		return Constant.FAILURE;
	}

	public String cancelRoom(int bookingId) throws IrretrievableException {
		
		 String deleteQuery = "DELETE FROM bookings WHERE bookingId = ?";

	        try { 
	             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
	            
	            preparedStatement.setInt(1, bookingId);

	            int rowsAffected = preparedStatement.executeUpdate();

	            if (rowsAffected > 0) {
	                return Constant.SUCCESS;
	            } else {
	                return Constant.FAILURE;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("Error while deleting booking with bookingId " + bookingId);
	            throw new IrretrievableException();
	        }
	}

	public List<Bookings> showPreviousBookings(String email) throws IrretrievableException {
		
		List<Bookings> listOfBookings = new ArrayList<Bookings>();
		
		String selectQuery = "SELECT * FROM bookings WHERE email = ?";

        try {
        	PreparedStatement preparedStatement = connection.prepareStatement(selectQuery) ;
      
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int bookingId = resultSet.getInt(Constant.BOOKING_ID);
                int roomId = resultSet.getInt(Constant.ROOM_ID);
                Time startTime = resultSet.getTime(Constant.START_TIME);
                Time endTime = resultSet.getTime(Constant.END_TIME);
                String floor = resultSet.getString(Constant.FLOOR);

                listOfBookings.add(new Bookings(bookingId, roomId, email, startTime, endTime, floor));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while fetching previous bookings " + email);
            throw new IrretrievableException();
        }

		return listOfBookings;
	}
	
	
}
