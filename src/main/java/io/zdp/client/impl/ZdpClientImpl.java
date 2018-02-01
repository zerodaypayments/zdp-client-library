package io.zdp.client.impl;

import java.math.BigDecimal;
import java.net.URI;
import java.security.PrivateKey;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.zdp.api.model.BalanceRequest;
import io.zdp.api.model.BalanceResponse;
import io.zdp.api.model.TransferDetails;
import io.zdp.api.model.TransferRequest;
import io.zdp.api.model.TransferResponse;
import io.zdp.client.ZdpClient;
import io.zdp.common.crypto.CryptoUtils;
import io.zdp.common.crypto.Signer;

@Component
public class ZdpClientImpl implements ZdpClient {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private RestTemplate restTemplate;

	@Value("${zdp.api.host}")
	private String hostUrl;

	private static final String URL_PING = "/ping";

	private static final String URL_GET_TX_FEE = "/api/v1/fee";
	private static final String URL_TRANSFER = "/api/v1/transfer";
	private static final String URL_GET_TX_DETAILS = "/api/v1/tx";

	private static final String URL_GET_ADDRESS_BALANCE = "/api/v1/balance";
	private static final String URL_GET_ADDRESSES_BALANCES = "/api/v1/balances";

	@PostConstruct
	public void init() {

		log.debug("Host url: " + hostUrl);

		restTemplate = new RestTemplate();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zerodaypayments.client.ZdpClient#transfer(java.security.PrivateKey,
	 * java.lang.String, java.lang.String, double, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public TransferResponse transfer(byte[] publicKey, byte[] privateKey, String to, BigDecimal amount, String fromRef, String toRef) throws Exception {

		URI uri = new URI(hostUrl + URL_TRANSFER);

		log.debug("transfer: " + uri);

		String fromAddress = Signer.getPublicKeyHash(publicKey);

		TransferRequest req = new TransferRequest();

		req.setAmount(amount);
		req.setDate(new Date());
		req.setPublicKey(publicKey);
		req.setRecipientReference(toRef);
		req.setSenderReference(fromRef);

		PrivateKey priv = Signer.generatePrivateKey(privateKey);
		byte[] signature = Signer.sign(priv, DigestUtils.sha256Hex(fromAddress + to + amount));
		req.setSignature(signature);

		req.setToAddressEncrypted(CryptoUtils.encrypt(priv, to));
		
		req.setRecipientReference(toRef);
		req.setSenderReference(fromRef);

		req.setSignedFromAddress(Signer.sign(priv, fromAddress));

		req.setSignature(signature);

		return restTemplate.postForObject(uri, req, TransferResponse.class);

	}

	@Override
	public TransferDetails getTransaction(String uuid) throws Exception {
		return null;
	}

	public String getHostUrl() {
		return hostUrl;
	}

	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}

	@Override
	public long ping() throws Exception {

		URI uri = new URI(hostUrl + URL_PING);
		return this.restTemplate.getForObject(uri, Long.class);
	}

	@Override
	public BigDecimal getFee() throws Exception {

		final URI uri = new URI(hostUrl + URL_GET_TX_FEE);

		log.debug("Get fee: " + uri);

		return restTemplate.getForObject(uri, BigDecimal.class);

	}

	@Override
	public BalanceResponse getAddressBalance(final byte[] publicKeyBytes, final byte[] privateKeyBytes) throws Exception {

		// Generate address from public key
		final String address = Signer.getPublicKeyHash(publicKeyBytes);

		// Sign address
		final PrivateKey pvt = Signer.generatePrivateKey(privateKeyBytes);
		final byte[] signature = Signer.sign(pvt, address);

		final BalanceRequest req = new BalanceRequest();
		req.setPublicKey(publicKeyBytes);
		req.setSignedAddress(signature);

		final URI uri = new URI(hostUrl + URL_GET_ADDRESS_BALANCE);

		log.debug("Get balance: " + address);

		return restTemplate.postForObject(uri, req, BalanceResponse.class);
	}

	@Override
	public List<BalanceResponse> getAddressesBalances(List<Pair<byte[], byte[]>> keyPairs) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
