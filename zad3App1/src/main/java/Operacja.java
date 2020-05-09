import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import com.hazelcast.aggregation.Aggregators;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.EntryProcessor;
import com.hazelcast.map.IMap;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;

public class Operacja {

    public static void pobierzPoNazwie(HazelcastInstance client) throws UnknownHostException {

        IMap<Long, Atrakcja> atrakcje = client.getMap("atrakcje");

        Scanner in = new Scanner(System.in);
        System.out.println("Podaj nazwę atrakcji którą chcesz wyszukać");
        String wybor = "";
        wybor = in.nextLine();

        Predicate<?,?> nazwaPredicate = Predicates.equal( "nazwa", wybor );

        Collection<Atrakcja> atrakcja = atrakcje.values(Predicates.and(nazwaPredicate));
        for (Atrakcja s : atrakcja) {
            System.out.println(s);
        }
    }

    public static void pobierzPoDacie(HazelcastInstance client) throws UnknownHostException {

        IMap<Long, Atrakcja> atrakcje = client.getMap("atrakcje");
        Collection<Atrakcja> atrakcja = atrakcje.values( Predicates.sql( "(nazwa=Rzym OR nazwa = Karaiby) AND (dlugoscwycieczki BETWEEN 3 AND 10)" ) );
        for (Atrakcja s : atrakcja) {
            System.out.println(s);
        }
    }

    public static void pobierzWszystko(HazelcastInstance client) throws UnknownHostException {

        IMap<Long, Atrakcja> atrakcje = client.getMap("atrakcje");
        System.out.println("Wszystkie atrakcje: ");
        for (Map.Entry<Long, Atrakcja> e : atrakcje.entrySet()) {
            System.out.println(e.getKey() + " => " + e.getValue());
        }
    }

    public static void agregacja( HazelcastInstance client) throws UnknownHostException {
        IMap<Long, Atrakcja> atrakcje = client.getMap("atrakcje");
        System.out.println(atrakcje.aggregate(Aggregators.integerMin("dlugoscwycieczki")));
    }


    final private static Random r = new Random(System.currentTimeMillis());

    public static void zapiszDoMapy(HazelcastInstance client) throws UnknownHostException {

        Map<Long, Atrakcja> atrakcje = client.getMap("atrakcje");
        Long key1 = (long) Math.abs(r.nextInt());
        byte key11 = (byte) Math.abs(r.nextInt(30));
        Atrakcja atrakcja1 = new Atrakcja("Rzym", key11 + 1);
        System.out.println("PUT " + key1 + " => " + atrakcja1);
        atrakcje.put(key1, atrakcja1);
        Long key2 = (long) Math.abs(r.nextInt());
        byte key12 = (byte) Math.abs(r.nextInt(30));
        Atrakcja atrakcja2 = new Atrakcja("Karaiby", key12+ 1);
        atrakcje.put(key2, atrakcja2);
        System.out.println("PUT " + key2 + " => " + atrakcja2);
        Long key3 = (long) Math.abs(r.nextInt());
        byte key13 = (byte) Math.abs(r.nextInt(30));
        Atrakcja atrakcja3 = new Atrakcja("Gdańsk", key13+ 1);
        atrakcje.put(key3, atrakcja3);
        System.out.println("PUT " + key3 + " => " + atrakcja3);
        Long key4 = (long) Math.abs(r.nextInt());
        byte key14 = (byte) Math.abs(r.nextInt(30));
        Atrakcja atrakcja4 = new Atrakcja("Kair", key14+ 1);
        atrakcje.put(key4, atrakcja4);
        System.out.println("PUT " + key4 + " => " + atrakcja4);
        Long key5 = (long) Math.abs(r.nextInt());
        byte key15 = (byte) Math.abs(r.nextInt(30));
        Atrakcja atrakcja5 = new Atrakcja("Rio de Janeiro", key15+ 1);
        atrakcje.put(key5, atrakcja5);
        System.out.println("PUT " + key5 + " => " + atrakcja5);
        Long key6 = (long) Math.abs(r.nextInt());
        byte key16 = (byte) Math.abs(r.nextInt(30));
        Atrakcja atrakcja6 = new Atrakcja("Praga", key16+ 1);
        atrakcje.put(key6, atrakcja6);
        System.out.println("PUT " + key6 + " => " + atrakcja6);

    }

    public static void usunWszystko(HazelcastInstance client ) throws UnknownHostException {
        IMap<Long, Atrakcja> atrakcje = client.getMap( "atrakcje" );
        atrakcje.evictAll();
    }

    public static void Przetworzenie(HazelcastInstance client ) throws UnknownHostException {

        IMap<Long, Atrakcja> atrakcje = client.getMap("atrakcje");

        Scanner in2 = new Scanner(System.in);
        System.out.println("Podaj nazwę atrakcji dla ktrócyh chcesz powiękdzyć długość wycieczki");
        String nazwa = "";
        nazwa = in2.nextLine();

        System.out.println("Wszystkie atrakcje: ");
        for (Map.Entry<Long, Atrakcja> e : atrakcje.entrySet()) {
            Atrakcja atrakcja = e.getValue();
            int dlugoscwycieczki = atrakcja.getDlugoscwycieczki();
            String nazwa1 = atrakcja.getNazwa();

            if (nazwa.equals(nazwa1)) {
                System.out.println("Before Processing = " + atrakcja);
                atrakcja.setDlugoscwycieczki(dlugoscwycieczki + 1);
                System.out.println("After Processing = " + atrakcja);
                //e.setValue(atrakcja);

                atrakcje.replace(e.getKey(), atrakcja);

            }
        }
    }

}
