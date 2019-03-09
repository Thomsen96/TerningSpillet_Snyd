/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_RMI;

/**
 *
 * @author root
 */
class SpilData {
    
    private int ID;
    private int Spillere;
    private int terninger;

    public SpilData(int ID, int Spillere, int terninger) {
        this.ID = ID;
        this.Spillere = Spillere;
        this.terninger = terninger;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSpillere() {
        return Spillere;
    }

    public void setSpillere(int Spillere) {
        this.Spillere = Spillere;
    }

    public int getTerninger() {
        return terninger;
    }

    public void setTerninger(int terninger) {
        this.terninger = terninger;
    }
}
