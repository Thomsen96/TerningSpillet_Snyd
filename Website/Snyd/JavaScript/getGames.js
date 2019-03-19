window.onload = function() {
	updateCurrentGames();
}


function updateCurrentGames() {
	var obj, dbParam, xmlhttp, myObj, x, txt = "";
	obj = { table: "customers", limit: 20 };
	dbParam = JSON.stringify(obj);
	xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
		    myObj = JSON.parse(this.responseText);
		    txt += "<table>"
	    	txt += "<tr><th>Port</th><th>Spillere</th><th>terninger</th></tr>";
		    for (x in myObj) {
		      	txt += "<tr><td><p>" 	+myObj[x].port 	+ "</p></td>";
		      	txt += "<td><p>"		+myObj[x].spillere 	+ "</p></td>";
		      	txt += "<td><p>"		+myObj[x].terninger + "</p></td></tr>";
		    }
		    txt += "</table>"    
		    document.getElementById("currentGames").innerHTML = txt;
		  }
	};
	xmlhttp.open("GET", "http://130.225.170.205:8080/REST_Terning_server/games", true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.send("x=" + dbParam);
}
