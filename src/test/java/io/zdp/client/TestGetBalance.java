package io.zdp.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.zdp.api.model.v1.GetBalanceResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/test-spring-context.xml" })
public class TestGetBalance extends BaseModelTest {

	@Autowired
	private ZdpClient zdp;

	@Test
	public void test() throws Exception {

		String privKey1 = "5NMXiwArVTnTHbBPcrsHj7bzXig8Sf2np7Tg4j9ThGBv";
		String pubKey1 = "xcStKZ3QgfiMuHSQBS4pjFRHDBArqpdiqPNni6diGWCn";

		GetBalanceResponse balance = zdp.getBalance(privKey1);

		assertNotNull(balance);
		assertNotNull(balance.getAmount());
		assertNotNull(balance.getMetadata().getIsoDate());
		assertNotNull(balance.getType());
		assertNotNull(balance.getMetadata().getUuid());
		assertNotNull(balance.getMetadata().getDate());

		System.out.println(objectMapper.writeValueAsString(balance));

	}

}
