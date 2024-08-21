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
public class Jegy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long diakId;
    private String tantargy;
    private int jegy;

    public Jegy() {
    }

    public Jegy(Long diakId, String tantargy, int jegy) {
        this.diakId = diakId;
        this.tantargy = tantargy;
        this.jegy = jegy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDiakId() {
        return diakId;
    }

    public void setDiakId(Long diakId) {
        this.diakId = diakId;
    }

    public String getTantargy() {
        return tantargy;
    }

    public void setTantargy(String tantargy) {
        this.tantargy = tantargy;
    }

    public int getJegy() {
        return jegy;
    }

    public void setJegy(int jegy) {
        this.jegy = jegy;
    }
    
}
