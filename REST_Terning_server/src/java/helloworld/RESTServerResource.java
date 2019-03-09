/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 * 
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */

package helloworld;

import brugerautorisation.transport.soap.Bruger;
import java.util.HashMap;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author mkuchtiak
 */

@Stateless
@Path("")
public class RESTServerResource {

    @EJB
    private NameStorageBean nameStorage = new NameStorageBean();
    
    
    
    //private HashMap<String, String> tokenMap = new HashMap<String, String>(); //Usernames mapped to tokens
    /**
     * Retrieves representation of an instance of helloworld.HelloWorldResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getGreeting() {
        return "<html><body><h1>Hello "+nameStorage.getName()+"!</h1></body></html>";
    }
    
    @Path("games")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGames() {
        //TODO return list of games gotten from Server Spil object over RMI
        JSONArray games = new JSONArray();
        JSONObject game = new JSONObject();
        game.put("spillere", 22022);
        game.put("terninger", 22);
        game.put("token", 44);
        JSONObject game2 = new JSONObject();
        game2.put("spillere", 3300);
        game2.put("terninger", 6);
        game2.put("token", 5);
        
        
        
        games.put(game);
        games.put(game2);
        return Response.ok(games.toString(), MediaType.APPLICATION_JSON).build();
    }
    
    @Path("games")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postGames(String content){
        JSONObject recived = new JSONObject(content);
        JSONObject response = new JSONObject();
        String token = recived.getString("token");
        String username = recived.getString("username");
        
        //Authenticate
        if(Storage.tokenMap.get(username).equals(token)){
            //TODO: Code here to gennerate game over RMI
            
            return Response.ok(response.toString(), MediaType.APPLICATION_JSON).build();
        }else {
            //not valid user/token
            System.out.println("User failed to authenticate with username" + username + " and token: " +token);
            response.put("url", "https://http.cat/401");
            return Response.status(Response.Status.UNAUTHORIZED).entity(response.toString()).build();
        }
        
        
    }
    
    
    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postLogin(String content) {
        //TODO return proper representation object
        JSONObject login = new JSONObject(content);
        JSONObject response = new JSONObject();
        
        Boolean loginSucess = Boolean.FALSE;
        
        
        String username = login.optString("username");
        String password = login.getString("password");
        
        System.out.println("Recived login: " + username + "@" +password);
        
        
        //Contact brugerautorisationsmodul
        try{
            Bruger user = hentBruger(username, password);
            System.out.println("User: "+ user.getBrugernavn()+ " - Sucessfull login");
            loginSucess = Boolean.TRUE;
        } catch (Exception e){
            e.printStackTrace();
        }
        
        System.out.println("Bruger: "+ username);
        
        
        if (loginSucess){
            //check for token
            
            String token = Storage.tokenMap.get(username);
            
            //if no token gennerate one
            if(token == null){
                token = UUID.randomUUID().toString();
                Storage.tokenMap.put(username, token);
                System.out.println("Token gennerated: " + token);
            } else {
                System.out.println("Token already mapped: " + token);
            }
            
            Storage.tokenMap.put(username, token);
            response.put("token", token);
            
            return Response.ok(response.toString(), MediaType.APPLICATION_JSON).build();
        } else {
            response.put("url", "https://http.cat/401");
            return Response.status(Response.Status.UNAUTHORIZED).entity(response.toString()).build();
        }
    }
    
    
    
    
    
    

    private static Bruger hentBruger(java.lang.String arg0, java.lang.String arg1) {
        brugerautorisation.transport.soap.BrugeradminImplService service = new brugerautorisation.transport.soap.BrugeradminImplService();
        brugerautorisation.transport.soap.Brugeradmin port = service.getBrugeradminImplPort();
        return port.hentBruger(arg0, arg1);
    }
    
    
}


