/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testrestapi;

import java.util.Scanner;
import java.io.Console;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.*;

public class CloseAllGames {

    public static void main(String[] args) {
        Console cons = System.console();
        JSONObject login = new JSONObject();
        System.out.println("!---Her lukkes alle igangværende spil!---!");
        System.out.println("Indtast brugernavn:");
        login.put("username", cons.readLine());
        
        System.out.println("Indtast adgangskode:");
        String pwd = new String(cons.readPassword());
        login.put("password", pwd);

        //String url = "http://localhost:8080/";
        String url = "http://130.225.170.205:8080/REST_Terning_server/";

        System.out.println("Login attempt");

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url + "login");
        Response response = target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.json(login.toString()), Response.class);
        if (response.getStatus() != 200) {
            System.out.println("Login failed!");
            System.exit(0);
        }
        System.out.println("Login suceeded!");
        System.out.println("Code: " + response.getStatus() + " Response: " + response.getStatusInfo().getReasonPhrase());

        JSONObject jResponse = new JSONObject(response.readEntity(String.class));
        //System.out.println("" + jResponse.toString());

        String token = jResponse.optString("token");

        login.put("token", token);
        login.remove("password");   
        
        
        System.out.println("Closing all games.");

        target = client.target(url + "closeAllGames");
        response = target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.json(login.toString()), Response.class);
        
        if (response.getStatus() != 200) {
            System.out.println("Closing all games failed - " + response.getStatusInfo().getReasonPhrase() + " - response");
            System.out.println("Are there any games left to close?");
            System.exit(0);
        }
        System.out.println("DONE!");
        
    }
}
