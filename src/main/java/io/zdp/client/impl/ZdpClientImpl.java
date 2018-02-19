package io.zdp.client.impl;

import java.math.BigDecimal;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.zdp.api.model.AccountRequest;
import io.zdp.api.model.AddressResponse;
import io.zdp.api.model.BalanceRequest;
import io.zdp.api.model.BalanceResponse;
import io.zdp.api.model.Key;
import io.zdp.api.model.Seed;
import io.zdp.api.model.TransactionHeadersResponse;
import io.zdp.api.model.TransferDetails;
import io.zdp.api.model.TransferDetailsList;
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

	private static final String URL_GET_PUBLIC_KEY = "/api/v1/account/getPublicKey";

	private static final String URL_GET_BALANCE = "/api/v1/account/balance";

	private static final String URL_GET_SEED = "/api/v1/account/seed";

	private static final String URL_GET_ACCOUNT_TRANSACTIONS = "/api/v1/account/transactions";

	private static final String URL_GET_ADDRESS = "/api/v1/account/address/";

	private static final String URL_GET_TXS_BY_TO_ADDRESS_HASH = "/api/v1/tx/to/";

	private static final String URL_GET_TXS_BY_FROM_ADDRESS_HASH = "/api/v1/tx/from/";

	@PostConstruct
	public void init() {

		log.debug("Host url: " + hostUrl);

		restTemplate = new RestTemplate();

	}

	@Override
	public Seed generateSeed() throws Exception {
		URI uri = new URI(hostUrl + URL_GET_SEED);
		return this.restTemplate.getForObject(uri, Seed.class);
	}

	@Override
	public TransferDetails getTransaction(String uuid) throws Exception {
		URI uri = new URI(hostUrl + URL_GET_TX_DETAILS + "/" + uuid);
		return this.restTemplate.getForObject(uri, TransferDetails.class);
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
	public BalanceResponse getAccountBalance(final byte[] publicKeyBytes, final byte[] privateKeyBytes) throws Exception {

		final AccountRequest req = createRequest(publicKeyBytes, privateKeyBytes);

		final URI uri = new URI(hostUrl + URL_GET_BALANCE);

		return restTemplate.postForObject(uri, req, BalanceResponse.class);
	}

	private AccountRequest createRequest(final byte[] publicKeyBytes, final byte[] privateKeyBytes) throws InvalidKeySpecException, NoSuchAlgorithmException, Exception {

		// Generate address from public key
		final String address = Signer.getPublicKeyHash(publicKeyBytes);

		// Sign address
		final PrivateKey pvt = Signer.generatePrivateKey(privateKeyBytes);
		final byte[] signature = Signer.sign(pvt, address);

		final BalanceRequest req = new BalanceRequest();
		req.setPublicKey(publicKeyBytes);
		req.setSignedAddress(signature);

		log.debug("Get balance: " + address);

		return req;
	}

	@Override
	public Key getPublicKey() throws Exception {

		final URI uri = new URI(hostUrl + URL_GET_PUBLIC_KEY);

		Key response = restTemplate.postForObject(uri, null, Key.class);

		return response;
	}

	@Override
	public TransferResponse transfer(byte[] publicKey, byte[] privateKey, String from, String to, BigDecimal amount, String memo) throws Exception {

		URI uri = new URI(hostUrl + URL_TRANSFER);

		log.debug("transfer: " + uri);

		TransferRequest req = new TransferRequest();

		req.setAmount(amount.toString());
		req.setDate(new Date());
		req.setPublicKey(publicKey);
		req.setMemo(memo);
		req.setToAddress(to);
		req.setFromAddress(from);

		PrivateKey priv = Signer.generatePrivateKey(privateKey);

		byte[] signature = Signer.sign(priv, DigestUtils.sha256Hex(from + amount + to));

		req.setSignature(signature);

		return restTemplate.postForObject(uri, req, TransferResponse.class);

	}

	@Override
	public AddressResponse getAddress(byte[] publicKey) throws Exception {
		final URI uri = new URI(hostUrl + URL_GET_ADDRESS);
		AddressResponse response = restTemplate.postForObject(uri, null, AddressResponse.class);
		return response;
	}

	@Override
	public TransferDetailsList getTransactionByToAddress(String addrHash) throws Exception {
		URI uri = new URI(hostUrl + URL_GET_TXS_BY_TO_ADDRESS_HASH + addrHash);
		return this.restTemplate.getForObject(uri, TransferDetailsList.class);
	}

	@Override
	public TransferDetailsList getTransactionByFromAddress(String addrHash) throws Exception {
		URI uri = new URI(hostUrl + URL_GET_TXS_BY_FROM_ADDRESS_HASH + addrHash);
		return this.restTemplate.getForObject(uri, TransferDetailsList.class);

	}

	@Override
	public TransactionHeadersResponse getTransactionHeaders(byte[] publicKey, byte[] privateKey) throws Exception {

		final AccountRequest req = createRequest(publicKey, privateKey);

		final URI uri = new URI(hostUrl + URL_GET_ACCOUNT_TRANSACTIONS);

		return restTemplate.postForObject(uri, req, TransactionHeadersResponse.class);

	}

}
