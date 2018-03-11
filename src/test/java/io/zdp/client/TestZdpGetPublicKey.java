package io.zdp.client;

import org.junit.Test;

import io.zdp.api.model.v1.GetPublicKeyResponse;
import io.zdp.client.impl.ZdpClientImpl;

public class TestZdpGetPublicKey extends BaseModelTest {

	@Test
	public void test() throws Exception {

		ZdpClientImpl zdp = new ZdpClientImpl();
		zdp.setHostUrl("http://localhost");
		zdp.init();

		GetPublicKeyResponse resp = zdp.getPublicKey("4Cy24BAn9KwkMZzaYgJjvQFzc1yxGvVSGm6a2X53FqxC");

		assertEquals("25wxARFSXq5sFwUbcRJ4TLpGV4mGsiADrzrmwnJq6xPT6", resp.getPublicKey());

		out(resp);

	}

}
