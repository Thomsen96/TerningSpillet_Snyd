

// Create NEW GAME!!
function closeAllGames() {
	var username = document.getElementById("userID").value;
	var password = document.getElementById("PassID").value;
	console.log(username + " " + password);
	var token = login(username, password);
}

function login(username , password) {
	var data = "{\n\"username\" : \""+ username +"\",\n\"password\" : \""+ password +"\"}";
	console.log(data);
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.open("POST", "http://130.225.170.205:8080/REST_Terning_server/login", true);

	//Send the proper header information along with the request
	xmlhttp.setRequestHeader("Content-Type", "application/json");

	xmlhttp.onreadystatechange = function() { // Call a function when the state changes.
	    if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
	    	var myObj = JSON.parse(this.responseText);
	        closeAllGames(myObj.token, username);
	        //console.log(this.responseText);
	    }
	}
	xmlhttp.send(data);
	// xmlhttp.send(new Int8Array()); 
	// xmlhttp.send(document);
}

function closeAllGames(token, username) {
	//console.log(token);
	var data = "{\n\"token\" : \""+ token +"\",\n\"username\" : \""+ username +"\"\n}";
	console.log("Posts: \n" + data);
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.open("POST", "http://130.225.170.205:8080/REST_Terning_server/closeAllGames", true);

	//Send the proper header information along with the request
	xmlhttp.setRequestHeader("Content-Type", "application/json");

	xmlhttp.onreadystatechange = function() { // Call a function when the state changes.
	    if (this.readyState === XMLHttpRequest.DONE) {
	    	console.log(this.status);
	    	console.log(this.responseText);
	    	var myObj = JSON.parse(this.responseText);

	        alert("Closed all games");
	        console.log("Closed all games. From server: " + myObj.response);
	    }
	}
	xmlhttp.send(data);
}

