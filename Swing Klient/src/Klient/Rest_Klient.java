package Klient;

import java.util.ArrayList;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class Rest_Klient {

    String url;

    Client client;
    WebTarget target;

    //JSONObject jResponse, jRecived;
    String token;

    public Rest_Klient() {
        this.url = "http://130.225.170.205:8080/REST_Terning_server/";
        this.client = ClientBuilder.newClient();
    }

    SpilData createGame(String user, String pass, int tern, int spillere) {

        JSONObject jResponse = new JSONObject();
        jResponse.put("username", user);
        jResponse.put("password", pass);

        target = client.target(url + "login");
        Response response = target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.json(jResponse.toString()), Response.class);

        if (response.getStatus() == 401) {
            System.out.println("Login failed - " + response.getStatusInfo().getReasonPhrase() + " - response");
            return null;
        } else if (response.getStatus() != 200) {
            System.err.println("UNKNOWN ERROR - Login failed with statuscode: " + response.getStatus());
        }

        JSONObject jRecived = new JSONObject(response.readEntity(String.class));

        token = jRecived.getString("token");
        jResponse = new JSONObject();
        jResponse.put("token", token);
        jResponse.put("spillere", spillere);
        jResponse.put("terninger", tern);

        target = client.target(url + "games");
        response = target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.json(jResponse.toString()), Response.class);

        if (response.getStatus() == 401) {
            System.out.println("Creating game failed - " + response.getStatusInfo().getReasonPhrase() + " - response");
            return null;
        } else if (response.getStatus() != 200) {
            System.err.println("UNKNOWN ERROR - Creating game failed with statuscode: " + response.getStatus());
            return null;
        }

        jRecived = new JSONObject(response.readEntity(String.class));

//TODO remove OPT for get in line below
        return new SpilData(jRecived.getInt("port"), jRecived.getInt("spillere"), jRecived.getInt("terninger"), jRecived.optString("brugernavn"));

    }

    ArrayList<SpilData> getGames() {
        target = client.target(url + "games");
        Response response = target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .get();
        if (response.getStatus() != 200) {
            System.err.println("UNKNOWN ERROR - Get games failed with statuscode: " + response.getStatus());
            return null;
        }

        JSONArray jRecived = new JSONArray(response.readEntity(String.class));
        ArrayList<SpilData> list = new ArrayList<SpilData>();

        for (int i = 0; i < jRecived.length(); i++) {
            SpilData temp = new SpilData(((JSONObject) jRecived.get(i)).getInt("port"),
                    ((JSONObject) jRecived.get(i)).getInt("spillere"),
                    ((JSONObject) jRecived.get(i)).getInt("terninger"),
                    ((JSONObject) jRecived.get(i)).optString("brugernavn"));
            list.add(temp);
        }
        return list;
    }

}
