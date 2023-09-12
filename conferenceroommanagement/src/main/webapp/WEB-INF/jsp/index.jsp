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

<title>Login</title>
</head>
<body>

	<div
		style="justify-content: center; margin-top: 100px; margin-left: 550px">
		<h2 id="homeTitle">
			<b>Schedule your conference rooms on the go!!!!</b>
		</h2>
		<br>
		<div id="emailForm">
			<div class="mb-3">
				<label for="email">Email address</label> <input style="width: 150px"
					type="email" class="form-control" id="email" placeholder="email">
			</div>
			<button onclick="verifyEmployee()" class="btn btn-primary">Submit</button>
		</div>
	</div>



	<div id="toast">
		<div id="img"></div>
		<div id="desc"></div>

	</div>



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

function loadPropertiesFile(){

//Load the properties from config.properties
var xhr = new XMLHttpRequest();
xhr.open("GET", "config/config.properties", false);
xhr.send();

if (xhr.status === 200) {
    var propertiesText = xhr.responseText;
    var propertiesArray = propertiesText.split('\n');
    var properties = {};
    
    for (var i = 0; i < propertiesArray.length; i++) {
        var line = propertiesArray[i];
        var keyValue = line.split('=');
        if (keyValue.length === 2) {
            var key = keyValue[0].trim();
            var value = keyValue[1].trim();
            properties[key] = value;
        }
    }
    
    var domain = properties.domain;
    var port = properties.port;
    
    console.log(domain);
    console.log(port);
    
    // Use the domain and port values in your HTTP request
     
    var httpRequestObject = new XMLHttpRequest();
    httpRequestObject.open("POST", url);
    httpRequestObject.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    httpRequestObject.send("email=" + email);
} else {
    alert("Failed to load properties file");
}
}


function verifyEmployee(){
	 let email = document.getElementById("email").value;
	 console.log(email); 
     var domain = "localhost";
     var port = "8080";
	 //ajax call
	 
	 let httpRequestObject = new XMLHttpRequest();
		httpRequestObject.onreadystatechange = function() {
			if (httpRequestObject.readyState == 4) {
				let response = JSON.parse(httpRequestObject.responseText);
				
				let status = response.status.statusCode;
				let body = response.body;
				
				let  description = status.description;
				console.log(status)
			   if(status===200){
				   window.location.href = "http://localhost:8080/showoptions";			   
					 
			   }
			   else if(status===403){
					alert("invalid email id");
			   }
			   else if(status===500){
				   alert("server error");
			   }
			}
		};
		
		let url = "http://" + domain + ":" + port + "/verifyEmployee";
		  console.log(url); 
		
		httpRequestObject.open("POST", url);
		httpRequestObject.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		httpRequestObject.send("email=" + email);

   }
   
 


</script>

</html>