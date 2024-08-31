/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;


/**
 * a MOCKITO. azért fontos, mert így (pl a DiakService) függőségeit 
 * teljesen magunk szabályozhatjuk, vezérelhetjük a tesztekben. 
 * Így nem kell valódi adatbázis kapcsolatot létrehozni vagy kezelni.
 * Ez gyorsabbá és biztonságosabbá teszi a tesztelést, a tesztek elszigeteltek maradnak, 
 * és csak a tesztelent üzleti logikára figyelünk.
 * @author User
 */
// @SpringBootTest
public class DiakServiceOriginTest {
        
    @InjectMocks
    // Ezt az osztályt teszteljük, injektáljuk
    // amikor a Teszt elindul a Mockito létrehoz egy DiakService() példányt 
    // amelybe beinjektálja a @Mock annotációval mockolt példányokat
    // @Autowired
    private DiakService diakService;   
        
    @Mock
    // @MockBean
    // azért mock-oljuk, hogy ne hajtsunk végre valódi adatbázis műveleteket!    
    private DiakRepository diakRepository;
    
    @Mock 
    // azért mock-oljuk, hogy ne hajtsunk végre valódi adatbázis műveleteket! 
    private JegyRepository jegyRepository;
    
    public DiakServiceOriginTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of importDiakok method, of class DiakService.
     */
    @Test
    @SuppressWarnings("null")
    public void testImportDiakok() {
        System.out.println("importDiakok : A teszteset a diak felvitelt vizsgalta.");
        
        List<Diakfelvetel> diakfelvetelek = new ArrayList<>();
        Diakfelvetel felvetel = new Diakfelvetel("Kiss1 Anna", 2010, "6.A"); 
        diakfelvetelek.add(felvetel);
        felvetel = new Diakfelvetel("Kiss2 Anna", 2010, "6.A");
        diakfelvetelek.add(felvetel);
        felvetel = new Diakfelvetel("Kiss3 Anna", 2010, "6.A");
        // listává kell alakítanunk:
        diakfelvetelek.add(felvetel);
        this.diakService.importDiakok(diakfelvetelek);
        verify(this.diakRepository, times(3)).save(any(Diak.class));
        //fail("A teszteset a diak felvitelt vizsgalta volna, de elbukott a teszt.");
    }

    /**
     * Test of isDiakLetezik method, of class DiakService.
     */
    @Test
    public void testIsDiakLetezik() {
        System.out.println("isDiakLetezik : A teszteset a diak letezeset viszgalja");
        // előkészítés
        Long diakId = 10L;
        // Mock setup: 
        // azért, hogy ha a "diakRepóból" lekérdezik, hogy létezik-e a "diakId" : 
        // - akkor inden esetben mondd azt, hogy "igen", létezik!
        when(this.diakRepository.existsById(10L)).thenReturn(true); 
//        // ezt várjuk el:
//        boolean expResult = true;
//        // ezt kapjuk vissza a "service-osztályból" - amely viszont a "diakRepóból" kérdezi le a "diakId" -t!
//        boolean result = this.diakService.isDiakLetezik(diakId);
        Assertions.assertEquals(true, this.diakService.isDiakLetezik(diakId));
        // az eredmény egy egyszerűbb formában:
//        Assertions.assertTrue(result);
        Assertions.assertTrue(this.diakService.isDiakLetezik(diakId));
        // illetve még azt is megnézzük, hogy legalább egyszer lefutott-e a repositoryban a keresés?
        verify(this.diakRepository, times(2)).existsById(diakId);
        //fail("A teszteset a diak letezeset viszgalta volna, de elbukott.");
    }

