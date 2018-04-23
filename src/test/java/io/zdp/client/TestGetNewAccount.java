package io.zdp.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.zdp.api.model.v1.GetNewAccountResponse;
import io.zdp.crypto.Curves;
import io.zdp.crypto.mnemonics.Mnemonics.Language;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/test-spring-context.xml" })
public class TestGetNewAccount extends BaseModelTest {

	@Autowired
	private ZdpClient zdp;

	@Test
	public void test() throws Exception {

		GetNewAccountResponse newAccount = zdp.getNewAccount(Curves.DEFAULT_CURVE, Language.ENGLISH);

		out(newAccount);

	}

}
