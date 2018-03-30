package io.zdp.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.zdp.api.model.v1.GetPublicKeyResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/test-spring-context.xml" })
public class TestZdpGetPublicKey extends BaseModelTest {

	@Autowired
	private ZdpClient zdp;

	@Test
	public void test() throws Exception {

		GetPublicKeyResponse resp = zdp.getPublicKey("4Cy24BAn9KwkMZzaYgJjvQFzc1yxGvVSGm6a2X53FqxC");

		assertEquals("25wxARFSXq5sFwUbcRJ4TLpGV4mGsiADrzrmwnJq6xPT6", resp.getPublicKey());

		out(resp);

	}

}
