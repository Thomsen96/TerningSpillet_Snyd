/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_RMI;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author john
 */
public class Storage {
    static public HashMap<String, String> tokenMap = new HashMap<String, String>(); //Usernames mapped to tokens
    static public ArrayList<String> admins = new ArrayList<String>();
}
