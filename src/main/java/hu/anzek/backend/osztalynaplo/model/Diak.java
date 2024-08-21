/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.osztalynaplo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/**
 *
 * @author User
 */
@Entity
public class Diak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nev;
    private int eletkora;
    private String osztaly;    

    public Diak() {
    }

    public Diak(String nev, int eletkora, String osztaly) {
        this.nev = nev;
        this.eletkora = eletkora;
        this.osztaly = osztaly;
    }
    
    public Diak(long id, String nev, int eletkora, String osztaly) {
        this.id = id;
        this.nev = nev;
        this.eletkora = eletkora;
        this.osztaly = osztaly;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getEletkora() {
        return eletkora;
    }

    public void setEletkora(int eletkora) {
        this.eletkora = eletkora;
    }

    public String getOsztaly() {
        return osztaly;
    }

    public void setOsztaly(String osztaly) {
        this.osztaly = osztaly;
    }

}
