<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">




<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<script src="https://kit.fontawesome.com/052a65ebc8.js"
	crossorigin="anonymous"></script>


<title>Cancel booking</title>

</head>
<body>
   
   <div id="cancelView">

		<div id=listOfBookings>

			<h3 style="margin-top: 70px; margin-left: 650px;">
				<b>Your bookings</b>
			</h3>
            <br> 
			<table class="table table-striped table-hover"
				style="width: 700px; height: 150px; margin-left: 410px;">
				<thead>
					<tr>
						<th scope="col">Booking id</th>
						<th scope="col">Start time</th>
						<th scope="col">End time</th>
						<th scope="col">Floor</th>
						<th scope="col">cancel</th>
					</tr>
				</thead>
				<tbody id="tableBodyCancel">

				</tbody>
			</table>
		</div>

		<div id="noPreviousBookings"></div>
	</div>

	<template>
		<tr>
			<th id="bookingIdTab" scope="row"></th>
			<td id="startTimeTab"></td>
			<td id="endTimeTab"></td>
			<td id="floorCancelTab"></td>
			<td><button class="btn btn-danger">cancel</button></td>
		</tr>
	</template>
   
   
</body>




<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>


<script>

loadBookings();

function loadBookings(){
	
	let listOfBookings = document.getElementById("listOfBookings");
	listOfBookings.style.display="none";
	let noPreviousBookings = document.getElementById("noPreviousBookings");
	let tableBodyCancel = document.getElementById("tableBodyCancel");
	

	 let xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				let response = JSON.parse(xhr.responseText);
				if(xhr.status == 200){
					listOfBookings.style.display="block";
					tableBodyCancel.innerHTML="";
					
					let body = response.body;
				
					 for (var i = 0; i < body.length; i++) {
							var eachObject = body[i];
							var templateTag = document.getElementsByTagName("template")[0];
							var eachItemDiv = templateTag.content;
							var copiedDiv = eachItemDiv.cloneNode(true);

							copiedDiv.querySelector("#bookingIdTab").innerText = eachObject.bookingId;
							copiedDiv.querySelector("#startTimeTab").innerText = eachObject.startTime;
							copiedDiv.querySelector("#endTimeTab").innerText = eachObject.endTime;
							copiedDiv.querySelector("#floorCancelTab").innerText = eachObject.floor;
						
							copiedDiv.querySelector(".btn-danger").setAttribute('onclick', "cancelBooking(" + eachObject.bookingId + ")");

							
							let tableBodyCancel = document.getElementById('tableBodyCancel');
							tableBodyCancel.append(copiedDiv);
						 } 
					
				}
				else if(xhr.status==403){
					alert("no previous bookings to cancel")
					
				}
				else{
					alert("sorry couldn't cancel due to internal server error");
					
				}
			}
		};
		xhr.open("GET", "http://localhost:8080/showPreviousBookings");
		xhr.send();
	
}

function cancelBooking(bookingId){
	 let xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				let response = JSON.parse(xhr.responseText);
				if(xhr.status == 200){
					alert("cancellation successful");
					window.location.href = "http://localhost:8080/showoptions";			   		
				}
				else alert("sorry couldn't cancel the room due to internal server error");	
			}
		};
		xhr.open("POST", "http://localhost:8080/cancelRoom");
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		xhr.send("bookingId="+bookingId);

}

</script>

</html>