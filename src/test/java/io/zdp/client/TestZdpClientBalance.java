package io.zdp.client;

import org.junit.Test;

import io.zdp.api.model.v1.GetBalanceResponse;
import io.zdp.client.impl.ZdpClientImpl;
import junit.framework.TestCase;

public class TestZdpClientBalance extends TestCase {

	@Test
	public void test() throws Exception {

		ZdpClientImpl zdp = new ZdpClientImpl();
		zdp.init();
		zdp.setHostUrl("http://localhost");

		System.out.println(zdp.ping());
		System.out.println(zdp.getFee());

		String privKey1 = "5NMXiwArVTnTHbBPcrsHj7bzXig8Sf2np7Tg4j9ThGBv";
		String pubKey1 = "xcStKZ3QgfiMuHSQBS4pjFRHDBArqpdiqPNni6diGWCn";
		
		GetBalanceResponse balance = zdp.getBalance(privKey1, pubKey1);
		
		assertNotNull(balance);

	}

}
