/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.osztalynaplo.model;


/**
 *
 * @author User
 */
public class Diakfelvetel {
    private String nev;
    private int szuletesi_eve;
    private String osztaly;

    public Diakfelvetel() {
    }

    public Diakfelvetel(String nev, int szuletesi_eve, String osztaly) {
        this.nev = nev;
        this.szuletesi_eve = szuletesi_eve;
        this.osztaly = osztaly;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getSzuletesiEve() {
        return szuletesi_eve;
    }

    public void setSzuletesiEve(int szuletesi_eve) {
        this.szuletesi_eve = szuletesi_eve;
    }

    public String getOsztaly() {
        return osztaly;
    }

    public void setOsztaly(String osztaly) {
        this.osztaly = osztaly;
    }
    
    @Override
    public String toString() {
        return this.nev + "," + this.szuletesi_eve + "," + this.osztaly;
    }
    
    public void toConsole(String s) {
        System.out.println("Diak(" + s + ")\n    {\n     neve : " 
                            + this.nev + "\n     szuletett : " 
                            + this.szuletesi_eve + "\n     osztalya : " 
                            + this.osztaly + "\n    }");
    }
}
