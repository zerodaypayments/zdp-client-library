package io.zdp.client;

import java.security.KeyPair;

import org.junit.Test;

import io.zdp.api.model.BalanceResponse;
import io.zdp.client.impl.ZdpClientImpl;
import io.zdp.common.crypto.CryptoUtils;
import junit.framework.TestCase;

public class TestZdpClientBalance extends TestCase {

	@Test
	public void test() throws Exception {

		ZdpClientImpl zdp = new ZdpClientImpl();
		zdp.init();
		zdp.setHostUrl("http://localhost");

		System.out.println(zdp.ping());

		System.out.println(zdp.getFee());

		{
			String seed1 = "116b35a304b3c4607e33d92958ab7ace5ca246bf16b43ae63bce9526caea73c4";
			KeyPair keys1 = CryptoUtils.generateKeys(seed1);
			BalanceResponse resp1 = zdp.getAddressBalance(keys1.getPublic().getEncoded(), keys1.getPrivate().getEncoded());
			System.out.println(resp1);

		}

		{
			String seed1 = "226b35a304b3c4607e33d92958ab7ace5ca246bf16b43ae63bce9526caea73c4";
			KeyPair keys1 = CryptoUtils.generateKeys(seed1);
			BalanceResponse resp1 = zdp.getAddressBalance(keys1.getPublic().getEncoded(), keys1.getPrivate().getEncoded());
			System.out.println(resp1);
		}

	}

}
