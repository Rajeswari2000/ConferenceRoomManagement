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

<title>Room booking</title>
</head>
<body>




	<br>
	<br>

	<div
		style="margin-left: 500px; margin-top: 100px; justify-content: center"
		id="bookRoomForm">
		<h1>
			<b>Choose the start and end time</b>
		</h1>
		<br>
		<div style="margin-left: 200px;"mb-3">
			<label for="startTime">Start time</label> <input style="width: 150px"
				type="time" class="form-control" id="startTime"
				placeholder="start time"> <label for="endTime">End
				time</label> <input style="width: 150px" type="time" class="form-control"
				id="endTime" placeholder="end time">
		</div>
		<br>
		<button style="margin-left: 200px;" onclick="checkAvailableRooms()"
			class="btn btn-primary">check availability</button>
	</div>

	<br>
	<br>
	<div style="display: none;" id="availabilityResponse">

		<div id=listOfAvailableRooms>

			<h3 style="margin-top: 70px; margin-left: 650px;">
				<b>Available rooms</b>
			</h3>
			<br>
			<table class="table table-striped table-hover"
				style="width: 700px; height: 150px; margin-left: 410px;">
				<thead>
					<tr>
						<th scope="col">room id</th>
						<th scope="col">floor</th>
						<th scope="col">capacity</th>
						<th scope="col">register</th>
					</tr>
				</thead>
				<tbody id="tableBody">

				</tbody>
			</table>
		</div>

		<div id="roomNotAvailable"></div>
	</div>

	<template>
		<tr>
			<th id="roomIdTab" scope="row"></th>
			<td id="floorTab"></td>
			<td id="capacityTab"></td>
			<td><button class="btn btn-success">book</button></td>

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

	function checkAvailableRooms() {
		let startTime = document.getElementById("startTime").value;
		let endTime = document.getElementById("endTime").value;
		
		if(startTime==='' || endTime===''){
			alert ("Choose appropriate start time and end time");
		}else{

		let xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				
				let availabilitytResponse = document.getElementById("availabilityResponse");
				let listOfAvailableRooms = document.getElementById("listOfAvailableRooms");	
				let tableBody = document.getElementById('tableBody');
                let roomNotAvailable = document.getElementById("roomNotAvailable");

				availabilityResponse.style.display = "block";
				
					let response = JSON.parse(xhr.responseText);
					
					let status = response.status;
					let body = response.body;
					
					let  description = status.description;
					
			 if(xhr.status==200){	
					
				   if(description==="roomsAvailable"){
					 tableBody.innerHTML = "";
					
					 listOfAvailableRooms.style.display = "block";
					 roomNotAvailable.style.display = "none";
				

					  for (var i = 0; i < body.length; i++) {
						var eachObject = body[i];
						var templateTag = document.getElementsByTagName("template")[0];
						var eachItemDiv = templateTag.content;
						var copiedDiv = eachItemDiv.cloneNode(true);

						copiedDiv.querySelector("#roomIdTab").innerText = eachObject.roomId;
						copiedDiv.querySelector("#floorTab").innerText = eachObject.floor;
						copiedDiv.querySelector("#capacityTab").innerText = eachObject.capacity;
					
						copiedDiv.querySelector(".btn-success").setAttribute('onclick', "bookRoom(" + eachObject.roomId + ",'" + startTime + "','" + endTime + "','" + eachObject.floor + "')");

						
						let tableBody = document.getElementById('tableBody');
						tableBody.append(copiedDiv);
					 } 
				  }
				   else if(description==="noRoomsAvailable"){
					    listOfAvailableRooms.style.display = "none";
						roomNotAvailable.style.display = "block";
						roomNotAvailable.innerHTML ="";
						
						document.getElementById("listOfAvailableRooms").style.display = "none";
						let message = `<div style="margin-left:200px"  class="jumbotron">
	                                     <h1 class="display-4">No rooms available</h1>
	                                     <p class="lead"> try for different timings</p>
	                                     </div>`;
	                               
	                    roomNotAvailable.innerHTML += (message);
				   }
			 }   
				
			else{
				alert ("server error");
			}

		  }
		};
		xhr.open("POST", "http://localhost:8080/showAvailableRooms");
		xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		xhr.send("startTime=" + startTime + "&endTime=" + endTime);


	}
}
	
	
	function bookRoom(roomId, startTime, endTime, floor){
		
		 let xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {
				if (xhr.readyState == 4) {
					let response = JSON.parse(xhr.responseText);
					if(xhr.status == 200){
						alert("booking successful");
						window.location.href = "http://localhost:8080/showoptions";			   		
					}
					else alert("sorry couldn't book a room due to internal server error");	
				}
			};
			xhr.open("POST", "http://localhost:8080/bookRooom");
			xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xhr.send("roomId=" + roomId+ "&startTime=" +startTime+ "&endTime=" +endTime+ "&floor=" +floor);

	}
	
</script>
</html>