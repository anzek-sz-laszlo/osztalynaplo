/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.osztalynaplo.controller;

import hu.anzek.backend.osztalynaplo.mapper.DiakMapper;
import hu.anzek.backend.osztalynaplo.model.DiakOsztalyzat;
import hu.anzek.backend.osztalynaplo.model.Diakfelvetel;
import hu.anzek.backend.osztalynaplo.model.Jegy;
import hu.anzek.backend.osztalynaplo.model.JegyDto;
import hu.anzek.backend.osztalynaplo.service.DiakService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author User
 */
@RestController
@RequestMapping("/diakok")
public class DiakController {
    
    private final DiakService diakService;
    private final DiakMapper mapper;
    
    @Autowired
    public DiakController(DiakService diakService, DiakMapper mapper) {
        this.diakService = diakService;
        this.mapper = mapper;
    }
    
    @PostMapping
    public ResponseEntity<Diakfelvetel> letrehozDiakot(@RequestBody Diakfelvetel diakfelvetel) {
        return ResponseEntity.ok( this.mapper.entityToDto(this.diakService.letrehozDiakot(diakfelvetel)));
    }

    @GetMapping
    public ResponseEntity<List<Diakfelvetel>> getOsszesDiak() {
       return ResponseEntity.ok( this.mapper.listToDtos(this.diakService.getOsszesDiak()) );
    }

    @DeleteMapping("/{diakId}")
    public ResponseEntity<Void> torolDiakot(@PathVariable Long diakId) {
        this.diakService.torolDiakot(diakId);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/import")    
    public void importDiakok(@RequestBody List<Diakfelvetel> diakfelvetelek) {
        this.diakService.importDiakok(diakfelvetelek);
    }

    @GetMapping("/letezike/{id}")
    public ResponseEntity<String> letezikE(@PathVariable("id") Long id) {
        if(this.diakService.isDiakLetezik(id))
            return ResponseEntity.ok( "Letezik a(z) " + id +" azonositoval egy diak");
        else 
            return ResponseEntity.notFound().build();    
    }
    
    @PostMapping("/osztalyzat")
    public ResponseEntity<Jegy> createJegy(@RequestBody JegyDto jegyDto) {
        Jegy jegy = this.diakService.letrehozOsztalyzatot(jegyDto);
        if(jegy != null) {
            return ResponseEntity.ok(jegy);
        }else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/osztalynaplo/{osztaly}")
    public ResponseEntity< Map<String, List<DiakOsztalyzat>>> getOsztalynaplo(@PathVariable String osztaly) {
        Map<String, List<DiakOsztalyzat>> jegylista = this.diakService.getOsztalynaplo(osztaly);
        if( ! jegylista.isEmpty()) {
            return ResponseEntity.ok(jegylista);
        }else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/osztalyatlag/{osztaly}")
    public ResponseEntity<Double> getOsztalyAtlag(@PathVariable String osztaly) {
        Double atlag = this.diakService.getOsztalyAtlag(osztaly);
        if(atlag != null) {
            return ResponseEntity.ok(atlag);
        }else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/iskolaiatlag")
    public ResponseEntity<Double> getIskolaiAtlag() {
        Double atlag = this.diakService.getIskolaiAtlag();
        if(atlag != null) {
            return ResponseEntity.ok(atlag);
        }else{
            return ResponseEntity.badRequest().body(null);
        }        
    }
}
