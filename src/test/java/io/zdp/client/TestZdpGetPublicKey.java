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

		GetPublicKeyResponse resp = zdp.getPublicKey("4uGzWU6fVXYmAMKj3eHLDK1aEmkeRMK2DzxVmSRMgogK");

		assertEquals("ibiuVPmcJgKeFYzqx7uxv4LGzH2faCKJrLtN3ok58k7q", resp.getPublicKey());

		out(resp);

	}

}
