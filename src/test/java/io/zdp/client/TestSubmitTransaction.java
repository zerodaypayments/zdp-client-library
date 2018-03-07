package io.zdp.client;

import java.math.BigDecimal;

import org.junit.Test;

import io.zdp.api.model.v1.CountTransactionsResponse;
import io.zdp.api.model.v1.GetBalanceResponse;
import io.zdp.api.model.v1.GetTransactionDetailsResponse;
import io.zdp.api.model.v1.ListTransactionsResponse;
import io.zdp.api.model.v1.SubmitTransactionResponse;
import io.zdp.client.impl.ZdpClientImpl;
import io.zdp.common.crypto.CryptoUtils;

public class TestSubmitTransaction extends BaseModelTest {

	@Test
	public void test() throws Exception {

		ZdpClientImpl zdp = new ZdpClientImpl();
		zdp.init();
		zdp.setHostUrl("http://localhost");

		String privKey1 = "5NMXiwArVTnTHbBPcrsHj7bzXig8Sf2np7Tg4j9ThGBv";
		String pubKey1 = "xcStKZ3QgfiMuHSQBS4pjFRHDBArqpdiqPNni6diGWCn";

		GetBalanceResponse balance1 = zdp.getBalance(privKey1, pubKey1);

		System.out.println("Balance from:");
		out(balance1);

		String from = CryptoUtils.generateAccountUniqueAddress(pubKey1);

		System.out.println("From:");
		out(from);

		String privKey2 = "5aQSWPxmHkT4p9RabtsFmGgPsEjCmTyph1Hmh6FLeaGn";
		String pubKey2 = "zjr8hMqg5LMiLoV9g2aRQKF61QM1xCnkHeMDsZLF8dV9";

		GetBalanceResponse balance2 = zdp.getBalance(privKey2, pubKey2);

		System.out.println("Balance to:");
		out(balance2);

		String to = CryptoUtils.generateAccountUniqueAddress(pubKey2);

		// 1 -> 2 50 coins
		BigDecimal amount = BigDecimal.valueOf(40.12345678);

		SubmitTransactionResponse resp = zdp.transfer(privKey1, pubKey1, from, to, amount, "REF123");

		assertNotNull(resp.getMetadata().getIsoDate());
		assertNotNull(resp.getType());
		assertNotNull(resp.getMetadata().getUuid());
		assertNotNull(resp.getMetadata().getDate());

		System.out.println(resp);

		out(resp);

		// tx by uuid
		{
			GetTransactionDetailsResponse tx = zdp.getTransactionDetails(resp.getTxUuid());
			out(tx);

			assertNotNull(tx);
			assertEquals(tx.getTxUuid().toLowerCase(), resp.getTxUuid().toLowerCase());
		}

		// tx by from address
		{
			GetTransactionDetailsResponse tx = zdp.getTransactionDetails(from);
			out(tx);

			assertNotNull(tx);
			assertEquals(tx.getTxUuid().toLowerCase(), resp.getTxUuid().toLowerCase());
		}
		
		// tx by to address
		{
			GetTransactionDetailsResponse tx = zdp.getTransactionDetails(to);
			out(tx);

			assertNotNull(tx);
			assertEquals(tx.getTxUuid().toLowerCase(), resp.getTxUuid().toLowerCase());
		}
		
		// count by account
		{
			CountTransactionsResponse countResponse = zdp.getTransactionsCount(privKey1, pubKey1);
			out(countResponse);
			long count = countResponse.getCount();

			assertEquals(1, count);
		}
		
		// list by account
		{
			ListTransactionsResponse listResp = zdp.getTransactions(privKey1, pubKey1, 0, 10);
			out(listResp);
		}
		
		

	}

}
