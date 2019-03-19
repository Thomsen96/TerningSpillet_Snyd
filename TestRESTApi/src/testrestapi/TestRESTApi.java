/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testrestapi;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.*;

public class TestRESTApi {

    public static void main(String[] args) {

        JSONObject login = new JSONObject();
        login.put("username", "madas");
        login.put("password", "madsersej");

        //String url = "http://localhost:8080/";
        String url = "http://130.225.170.205:8080/REST_Terning_server/";

        System.out.println("Login attempt");

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url + "login");
        Response response = target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.json(login.toString()), Response.class);

        System.out.println("Code: " + response.getStatus() + " Response: " + response.getStatusInfo().getReasonPhrase());

        JSONObject jResponse = new JSONObject(response.readEntity(String.class));
        System.out.println("" + jResponse.toString());

        String token = jResponse.optString("token");

        login.put("token", token);
        login.put("spillere", 2);
        login.put("terninger", 3);

        System.out.println("Create game attempt:");

        target = client.target(url + "games");
        response = target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.json(login.toString()), Response.class);
        //status
        System.out.println("Code: " + response.getStatus() + " Response: " + response.getStatusInfo().getReasonPhrase());

        //response
        jResponse = new JSONObject(response.readEntity(String.class));
        System.out.println("" + jResponse.toString());

        
        
        System.out.println("Get games:");

        target = client.target(url + "games");
        response = target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .get();
        JSONArray jArr = new JSONArray(response.readEntity(String.class));

        System.out.println("Games:" + jArr.toString());

        
        
        /*
        System.out.println("Post closeGame:");

        login.put("port", jResponse.getInt("port"));

        target = client.target(url + "closeGame");
        response = target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.json(login.toString()), Response.class);
        jResponse = new JSONObject(response.readEntity(String.class));

        System.out.println("Games:" + jResponse.toString());
        */
        
        
        System.out.println("Get games2:");

        target = client.target(url + "games");
        response = target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .get();
        jArr = new JSONArray(response.readEntity(String.class));

        System.out.println("Games:" + jArr.toString());
        
        
        
        System.out.println("Post closeAllGames:");

        target = client.target(url + "closeAllGames");
        response = target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.json(login.toString()), Response.class);
        
        if (response.getStatus() != 200) {
            System.out.println("Creating game failed - " + response.getStatusInfo().getReasonPhrase() + " - response");
            System.exit(0);
        }
        jResponse = new JSONObject(response.readEntity(String.class));

        System.out.println("Games:" + jResponse.toString());
        
        
        
        System.out.println("Get games3:");

        target = client.target(url + "games");
        response = target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .get();
        jArr = new JSONArray(response.readEntity(String.class));

        System.out.println("Games:" + jArr.toString());
        

    }
}
