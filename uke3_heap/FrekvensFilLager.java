import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// 
// OBS! FrekvensFilLager er ikke pensum, det er kun er verktøy jeg skrev for å lage
// frekvens-filer, noe man trenger når man skal bruke Huffman-koding
// 

public class FrekvensFilLager {
    public static void main(String[] args) {
        String filnavn = "foilEks.in";

        // Lage frekvens-fil av en input-string:
        new FrekvensFilLager("det er veldig vanskelig å finne på en eksempelsetning", filnavn);
        
        /* output-filen blir slik:
            a 1
            d 2
            e 10
            ...
        */

        // Lage frekvens-fil av en input-fil:
        // new FrekvensFilLager(new File(<input-filnavn>), <output-filnavn>);

        HuffmanBuilder huff = new HuffmanBuilder(filnavn);
    }

    // Input er en string
    public FrekvensFilLager(String tekst, String outFilNavn) {
        finnFrekvensene(tekst, outFilNavn);
    }

    // Input er en fil
    public FrekvensFilLager(File fil, String outFilNavn) {
        String tekst = "";
        try {
            Scanner in = new Scanner(fil);
            while (in.hasNextLine()) {
                tekst += in.nextLine();
            }
            in.close();
        } catch (Exception e) {}
        finnFrekvensene(tekst, outFilNavn);
    }

    public void finnFrekvensene(String tekst, String outFilnavn) {
        // Tell opp frekvensene
        char[] tegn = tekst.toCharArray();
        HashMap<Character, Integer> ordbok = new HashMap<Character, Integer>();
        for (char c : tegn) {
            if (ordbok.containsKey(c)) {
                ordbok.put(c, ordbok.get(c) + 1);
            } else {
                ordbok.put(c, 1);
            }
        }

        // Sorter tegnene (ikke nødvendig)
        List<Character> charSorted = ordbok.keySet().stream().collect(Collectors.toList());
        Collections.sort(charSorted, (o1, o2) -> o1.compareTo(o2));
        
        // Lag output-string
        String output = "";
        for (char c : charSorted) {
            output += c + " " + ordbok.get(c) + "\n";
        }
        output = output.substring(0, output.length() - 1);

        // Skriv til fil
        try {
            FileWriter outWriter = new FileWriter(new File(outFilnavn));
            outWriter.write(output);
            outWriter.close();
        } catch (Exception e) {}
    }
}