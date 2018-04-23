package io.zdp.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/test-spring-context.xml" })
public class TestSubmitTransaction extends BaseModelTest {

	@Autowired
	private ZdpClient zdp;

	@Test
	public void test() throws Exception {
/*
		String privKey1 = "5NMXiwArVTnTHbBPcrsHj7bzXig8Sf2np7Tg4j9ThGBv";

		GetBalanceResponse balance1 = zdp.getBalance(privKey1, Curves.DEFAULT_CURVE);

		System.out.println("Balance from:");
		out(balance1);

		System.out.println("From:");
		out(from);

		String privKey2 = "5aQSWPxmHkT4p9RabtsFmGgPsEjCmTyph1Hmh6FLeaGn";
		String pubKey2 = Base58.encode(CryptoUtils.getPublicKeyFromPrivate(new java.math.BigInteger(org.bitcoinj.core.Base58.decode(privKey2)), true));

		GetBalanceResponse balance2 = zdp.getBalance(privKey2);

		System.out.println("Balance to:");
		out(balance2);

		String to = CryptoUtils.generateUniqueAddressByPublicKey58(pubKey2);

		// 1 -> 2 50 coins
		BigDecimal amount = BigDecimal.valueOf(40.12345678);

		SubmitTransactionResponse resp = zdp.transfer(privKey1, from, to, amount, "REF123");

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
			assertEquals(tx.getUuid().toLowerCase(), resp.getTxUuid().toLowerCase());
		}

		// tx by from address
		{
			GetTransactionDetailsResponse tx = zdp.getTransactionDetails(from);
			out(tx);

			assertNotNull(tx);
			assertEquals(tx.getUuid().toLowerCase(), resp.getTxUuid().toLowerCase());
		}

		// tx by to address
		{
			GetTransactionDetailsResponse tx = zdp.getTransactionDetails(to);
			out(tx);

			assertNotNull(tx);
			assertEquals(tx.getUuid().toLowerCase(), resp.getTxUuid().toLowerCase());
		}
*/
	}

}
