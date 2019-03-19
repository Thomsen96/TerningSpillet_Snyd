

// Create NEW GAME!!
function closeGame() {
	var username = document.getElementById("userID").value;
	var password = document.getElementById("PassID").value;
	console.log("Calls login with: " + username + " " + password);
	var token = login1(username, password);
}

function login1(username , password) {
	console.log("login Recived:" + username + " & " + password);
	var data = "{\n\"username\" : \""+ username +"\",\n\"password\" : \""+ password +"\"}";
	console.log("Login POSTS:\n" + data);
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.open("POST", "http://130.225.170.205:8080/REST_Terning_server/login", true);

	//Send the proper header information along with the request
	xmlhttp.setRequestHeader("Content-Type", "application/json");

	xmlhttp.onreadystatechange = function() { // Call a function when the state changes.
	    if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
	    	var myObj = JSON.parse(this.responseText);
	    	console.log("Recived: " + myObj);
	        closeGame2(myObj.token, username);
	        //console.log(this.responseText);
	    }
	}
	xmlhttp.send(data);
	// xmlhttp.send(new Int8Array()); 
	// xmlhttp.send(document);
}

function closeGame2(token, username) {
	var port = document.getElementById("closeGameID").value;
	console.log("closeGame was called with: " + token + " & " + username + " Og port: " + port);
	var data = "{\n\"token\" : \""+ token +"\",\n\"username\" : \""+ username +"\",\n\"port\" : "+ port +",\n}";
	console.log("closeGame POSTS: \n" + data);
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.open("POST", "http://130.225.170.205:8080/REST_Terning_server/closeGame", true);

	//Send the proper header information along with the request
	xmlhttp.setRequestHeader("Content-Type", "application/json");

	xmlhttp.onreadystatechange = function() { // Call a function when the state changes.
	    if (this.readyState === XMLHttpRequest.DONE) {
	    	//console.log(this.status);
	    	//console.log(this.responseText);
	    	updateCurrentGames();
	    	var myObj = JSON.parse(this.responseText);
	        var port = myObj.port;
	        //alert("Closed game on port: \n" + port);
	        console.log("Closed game on port: \n" + port);
	        
	    }
	}
	xmlhttp.send(data);
}

