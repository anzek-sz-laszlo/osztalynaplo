/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.osztalynaplo.diaknevsor;


import hu.anzek.backend.osztalynaplo.model.Diakfelvetel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Diakok adatainak fevitele konzol alkalmazása
 * @author User
 */
public class DiakNevsor {
    
    // Ezeket a kódban fel kell használnod: 
    private static final String DIAKOK_FILE = "diaknevsor.txt";   
    Scanner scanner = new Scanner(System.in);
    
    // ezek a mezők tprojektadminisztrációs mezők, 
    // ne foglalkozz velük, ne változtasd, és nem kell használnod sehol sem:
    private LocalDateTime projectNow = null;    
    private static final String TMP_FILE = "projektadmin.tmp";

    // metódus deklarációk:
    public void hozzaad(Diakfelvetel diak) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DIAKOK_FILE, true))) {
            writer.write(diak.toString());
            // ez egyébként (most) pont ugyan az, mint ez itt:
            //writer.write(diak.getNev() + "," + diak.getSzuletesiEve() + "," + diak.getOsztaly());
            // CRLF (#13#10)
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  
    /**
     * .a "diaknevsor,txt" kiolvassa 
     * @return a diákok névsorának lista kollekcióra alakítása
     */
    public List<Diakfelvetel> kiolvasTxt() {
        List<Diakfelvetel> diakok = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DIAKOK_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] adatTomb = line.split(",");
                System.out.println("sor : " + line);
                if(line.length()!=0){
                    diakok.add(new Diakfelvetel(adatTomb[0], Integer.parseInt(adatTomb[1]), adatTomb[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return diakok;
    }  
    
    /**
     * A megrendelések felvitele metódusa
     * @throws IOException lehetséges I/O hiba a fájl írás esetén
     */
    private void diakFelvetel() throws IOException {
        System.out.println("+-----------------------------+");
        System.out.println("| 1. feladat adatok felvitele |");
        System.out.println("+-----------------------------+");  
   if (false) {    
                
        while (true) {
            System.out.print("A diak neve: ");
            String nev = this.scanner.nextLine();
            System.out.print("Szuletesi ev: ");
            int szuletesiEve  = Integer.parseInt(this.scanner.nextLine());
            // puffer törlése (az egész szám után):
            this.scanner.nextLine();  
            System.out.print("Osztalya: ");
            String osztaly = this.scanner.nextLine();
            
            // hozzáadjuk a megrendelt termékek listához:
            Diakfelvetel diak = new Diakfelvetel(nev, szuletesiEve, osztaly);
            this.hozzaad(diak);
            
            System.out.print("Tovabbi diak adatainak felvetele lesz? (i/n): ");
            if (this.scanner.nextLine().equalsIgnoreCase("n")) {
                break;
            }
        }
  
    }
        System.out.println("Diak-nevsorlista elmentve a '" + DIAKOK_FILE + "' nevu fajlba (a projekt-gyoker mappaban kell lennie)."); 
        
        // projekt-adminisztració:               
        this.projektAdmin(1);      
    }

    private void listazDiakokat(List<Diakfelvetel> diakok ) throws IOException {        
        // listázás:
        System.out.println("+-----------------------------+");
        System.out.println("| 2. feladat adatok listazasa |");
        System.out.println("+-----------------------------+");  
        int index = 0;
        for (Diakfelvetel d : diakok) {
            d.toConsole(++index +".");
        }                   
    }
    
    private double szamoljAtlagEletkort(List<Diakfelvetel> diakok) { 
        System.out.println("+-----------------------------+");
        System.out.println("| 3. feladat atlageletkor     |");
        System.out.println("+-----------------------------+");        
        int osszesKor = diakok.stream().mapToInt(diak -> 2024 - diak.getSzuletesiEve()).sum();
        return (double) osszesKor / diakok.size();
    }
    
    private double szamoljMedianEletkort(List<Diakfelvetel> diakok) {  
        System.out.println("+-----------------------------+");
        System.out.println("| 4. feladat median eletkor   |");
        System.out.println("+-----------------------------+");        
        List<Integer> korok = diakok.stream().map(e -> 2024 - e.getSzuletesiEve()).sorted().toList();
        int size = korok.size();
        if (size % 2 == 0) {
            return (korok.get((size / 2) - 1) + korok.get(size / 2)) / 2.0; 
        } else {
            return korok.get(size / 2);
        }        
    }
    
    /**
     * A konzolos alkalmazás második feladatrésze:
     */
    private void tovabbiFeladatok() throws IOException{   
        List<Diakfelvetel> diakok = this.kiolvasTxt();
        // a, Konzolra listázás:
        this.listazDiakokat(diakok);        
        // projekt-adminisztració:               
        this.projektAdmin(2);  
        // b/i, Az átlagéletkor kiszámítása:
        double atlagEletkor = this.szamoljAtlagEletkort(diakok);
        // a System.out.printf(format, number) egy formázó-metajelöléssel íratja ki a numerikus adatokat (itt pl 2 tizedes pontossággal):
        System.out.printf("A diakok atlageletkora: %.2f Ev\n", atlagEletkor );
        // projektadmin:
        this.projektAdmin(3);        
        // b/ii, Az iskolai és osztály (medián átlag) kalkulációja:
        double medianEletkor = this.szamoljMedianEletkort(diakok);
        System.out.printf("A diakok median eletkora: %.2f Ev\n", medianEletkor );     
        // projektadmin:
        this.projektAdmin(4);         
    }
    
    /**
     * A vizsga-indító eljárás - nincs vele dolgod!
     * @return kezdhetjük? (Visszavonhatatlanul megezdi a vizsgát)
     */
    private void tudnivalok() throws IOException {        
        if (!Files.exists(Paths.get(TMP_FILE))) {
            System.out.println("///////////////////////////////");
            System.out.println("//   Fontos tudnivalok!      //");
            System.out.println("///////////////////////////////");
            System.out.println("//                           //");
            System.out.println("//   Olvasd el, fogadd meg!  //");
            System.out.println("///////////////////////////////");
            System.out.println("1, csak a specifikacioban eloirt dolgokkal foglalkozz!");
            System.out.println("2, kovesd a JAVA -konvenciokat (elnevezesek, szokasok, mappaszerkezetek, stb)!");
            System.out.println("3, kovesd a CC elveket!");
            System.out.println("4, a modositast, bovitest, stb, roviden dokumentald: pl a javitott sor folott '//' jelekkel, vagy a JAVADOC '/** * */' konvencioval!");
            System.out.println("5, hasznalhatsz batran magyar elnevezeseket de az ekezeteket keruld el!");
            System.out.println("6, A projekt-adminisztracios reszt ne \"babrald\" - mert ha nem lesz ervenyes adat benne a vizsgad ervenytelen lesz!");
            System.out.println("Fontos: harom ertekelest kell adnunk az '1/a,'-rol, '1/b,'-rol, es a '2,'-rol onallo ertekels lesz!");
            System.out.println("");            
            System.out.println("A feladataidrol:");
            System.out.println("1,  a projekten belul ket kulonallo kod lesz: a, egy konzol alkalmazas; b, egy Springes alkalmazas");
            System.out.println("    ezek egy feladatot alkotva valositjak meg a projekedet!");
            System.out.println("    Nem hagyhato egyik sem el!");
            System.out.println("    Ha nem keszultel el az elsovel idore,tolds fel a minta szerint a txt fajlt, es ne add fel, a masodik reszt igy is megoldhato elso resz hianyaban!");
            System.out.println("1/a) OOP-DiakNevsor() (ez itt, a konzolalkamazas, amely elinditja a vizsgaprojektet)");
            System.out.println("    ...");
            System.out.println("    ...");
            System.out.println("1/b) Backend-DiakokTeljesitmenyen() (a Springes kornyezet, a masik projektreszed lesz, amely kiterjeszti a projektet");
            System.out.println("    ...");
            System.out.println("    ...");
            System.out.println("2, SQL-A masodik feladatod egy MySQL script csatolasa lesz. Ezeket a feladatokat a \"VizsgaSpecifikacio\" tartalmazza!");
            System.out.println("    - a scriptet egy munkafeluleten vegezd el es masold be a projektgyokerben talalhato \"sql.script\" -be!");
            System.out.println("    - a script maga is 100 pontot er!");
            System.out.println("");
            System.out.print("A feladatot elkezdhetjuk? (i/n): ");

            // projekt admin:
            if (scanner.nextLine().equalsIgnoreCase("i")) {
                this.projektAdmin(0);
            }
        }else{
            this.projektAdmin(5);
        }
    }
    
    /**
     * projektadminisztrációs metódus (kérlek ezt nem babráld meg)!
     */
    private void projektAdmin(int logIndex) throws IOException {
        String honnan = "projekt-indítás";
        switch (logIndex){
            case 1 -> honnan = "1, adatbeviteli reszfeladat :";
            case 2 -> honnan = "2/a, adatok listazasa :"; 
            case 3 -> honnan = "2/b/i, atlageletkor :"; 
            case 4 -> honnan = "2/b/ii, median eletkor:";
            case 5 -> honnan = "0-folytatashoz: ujabb projektinditas!";
            default -> honnan = "0-projektinditas...";
        }        

        String s = "";
        if (Files.exists(Paths.get(TMP_FILE ))) {
            s = new String(Files.readAllBytes(Paths.get(TMP_FILE)));
            this.projectNow = LocalDateTime.parse(s.substring(0,19));
        }    
        if (this.projectNow == null) this.projectNow = LocalDateTime.now();
        long projekt = ChronoUnit.MINUTES.between(this.projectNow, LocalDateTime.now());        
        try (PrintWriter tmpWriter = new PrintWriter(new FileWriter(TMP_FILE))) {
            tmpWriter.println(s + this.projectNow.toString() + "\n" + honnan + projekt + "\n\n");
        } catch (IOException e) {
            System.err.println("Projek-adminisztracios hiba!");
        }
    }
    
    /**
     * . A rendszer beléptetési pont (a fő metódus)
     * @param args nincs argumentumlista
     * @throws java.io.IOException lehetséges I/O hiba - fájl írás/olbasás esetén!
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!\n Ez itt a 176-os backend csoport zarovizsga elokeszito utolso komlex feladata!");
        // a statikus környezetet példánmyszintűvé alakítjuk
        // hogy a példányszintű (nem statikus) meódusokat is meg tudjuk hívni:
        DiakNevsor gyrk = new DiakNevsor();
        gyrk.tudnivalok();              
        gyrk.diakFelvetel();
        gyrk.tovabbiFeladatok();        
    }  
}
