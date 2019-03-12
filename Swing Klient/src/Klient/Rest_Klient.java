package Klient;

import java.util.ArrayList;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

public class Rest_Klient {

    String url = "http://130.225.170.205:8080/REST_Terning_server/";

    Client client;
    WebTarget target;

    JSONObject jResponce, recived;

    public Rest_Klient(Client client) {
        this.client = ClientBuilder.newClient();

    }

    SpilData createGame(String user, String Pass, int tern, int spillere) {

        target = client.target(url + "login");

        Response response = target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.json(jResponce.toString()), Response.class);

        if (response.getStatus() == 401) {
            System.out.println("Login failed - " + response.getStatusInfo().getReasonPhrase() + " - response");
            return null;
        } else if (response.getStatus() != 200) {
            System.err.println("Login failed with statuscode: " + response.getStatus());
        }

        return new SpilData(tern, spillere, tern, user);

    }

    ArrayList<SpilData> getGames() {
        return null;
    }

}
