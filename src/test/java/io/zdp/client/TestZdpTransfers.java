package io.zdp.client;

import org.junit.Test;

import io.zdp.client.impl.ZdpClientImpl;
import junit.framework.TestCase;

public class TestZdpTransfers extends TestCase {

	@Test
	public void test() throws Exception {

		/*
		ZdpClientImpl zdp = new ZdpClientImpl();
		zdp.init();
		zdp.setHostUrl("http://localhost");

		System.out.println(zdp.ping());
		System.out.println(zdp.getFee());

		String privKey1 = "5NMXiwArVTnTHbBPcrsHj7bzXig8Sf2np7Tg4j9ThGBv";
		String pubKey1 = "xcStKZ3QgfiMuHSQBS4pjFRHDBArqpdiqPNni6diGWCn";
		
		zdp.getAccountBalance(keys1.getPublic().getEncoded(), keys1.getPrivate().getEncoded());
		System.out.println(resp1.getBalance());
		String from = CryptoUtils.getUniqueAddressForAccountUuid(Signer.getPublicKeyHash(keys1.getPublic().getEncoded()));

		String seed2 = "2222222222222222222222222222222222222222222222222222222222222222";
		KeyPair keys2 = CryptoUtils.generateKeys(seed2);
		BalanceResponse resp2 = zdp.getAccountBalance(keys2.getPublic().getEncoded(), keys2.getPrivate().getEncoded());
		System.out.println(resp2.getBalance());

		String to = CryptoUtils.getUniqueAddressForAccountUuid(Signer.getPublicKeyHash(keys2.getPublic().getEncoded()));

		// 1 -> 2 50 coins
		BigDecimal amount = BigDecimal.valueOf(40.12345678);

		SubmitTransactionResponse resp = zdp.transfer(keys1.getPublic().getEncoded(), keys1.getPrivate().getEncoded(), from, to, amount, "REF123");
		assertNotNull(resp.getDate());
		System.out.println(resp);

		// tx by uuid
		{
			TransferDetails tx = zdp.getTransaction(resp.getUuid());
			assertNotNull(tx);
			assertEquals(tx.getUuid().toLowerCase(), resp.getUuid().toLowerCase());
		}

		// tx by account
		{
			TransactionHeadersResponse transactions = zdp.getTransactionHeaders(keys1.getPublic().getEncoded(), keys1.getPrivate().getEncoded());
			assertNotNull(transactions);
			System.out.println("transactions: " + transactions.getTransactions().size());
		}
*/
	}

}
