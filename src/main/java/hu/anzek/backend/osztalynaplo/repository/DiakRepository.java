/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.backend.osztalynaplo.repository;


import hu.anzek.backend.osztalynaplo.model.Diak;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * @author User
 */
@Repository
public interface DiakRepository extends JpaRepository<Diak,Long> {
    public List<Diak> findByOsztaly(String osztaly);  
    Diak findByNev(String nev);
}
