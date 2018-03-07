package io.zdp.client;

import org.junit.Test;

import io.zdp.api.model.v1.PingResponse;
import io.zdp.client.impl.ZdpClientImpl;

public class TestPing extends BaseModelTest {

	@Test
	public void test() throws Exception {

		ZdpClientImpl zdp = new ZdpClientImpl();
		zdp.init();
		zdp.setHostUrl("http://localhost");

		PingResponse resp = zdp.ping();
		System.out.println(objectMapper.writeValueAsString(resp));

		assertNotNull(resp);
		assertNotNull(resp.getMetadata().getIsoDate());
		assertNotNull(resp.getType());
		assertNotNull(resp.getMetadata().getUuid());
		assertNotNull(resp.getMetadata().getDate());

	}

}
