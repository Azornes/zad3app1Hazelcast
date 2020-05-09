import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.Map.Entry;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.map.EntryProcessor;

public class ZmianaLiter {

    public static void main( HazelcastInstance client ) throws UnknownHostException {
		//final HazelcastInstance client = HazelcastClient.newHazelcastClient();

		IMap<Long, Atrakcja> atrakcje = client.getMap("atrakcje");
		atrakcje.executeOnEntries(new HEntryProcessor());

		for (Entry<Long, Atrakcja> e : atrakcje.entrySet()) {
			System.out.println(e.getKey() + " => " + e.getValue());
		}
	}
}

class HEntryProcessor implements EntryProcessor<Long, Atrakcja, String>, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String process(Entry<Long, Atrakcja> e) {
		Atrakcja atrakcja = e.getValue();
		String nazwa = atrakcja.getNazwa();
		if (nazwa.equals(nazwa.toLowerCase())) {
			nazwa = nazwa.toUpperCase();
			atrakcja.setNazwa(nazwa);
		} else{
			nazwa = nazwa.toLowerCase();
			atrakcja.setNazwa(nazwa);
		}
		
		System.out.println("Processing = " + atrakcja);
		e.setValue(atrakcja);
		
		return nazwa;
	}
}
