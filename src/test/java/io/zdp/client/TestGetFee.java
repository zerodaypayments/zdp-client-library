package io.zdp.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.zdp.api.model.v1.GetFeeResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/test-spring-context.xml" })
public class TestGetFee extends BaseModelTest {

	@Autowired
	private ZdpClient zdp;

	@Test
	public void test() throws Exception {

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
