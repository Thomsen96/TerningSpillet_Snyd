

// Create NEW GAME!!
function closeAllGames() {
	var username = document.getElementById("userID").value;
	var password = document.getElementById("PassID").value;
	////console.log("Calls login with: " + username + " " + password);
	var token = login2(username, password);
}

function login2(username , password) {
	////console.log("login Recived:" + username + " & " + password);
	var data = "{\n\"username\" : \""+ username +"\",\n\"password\" : \""+ password +"\"}";
	////console.log("Login POSTS:\n" + data);
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.open("POST", "http://130.225.170.205:8080/REST_Terning_server/login", true);

	//Send the proper header information along with the request
	xmlhttp.setRequestHeader("Content-Type", "application/json");

	xmlhttp.onreadystatechange = function() { // Call a function when the state changes.
	    if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
	    	var myObj = JSON.parse(this.responseText);
	    	console.log("Recived: "+ myObj);
	        closeAllGames2(myObj.token, username);
	    }
	}
	xmlhttp.send(data);
}

function closeAllGames2(token, username) {
	////console.log("closeAllGames was called with: " + token + " & " + username);
	var data = "{\n\"token\" : \""+ token +"\",\n\"username\" : \""+ username +"\"\n}";
	////console.log("Posts: \n" + data);
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.open("POST", "http://130.225.170.205:8080/REST_Terning_server/closeAllGames", true);

	//Send the proper header information along with the request
	xmlhttp.setRequestHeader("Content-Type", "application/json");

	xmlhttp.onreadystatechange = function() { // Call a function when the state changes.
	    if (this.readyState === XMLHttpRequest.DONE) {
	    	//console.log(this.status);
	    	//console.log(this.responseText);
	    	var myObj = JSON.parse(this.responseText);
	    	updateCurrentGames();
	        //alert("Closed all games");
	        //console.log("Closed " + myObj.gamesClosed + " games");

	    }
	}
	xmlhttp.send(data);
}

