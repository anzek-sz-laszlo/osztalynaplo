/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.backend.osztalynaplo.repository;


import hu.anzek.backend.osztalynaplo.model.Jegy;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * @author User
 */
@Repository
public interface JegyRepository extends JpaRepository<Jegy,Long> {
    public List<Jegy> findByDiakOsztaly(Long id,String osztaly);    

    public List<Jegy> findByDiakId(Long id);
}
