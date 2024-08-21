/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.osztalynaplo.service;


import hu.anzek.backend.osztalynaplo.model.Diak;
import hu.anzek.backend.osztalynaplo.model.DiakOsztalyzat;
import hu.anzek.backend.osztalynaplo.model.Diakfelvetel;
import hu.anzek.backend.osztalynaplo.model.Jegy;
import hu.anzek.backend.osztalynaplo.model.JegyDto;
import hu.anzek.backend.osztalynaplo.repository.DiakRepository;
import hu.anzek.backend.osztalynaplo.repository.JegyRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;


/**
 *
 * @author User
 */
@Service
public class DiakService {

    private final DiakRepository diakRepository;
    private final JegyRepository jegyRepository;

    public DiakService(DiakRepository diakRepository,
                       JegyRepository jegyRepository) {
        this.diakRepository = diakRepository;
        this.jegyRepository = jegyRepository;
    }

    /**
     * Átvesszük a diákok listáját, de csak ha még nincs benn adat!
     * @param diakfelvetelek a Txt -be felsorolt diakok adatainak listaja
     */
    public void importDiakok(List<Diakfelvetel> diakfelvetelek) {
        // megnézzük van-e adat az adatbázisban? -> ha nincs, akkor átvesszük azokat
        if(this.diakRepository.findAll().isEmpty()) {
            for (Diakfelvetel felvetel : diakfelvetelek) {
                Diak diak = new Diak();
                diak.setNev(felvetel.getNev());
                diak.setEletkora(2024 - felvetel.getSzuletesiEve());
                diak.setOsztaly(felvetel.getOsztaly());
                this.diakRepository.save(diak);
            }
        }
    }

    public boolean isDiakLetezik(Long diakId) {
        return this.diakRepository.existsById(diakId);
    }

    public Jegy letrehozOsztalyzatot(JegyDto jegyDto) {
        if(jegyDto!=null) {
            if ( ! this.isDiakLetezik(jegyDto.getDiakId())) {
                throw new IllegalArgumentException("Nincs ilyen diak az adatbazisban!");
            }
            Jegy jegy = new Jegy(jegyDto.getDiakId(), jegyDto.getTantargy(), jegyDto.getJegy());
            return this.jegyRepository.save(jegy);
        }
        return null;
    }

    public Map<String, List<DiakOsztalyzat>> getOsztalynaplo(String osztaly) {
        // Lekérjük az adott osztály összes diákját
        List<Diak> diakok = this.diakRepository.findByOsztaly(osztaly);
        // Létrehozzuk az osztálynapló map -et (inicializálunk egy Map<K,V> kollekciót):
        Map<String, List<DiakOsztalyzat>> osztalynaplo = new HashMap<>();
        // leiteráljuk a "diakok" listakollekció referenciát:
        for (Diak diak : diakok) {
            // lekérjük az egyes diákok jegyeit (diákonként csinálunk egy jegyek kollekciót):
            List<Jegy> jegyek = this.jegyRepository.findByDiakId(diak.getId());
            // feldolgozzuk a diák jegyeit:            
            for (Jegy jegy : jegyek) {
                // Megnézzük, hogy létezik-e már ilyen tantárgy a map-ben?
                // - és ha nem, akkor inicializálunk neki ehgy új tömböt amely KULCS eleme a tantárgy neve lesz:                
                osztalynaplo.putIfAbsent(jegy.getTantargy(), new ArrayList<>());
                // Végül hozzáadjuk a diák nevét és osztályzatát a tantárgyhoz:
                // itt a .get(kulcs) egy List<DiakOsztalyzat> típusú referenciát(!) ad vissza (azaz a kollekció memóriacímét), 
                // - ezért tudjnk az .add(diakNeve,jegy) -el hozzáadni egy új elemet:
                osztalynaplo.get(jegy.getTantargy()).add(new DiakOsztalyzat(diak.getNev(), jegy.getJegy()));
            }
        }
        return osztalynaplo;
    }

    public double getOsztalyAtlag(String osztaly) {
        // Lekérjük az adott osztály összes diákját
        List<Diak> diakok = this.diakRepository.findByOsztaly(osztaly);
        // A diákok összes jegyének az összegét és darabszámát átlagoljuk:
        return atalgolas(diakok);
    }

    public double getIskolaiAtlag() {
        // majdnem ugyan az, mint az osztályátlag, de más a bemenete:
        // Lekérjük az iskola összes diákját:
        List<Diak> diakok = this.diakRepository.findAll();
        return atalgolas(diakok);
    }   

    private double atalgolas(List<Diak> diakok) {
        // Ha nincs diák, nincs átlag sem
        if (diakok.isEmpty()) {            
            return 0.0;
        }        
        // Minden diákhoz tartozó jegyet lekérünk a jegyRepository.findByDiakId(diak.getId()) metódussal:
        // A diákok összes jegyének az összegét a "sum" -ban eltároljuk, a "count"-al pedig számoljuk, hány db jegyetz adtunk össze:
        double sum = 0;
        int count = 0;
        for (Diak diak : diakok) {
            List<Jegy> jegyek = this.jegyRepository.findByDiakId(diak.getId());
            for (Jegy jegy : jegyek) {
                sum += jegy.getJegy();
                count++;
            }
        }
        // Ha nincs egy jegy sem, akkor nincs átlag
        if (count == 0) {            
            return 0.0;
        }
        // A diákok összes jegyének az összegét és darabszámát átlagoljuk:
        return sum / count;
    }   
}
