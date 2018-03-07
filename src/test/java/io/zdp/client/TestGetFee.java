package io.zdp.client;

import org.junit.Test;

import io.zdp.api.model.v1.GetFeeResponse;
import io.zdp.client.impl.ZdpClientImpl;

public class TestGetFee extends BaseModelTest {

	@Test
	public void test() throws Exception {

		ZdpClientImpl zdp = new ZdpClientImpl();
		zdp.init();
		zdp.setHostUrl("http://localhost");

		GetFeeResponse fee = zdp.getFee();
		System.out.println(objectMapper.writeValueAsString(fee));

		assertNotNull(fee);
		assertNotNull(fee.getFee());
		assertNotNull(fee.getMetadata().getIsoDate());
		assertNotNull(fee.getType());
		assertNotNull(fee.getMetadata().getUuid());
		assertNotNull(fee.getMetadata().getDate());

	}

}
