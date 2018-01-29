package io.zdp.client;

import java.math.BigDecimal;
import java.security.PrivateKey;

import org.junit.Test;

import io.zdp.api.model.AddressDetailsResponse;
import io.zdp.api.model.TransferResponse;
import io.zdp.client.impl.ZdpClientImpl;
import io.zdp.common.crypto.Signer;
import junit.framework.TestCase;

public class TestZdpClient extends TestCase {

	@Test
	public void test() throws Exception {

		ZdpClientImpl zdp = new ZdpClientImpl();
		zdp.init();
		zdp.setHostUrl("http://localhost");

		AddressDetailsResponse addr1 = zdp.getAddress();
		System.out.println(addr1);

		AddressDetailsResponse addr2 = zdp.getAddress();
		System.out.println(addr2);

		System.out.println("Balances: ");

		System.out.println("Addr1: " + addr1.getBalance());
		System.out.println("Addr2: " + addr2.getBalance());

		PrivateKey priv1 = Signer.generatePrivateKey(addr1.getPrivateKey());
		PrivateKey priv2 = Signer.generatePrivateKey(addr2.getPrivateKey());

		TransferResponse transfer = zdp.transfer(priv1, addr1.getAddress(), addr2.getAddress(), BigDecimal.valueOf(50), "my ref", "their ref");

		System.out.println(transfer);

		System.out.println("Bal 1" + zdp.getAddressBalance(priv1, addr1.getAddress()).getBalance());
		System.out.println("Bal 2" + zdp.getAddressBalance(priv2, addr2.getAddress()).getBalance());

	}

}
