/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package hu.anzek.backend.osztalynaplo.service;


import hu.anzek.backend.osztalynaplo.model.Diak;
import hu.anzek.backend.osztalynaplo.model.Jegy;
import hu.anzek.backend.osztalynaplo.repository.DiakRepository;
import hu.anzek.backend.osztalynaplo.repository.JegyRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DiakServiceIntegrationTest {
    
    private final int TARGY_EV = LocalDate.now().getYear();
    
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DiakRepository diakRepository;
    @Autowired
    private JegyRepository jegyRepository;

    public DiakServiceIntegrationTest() {
    }
    
    @Test
    void testLetrehozEsLekerdezDiakot() throws Exception {
        System.out.println("erőforrás: \"/diakok\" (post) -> letrehozEsLekerdezDiakot(): egy diak adatainak rogzitese!");
        // Diák létrehozása
        this.mockMvc.perform(post("/diakok")
                                        .contentType("application/json")
                                        .content("{\"nev\":\"Kiss Béla\",\"szuletesiEve\":2008,\"osztaly\":\"8.A\"}"))
        // {
        //    "nev":"Kiss Béla",
        //    "szuletesiEve":2008,
        //    "osztaly":"8.A"
        //  }                
                
                    .andExpect(status().isOk());
        // Lekérdezés a diákra
        this.mockMvc.perform(get("/diakok"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].nev").value("Kiss Béla"))
                    .andExpect(jsonPath("$[0].osztaly").value("8.A"));
    }
    
    @Test
    void testDiakEsJegyTorzsTorles() throws Exception {
        System.out.println("erőforrás: \"/diakok\" (delete) -> diakEsJegyTorzsTorles(): diak es jegyei torlese!");
        // Diák létrehozása
        Diak diak = new Diak();
        diak.setNev("Kovács Péter");
        diak.setEletkora(this.TARGY_EV - 2009);
        diak.setOsztaly("7.B");
        diak = this.diakRepository.save(diak);
        // Ellenőrizzük, hogy a diák mentésre került
        Assertions.assertNotNull(diak.getId());
    
        // Jegy létrehozása
        Jegy jegy = new Jegy();
        jegy.setDiakId(diak.getId());
        jegy.setTantargy("Matematika");
        jegy.setJegy(4);
        this.jegyRepository.save(jegy);
        // Ellenőrizzük, hogy a jegy mentésre került
        Assertions.assertNotNull(jegy.getId());
        
        // Diák törlése (diákot és kapcsolódó jegyeket is törölni kell)
        this.mockMvc.perform(delete("/diakok/" + diak.getId()))
                    .andExpect(status().isOk());
        
        // Ellenőrzés: diák(és emiatt a jegyei is) törölve vannak-e?
        this.mockMvc.perform(get("/diakok"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("[]"));
    }
    
    @Test
    void testOsztalynaploLekerdezes() throws Exception {
        System.out.println("erőforrás: \"/diakok/osztalynaplo\" -> getOsztalynaplo(): Az osztálynaplo lekerdezese!");
        // Diák és jegyek létrehozása
        Diak diak = new Diak();
        diak.setNev("Szabó Gábor");
        diak.setEletkora(this.TARGY_EV - 2010);
        diak.setOsztaly("6.A");
        diak = diakRepository.save(diak);
        Jegy jegy1 = new Jegy();
        jegy1.setDiakId(diak.getId());
        jegy1.setTantargy("Matematika");
        jegy1.setJegy(5);
        this.jegyRepository.save(jegy1);
        Jegy jegy2 = new Jegy();
        jegy2.setDiakId(diak.getId());
        jegy2.setTantargy("Történelem");
        jegy2.setJegy(4);
        this.jegyRepository.save(jegy2);
        
        // Osztálynapló lekérdezése
        this.mockMvc.perform(get("/diakok/osztalynaplo/6.A"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.Matematika[0].diakNev").value("Szabó Gábor"))
                    .andExpect(jsonPath("$.Matematika[0].osztalyzat").value(5))
                    .andExpect(jsonPath("$.Történelem[0].diakNev").value("Szabó Gábor"))
                    .andExpect(jsonPath("$.Történelem[0].osztalyzat").value(4));
    }
}
