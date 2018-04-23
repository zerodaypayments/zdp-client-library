package io.zdp.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.zdp.api.model.v1.PingResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/test-spring-context.xml" })
public class TestPing extends BaseModelTest {

	@Autowired
	private ZdpClient zdp;

	@Test
	public void testS() throws Exception {

		PingResponse resp = zdp.ping();
		System.out.println(objectMapper.writeValueAsString(resp));

		assertNotNull(resp);
		assertNotNull(resp.getMetadata().getIsoDate());
		assertNotNull(resp.getType());
		assertNotNull(resp.getMetadata().getUuid());
		assertNotNull(resp.getMetadata().getDate());

	}

}
