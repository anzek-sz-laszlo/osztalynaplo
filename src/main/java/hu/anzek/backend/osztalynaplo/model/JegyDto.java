/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.osztalynaplo.model;


import lombok.AllArgsConstructor;
import lombok.Data;


/**
 *
 * @author User
 */
@Data
@AllArgsConstructor
public class JegyDto {
    private Long diakId;
    private String tantargy;
    private int jegy;
}
