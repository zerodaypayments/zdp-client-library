package io.zdp.client;

import org.junit.Test;

import io.zdp.api.model.v1.GetNewAccountResponse;
import io.zdp.api.model.v1.GetPublicKeyResponse;
import io.zdp.client.impl.ZdpClientImpl;

public class TestGetNewAccount extends BaseModelTest {

	@Test
	public void test() throws Exception {

		ZdpClientImpl zdp = new ZdpClientImpl();
		zdp.setHostUrl("http://localhost");
		zdp.init();

		GetNewAccountResponse newAccount = zdp.getNewAccount();

		out(newAccount);

	}

}