    /**
     * Test of letrehozOsztalyzatot method, of class DiakService.
     */
    @Test
    public void testLetrehozOsztalyzatot() {
        System.out.println("letrehozOsztalyzatot : A teszteset egy osztalyzat letrehozasat ellenori.");
        // előkészítés
        JegyDto jegyDto = new JegyDto(1L, "Matematika", 5);   
        // itt is javvítjuk a generált tesztet és létrehozunk egy osztályzat (pontosabban egy Jegy()) példányt:
        Jegy jegy = new Jegy();
        jegy.setDiakId(jegyDto.getDiakId());
        jegy.setTantargy(jegyDto.getTantargy());
        jegy.setJegy(jegyDto.getJegy());
        // Mock setup        
        when(this.diakService.isDiakLetezik(jegyDto.getDiakId())).thenReturn(true);
        // when(this.diakRepository.existsById(jegyDto.getDiakId())).thenReturn(true);            
        when(this.jegyRepository.save(any(Jegy.class))).thenReturn(jegy);        
        // a mockolás miatt most már futni fog az Osztályzat létrehozás, amely két dolgot csinál:
        // - egyszer ellenőrzi, hogy létezik-e a diák (amit a JegyDto-n kaptunk a RESTkontrollerünkből)
        // - majd elmenti adatbázisba az osztályzat példányt
        // végrehajtás:
        Jegy result = this.diakService.letrehozOsztalyzatot(jegyDto);
        // result = this.diakService.letrehozOsztalyzatot(jegy1Dto);
        // jöhetnek az ellenőrzések:
        Assertions.assertNotNull(result);
        Assertions.assertEquals(jegy, result);
        Assertions.assertEquals(jegy.getDiakId(), result.getDiakId());
        Assertions.assertEquals(jegy.getTantargy(), result.getTantargy());
 //       Assertions.assertEquals(jegy.getJegy(), result.getJegy());        
        // itt is megnézzük, hogy egyszer legalább ténylegesen lefutott-e a .save(Jegy.class) -adatbázisba mentése:
        verify(this.jegyRepository, times(1)).save(any(Jegy.class));
        //fail("A teszteset egy osztalyzat letrehozasat ellenorizte volna, de elbukott.");
    }

