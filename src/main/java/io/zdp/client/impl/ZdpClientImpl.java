package io.zdp.client.impl;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;

import org.bitcoinj.core.Base58;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.zdp.api.model.v1.CountTransactionsResponse;
import io.zdp.api.model.v1.GetAddressRequest;
import io.zdp.api.model.v1.GetAddressResponse;
import io.zdp.api.model.v1.GetBalanceResponse;
import io.zdp.api.model.v1.GetFeeResponse;
import io.zdp.api.model.v1.GetNewAccountResponse;
import io.zdp.api.model.v1.GetPublicKeyRequest;
import io.zdp.api.model.v1.GetPublicKeyResponse;
import io.zdp.api.model.v1.GetTransactionDetailsResponse;
import io.zdp.api.model.v1.ListTransactionsResponse;
import io.zdp.api.model.v1.PingResponse;
import io.zdp.api.model.v1.SubmitTransactionResponse;
import io.zdp.client.ZdpClient;
import io.zdp.common.crypto.CryptoUtils;

@Component
public class ZdpClientImpl implements ZdpClient {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private RestTemplate restTemplate;

	@Value("${zdp.api.host}")
	private String hostUrl;

	private static final String URL_PING = "/ping";

	private static final String URL_GET_TX_FEE = "/api/v1/fee";

	private static final String URL_GET_NEW_ACCOUNT = "/api/v1/account/new";

	private static final String URL_GET_PUBLIC_KEY = "/api/v1/account/publicKey";

	private static final String URL_GET_ADDRESS = "/api/v1/account/address/";

	private static final String URL_GET_BALANCE = "/api/v1/account/balance";

	private static final String URL_TRANSFER = "/api/v1/transfer";

	private static final String URL_GET_TX_DETAILS = "/api/v1/tx";

	private static final String URL_GET_ACCOUNT_TRANSACTIONS = "/api/v1/account/get/transactions";

	private static final String URL_COUNT_ACCOUNT_TRANSACTIONS = "/api/v1/account/count/transactions";

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

		URI uri = new URI(hostUrl + URL_PING);

		return this.restTemplate.getForObject(uri, PingResponse.class);

	}

	@Override
	public GetFeeResponse getFee() throws Exception {

		final URI uri = new URI(hostUrl + URL_GET_TX_FEE);

		return restTemplate.getForObject(uri, GetFeeResponse.class);

	}

	@Override
	public GetNewAccountResponse getNewAccount() throws Exception {

		final URI uri = new URI(hostUrl + URL_GET_NEW_ACCOUNT);

		return restTemplate.getForObject(uri, GetNewAccountResponse.class);

	}

	@Override
	public GetPublicKeyResponse getPublicKey(String privateKeyB58) throws Exception {

		final URI uri = new URI(hostUrl + URL_GET_PUBLIC_KEY);

		final GetPublicKeyRequest req = new GetPublicKeyRequest(privateKeyB58);

		GetPublicKeyResponse response = restTemplate.postForObject(uri, req, GetPublicKeyResponse.class);

		return response;

	}

	@Override
	public GetAddressResponse getAddress(String privateKeyB58, String publicKeyB58)  throws Exception {
		
		final URI uri = new URI(hostUrl + URL_GET_ADDRESS);
		
		final GetAddressRequest req = new GetAddressRequest();
		req.setPublicKey(publicKeyB58);
		req.setSignature(CryptoUtils.sign(Base58.decode(privateKeyB58), publicKeyB58));
		
		final GetAddressResponse response = restTemplate.postForObject(uri, null, GetAddressResponse.class);
		
		return response;		
	}

	@Override
	public GetBalanceResponse getBalance(String privateKeyB58, String publicKeyB58) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubmitTransactionResponse transfer(String privateKeyB58, String publicKeyB58, String from, String to, BigDecimal amount, String memo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetTransactionDetailsResponse getTransactionDetails(String uuid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListTransactionsResponse getTransactions(String publicKeyB58, String fromAddress, String toAddress, String memo, int page, int pageSize) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CountTransactionsResponse getTransactionsCount(String publicKeyB58, String fromAddress, String toAddress, String memo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
