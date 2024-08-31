/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.osztalynaplo.mapper;


import hu.anzek.backend.osztalynaplo.model.Diak;
import hu.anzek.backend.osztalynaplo.model.Diakfelvetel;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;


/**
 *
 * @author User
 */
@Service
public class DiakMapper {
    
    public Diakfelvetel entityToDto(Diak diak) {
        return new Diakfelvetel(diak.getNev(), LocalDate.now().getYear() - diak.getEletkora() ,diak.getOsztaly());
    }
    
    public List<Diakfelvetel> listToDtos(List<Diak> l) {
        return l.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
