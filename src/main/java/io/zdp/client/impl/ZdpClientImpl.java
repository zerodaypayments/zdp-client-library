package io.zdp.client.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.security.PrivateKey;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.zdp.api.model.v1.GetBalanceRequest;
import io.zdp.api.model.v1.GetBalanceResponse;
import io.zdp.api.model.v1.GetFeeResponse;
import io.zdp.api.model.v1.GetNewAccountResponse;
import io.zdp.api.model.v1.GetTransactionDetailsResponse;
import io.zdp.api.model.v1.PingResponse;
import io.zdp.api.model.v1.SubmitTransactionRequest;
import io.zdp.api.model.v1.SubmitTransactionResponse;
import io.zdp.api.model.v1.Urls;
import io.zdp.client.ZdpClient;
import io.zdp.crypto.Hashing;
import io.zdp.crypto.Keys;
import io.zdp.crypto.Signing;
import io.zdp.crypto.mnemonics.Mnemonics.Language;

@Component
public class ZdpClientImpl implements ZdpClient {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private RestTemplate restTemplate;

	@Value("${zdp.api.host}")
	private String hostUrl;

	@PostConstruct
	public void init() {

		log.debug("Host url: " + hostUrl);

		restTemplate = new RestTemplate();

	}

	public String getHostUrl() {
		return hostUrl;
	}

	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}

	@Override
	public PingResponse ping() throws Exception {

		URI uri = new URI(hostUrl + Urls.URL_PING);

		log.debug("ping: " + uri);

		return this.restTemplate.getForObject(uri, PingResponse.class);

	}

	@Override
	public GetFeeResponse getFee() throws Exception {

		final URI uri = new URI(hostUrl + Urls.URL_GET_TX_FEE);

		return restTemplate.getForObject(uri, GetFeeResponse.class);

	}

	@Override
	public GetNewAccountResponse getNewAccount(String curve, Language language) throws Exception {

		final URI uri = new URI(hostUrl + Urls.URL_GET_NEW_ACCOUNT);

		return restTemplate.getForObject(uri, GetNewAccountResponse.class);

	}

	@Override
	public GetBalanceResponse getBalance(String privateKeyB58, String curve) throws Exception {

		final URI uri = new URI(hostUrl + Urls.URL_GET_BALANCE);

		BigInteger privKey = Keys.toBigIntegerFromPrivateKeyBase58(privateKeyB58);
		PrivateKey pvt = Keys.getPrivateKeyFromECBigIntAndCurve(privKey, curve);

		final String publicKeyB58 = Keys.toZDPPublicKey(privKey, curve);

		log.debug("getBalance: " + uri);

		final GetBalanceRequest req = new GetBalanceRequest();
		req.setPublicKey(publicKeyB58);

		req.setSignature(Signing.sign(pvt, publicKeyB58));

		final GetBalanceResponse response = restTemplate.postForObject(uri, req, GetBalanceResponse.class);

		return response;

	}

	@Override
	public SubmitTransactionResponse transfer(String privateKeyB58, String curve, String from, String to, BigDecimal amount, String memo) throws Exception {

		URI uri = new URI(hostUrl + Urls.URL_TRANSFER);

		BigInteger privKey = Keys.toBigIntegerFromPrivateKeyBase58(privateKeyB58);
		PrivateKey pvt = Keys.getPrivateKeyFromECBigIntAndCurve(privKey, curve);

		final String publicKeyB58 = Keys.toZDPPublicKey(privKey, curve);

		log.debug("transfer: " + uri);

		SubmitTransactionRequest req = new SubmitTransactionRequest();

		req.setAmount(amount.toPlainString());
		req.setFromAddress(from);
		req.setMemo(memo);
		req.setPublicKey(publicKeyB58);
		req.setRequestUuid(UUID.randomUUID().toString());
		req.setToAddress(to);

		byte[] signature = Hashing.hashTransactionSignature(from + amount.toPlainString() + to);

		req.setSignature(Signing.sign(pvt, signature));

		return restTemplate.postForObject(uri, req, SubmitTransactionResponse.class);
	}

	@Override
	public GetTransactionDetailsResponse getTransactionDetails(String uuid) throws Exception {

		final URI uri = new URI(hostUrl + Urls.URL_GET_TX_DETAILS + uuid);

		final GetTransactionDetailsResponse response = restTemplate.getForObject(uri, GetTransactionDetailsResponse.class);

		return response;
	}

}
