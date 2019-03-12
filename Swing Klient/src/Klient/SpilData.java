/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klient;

/**
 *
 * @author root
 */
class SpilData {

    private final int port;
    private final int Spillere;
    private final int terninger;
    private final String brugernavn;

    public SpilData(int port, int Spillere, int terninger, String brugernavn) {
        this.port = port;
        this.Spillere = Spillere;
        this.terninger = terninger;
        this.brugernavn = brugernavn;
    }

    public String getBrugernavn() {
        return brugernavn;
    }

    public int getPort() {
        return port;
    }

    public int getSpillere() {
        return Spillere;
    }

    public int getTerninger() {
        return terninger;
    }
}
