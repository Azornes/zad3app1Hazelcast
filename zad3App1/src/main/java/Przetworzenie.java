import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.Map.Entry;
import java.util.Scanner;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.map.EntryProcessor;

public class Przetworzenie {

    public static void main( HazelcastInstance client) throws UnknownHostException {

        IMap<Long, Atrakcja> atrakcje = client.getMap("atrakcje");

        Scanner in2 = new Scanner(System.in);
        System.out.println("Podaj nazwę atrakcji dla ktrócyh chcesz powiękdzyć długość wycieczki");
        String nazwa = "";
        nazwa = in2.nextLine();

        atrakcje.executeOnEntries(new PrzetworzenieNaSerwerze(nazwa));

        for (Entry<Long, Atrakcja> e : atrakcje.entrySet()) {
            System.out.println(e.getKey() + " => " + e.getValue());
        }
    }
}

class PrzetworzenieNaSerwerze implements EntryProcessor<Long, Atrakcja, Integer>, Serializable {
    private static final long serialVersionUID = 1L;

    String nazwa = "";

    public PrzetworzenieNaSerwerze(String nazwa) {
        this.nazwa = nazwa;
    }

    @Override
    public Integer process(Entry<Long, Atrakcja> e) {
        Atrakcja atrakcja = e.getValue();
        int dlugoscwycieczki = atrakcja.getDlugoscwycieczki();
        String nazwa1 = atrakcja.getNazwa();


        if (nazwa.equals(nazwa1)) {
            System.out.println("Before Processing = " + atrakcja);
            atrakcja.setDlugoscwycieczki(dlugoscwycieczki + 1);
            System.out.println("After Processing = " + atrakcja);
            e.setValue(atrakcja);
        }
        //System.out.println("Processing = " + atrakcja);
        //e.setValue(atrakcja);

        return dlugoscwycieczki;
    }
}
