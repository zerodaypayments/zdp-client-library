package io.zdp.client;

import java.math.BigDecimal;
import java.security.KeyPair;

import org.junit.Test;

import io.zdp.api.model.BalanceResponse;
import io.zdp.api.model.TransferResponse;
import io.zdp.client.impl.ZdpClientImpl;
import io.zdp.common.crypto.CryptoUtils;
import junit.framework.TestCase;

public class TestZdpTransfers extends TestCase {

	@Test
	public void test() throws Exception {

		ZdpClientImpl zdp = new ZdpClientImpl();
		zdp.init();
		zdp.setHostUrl("http://localhost");

		System.out.println(zdp.ping());

		System.out.println(zdp.getFee());

		String seed1 = "1111111111111111111111111111111111111111111111111111111111111111";
		KeyPair keys1 = CryptoUtils.generateKeys(seed1);
		BalanceResponse resp1 = zdp.getAccountBalance(keys1.getPublic().getEncoded(), keys1.getPrivate().getEncoded());
		System.out.println(resp1.getBalance());
		
		String from = CryptoUtils.getUniqueAddressForAccountUuid(resp1.getAddress());

		String seed2 = "2222222222222222222222222222222222222222222222222222222222222222";
		KeyPair keys2 = CryptoUtils.generateKeys(seed2);
		BalanceResponse resp2 = zdp.getAccountBalance(keys2.getPublic().getEncoded(), keys2.getPrivate().getEncoded());
		System.out.println(resp2.getBalance());
		
		String to = CryptoUtils.getUniqueAddressForAccountUuid(resp2.getAddress());

		// 1 -> 2 50 coins
		BigDecimal amount = BigDecimal.valueOf(40.12345678);
		
		TransferResponse resp = zdp.transfer(keys1.getPublic().getEncoded(), keys1.getPrivate().getEncoded(), from, to, amount, "memo goes here");
		
		assertNotNull(resp.getDate() );

		System.out.println(resp);

	}

}
