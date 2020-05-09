import java.net.UnknownHostException;

import com.hazelcast.core.Hazelcast;

public class HInstance {

    public static void main(String[] args) throws UnknownHostException {
		Hazelcast.newHazelcastInstance();
	}
}