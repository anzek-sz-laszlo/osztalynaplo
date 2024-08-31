/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.backend.osztalynaplo.repository;


import hu.anzek.backend.osztalynaplo.model.Jegy;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 *
 * @author User
 */
@Repository
public interface JegyRepository extends JpaRepository<Jegy,Long> {
    @Query("SELECT j FROM Jegy j WHERE j.diakId IN (SELECT d.id FROM Diak d WHERE d.osztaly = :osztaly)")    
    public List<Jegy> findByDiakOsztaly(@Param("osztaly") String osztaly);    
    public List<Jegy> findByDiakId(Long id);
    void deleteByDiakId(Long diakId);
}
