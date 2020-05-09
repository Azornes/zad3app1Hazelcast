import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.concurrent.Callable;

import com.hazelcast.client.HazelcastClient;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.map.IMap;

public class HExecutorService {

    public static void main( HazelcastInstance client ) throws UnknownHostException {

        //final HazelcastInstance client = HazelcastClient.newHazelcastClient(  );

		IExecutorService executorService = client.getExecutorService("exec");
		executorService.submitToAllMembers(new HCallable());
	}
}

class HCallable implements Callable<Integer>, Serializable, HazelcastInstanceAware {
	private static final long serialVersionUID = 1L;	
	private transient HazelcastInstance instance;
	
	@Override
	public Integer call() {
		IMap<Long, Atrakcja> atrakcje = instance.getMap("atrakcje");
		Set<Long> keys = atrakcje.localKeySet();
		for (Long k : keys) {
			System.out.println("Instance " + instance + " " + k + " => " + atrakcje.get(k));
		}
		return keys.size();
	}

	@Override
	public void setHazelcastInstance(HazelcastInstance instance) {
		this.instance = instance;
	}
}