    /**
     * Test of getOsztalynaplo method, of class DiakService.
     */
    @Test
    @SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
    public void testGetOsztalynaplo() {
        System.out.println("getOsztalynaplo : Egy osztaly Osztalynaplo-listajanak a tesztesete.");
        String osztaly = "6.A";
        Diak diak = new Diak(1L, "KOCSIS BELA",13,"6.A");
        Jegy jegy = new Jegy();
        jegy.setDiakId(1L);
        jegy.setTantargy("Matematika");
        jegy.setJegy(5);  
        DiakOsztalyzat diakOsztalyzat = new DiakOsztalyzat(diak.getNev(), jegy.getJegy());
        // Megcsináljuk a mock-setup-ot:
        // amikor az "osztaly"-t (6.A) keressük az adatbázisban, 
        // - mindig ezt az egyetlen (a fenti) az általunk mockolt egyetlen Diak-ból álló "listát" adja vissza:
        when(this.diakRepository.findByOsztaly(osztaly)).thenReturn(Collections.singletonList(diak));
        // amikor keressük a a Diak-jegyeit az adatbázis Jegy() osztályban a mockolt Jegy-et "találja meg"
        // és ebből csináljon egy önálló listát, azt adja vissza (mintha csak egy osztályzat lenne a naplóban):
        when(this.jegyRepository.findByDiakId(1L)).thenReturn(Collections.singletonList(jegy));
        // most végrehajtjuk a művelet üzleti logikáját:
        Map<String, List<DiakOsztalyzat>> result = this.diakService.getOsztalynaplo(osztaly);
        Assertions.assertEquals(Collections.singletonMap("Matematika", Collections.singletonList(diakOsztalyzat)), result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(jegy.getJegy(), result.get(jegy.getTantargy()).get(0).getOsztalyzat());
        // itt is megnézzük, hogy egyszer legalább ténylegesen lefutott-e az adatbázisban az osztály keresése:
        verify(this.diakRepository, times(1)).findByOsztaly(osztaly);
        // illetve, hogy a jegyRepo-ban a DiakId -re keresés is egyszer futott-e valójában?
        verify(this.jegyRepository, times(1)).findByDiakId(1L);
        //fail("Egy osztaly Osztalynaplo-listajanak a tesztesete elbukott.");
    }

    /**
     * Test of getOsztalyAtlag method, of class DiakService.
     */
    @Test
    public void testGetOsztalyAtlag() {
        System.out.println("getOsztalyAtlag : Az osztalyatlag tesztesete");
        Diak diak = new Diak(0L,"KOCSIS BELA",13,"6.A");
        String osztaly = "6.A";
        // létrehozunk két osztályzat példányt (azaz két Jegy() példányt!)
        Jegy jegy1 = new Jegy();
        jegy1.setJegy(5);
        Jegy jegy2 = new Jegy();
        jegy2.setJegy(3);
        // Beállítjuk a Mock setupot, hogyha az adatbázsiban jeressük a "6.A"-t, akkor a fenti két osztlyzatból álló listát
        // (persze itt most mi egy tömböt alakítunk át List<T> kollekcióvá) találja meg és adja vissza mint a lekérdezés eredményét:
        when(this.diakRepository.findByOsztaly(osztaly)).thenReturn(Collections.singletonList(diak));
        when(this.jegyRepository.findByDiakId(0L)).thenReturn(Arrays.asList(jegy1, jegy2));
        // most már tesztelhetjük, hogy működik-e az átlagoló:
        double result = this.diakService.getOsztalyAtlag(osztaly);
        // Na és akkor az ellenőrzés - ilyen még nem volt:
        // "Assertions.assertEquals(expected, actual, delta)"
        // expected: az érték, amit elvártunk, hogy a tesztelt metódus visszaadjon.
        // actual: a tényleges érték, amit a tesztelt metódus visszaadott.
        // delta: a megengedett eltérés az elvárt és a tényleges érték között.
        Assertions.assertEquals(4.0, result, 0.01);
        // És szintén itt is megnézzük, hogy egyszer legalább ténylegesen lefutott-e az adatbázisban az osztály keresése:
        verify(this.diakRepository, times(1)).findByOsztaly(osztaly);
        // meg legalább egyszer lefutott-e a diák jegyeinek a lekérdezése:
        verify(this.jegyRepository, times(1)).findByDiakId(0L);
        //fail("Az osztalyatlag tesztesete elbukott.");
    }

    /**
     * Test of getIskolaiAtlag method, of class DiakService.
     */
    @Test
    public void testGetIskolaiAtlag() {
        System.out.println("getIskolaiAtlag : Az iskolai atlag tesztesete.");
        Diak diak = new Diak(0L,"KOCSIS BELA",13,"6.A");   
        // létrehozunk két osztályzat példányt (azaz két Jegy() példányt!)
        Jegy jegy1 = new Jegy();
        jegy1.setJegy(4);
        Jegy jegy2 = new Jegy();
        jegy2.setJegy(5);
        // Itt is hasonló mockolás lesz, mint az osztályátlag esetében, csak most
        // nem az osztályra keresünk, hanem az összes tanulóra:
        when(this.diakRepository.findAll()).thenReturn(Collections.singletonList(diak));
        when(this.jegyRepository.findByDiakId(0L)).thenReturn(Arrays.asList(jegy1, jegy2));
        // most már tesztelhetjük, hogy működik-e az átlagoló:
        double result = this.diakService.getIskolaiAtlag();
        // és az ellenőrzés:
        Assertions.assertEquals(4.5, result, 0.01);
        // És szintén itt is megnézzük, hogy egyszer legalább ténylegesen lefutott-e az adatbázisban az osztály keresése:
        verify(this.diakRepository, times(1)).findAll();
        // meg legalább egyszer lefutott-e a diák jegyeinek a lekérdezése:
        verify(this.jegyRepository, times(1)).findByDiakId(0L);
        //fail("Az iskolai atlag tesztesete elbukott.");
    }    
}
