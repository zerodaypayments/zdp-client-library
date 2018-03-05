package io.zdp.client;

import org.junit.Test;

import io.zdp.client.impl.ZdpClientImpl;
import junit.framework.TestCase;

public class TestZdpGetPublicKey extends TestCase {

	@Test
	public void test() throws Exception {

		ZdpClientImpl zdp = new ZdpClientImpl();
		zdp.init();
		zdp.setHostUrl("http://localhost");


	}

}
