/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.osztalynaplo.model;


import lombok.Data;


/**
 *
 * @author User
 */
@Data
public class DiakOsztalyzat {
    private String diakNev;
    private int osztalyzat;

    public DiakOsztalyzat(String diakNev, int osztalyzat) {
        this.diakNev = diakNev;
        this.osztalyzat = osztalyzat;
    }    
}
