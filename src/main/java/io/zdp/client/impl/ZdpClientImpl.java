package io.zdp.client.impl;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

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
import io.zdp.api.model.v1.GetBalanceRequest;
import io.zdp.api.model.v1.GetBalanceResponse;
import io.zdp.api.model.v1.GetFeeResponse;
import io.zdp.api.model.v1.GetNewAccountResponse;
import io.zdp.api.model.v1.GetPublicKeyRequest;
import io.zdp.api.model.v1.GetPublicKeyResponse;
import io.zdp.api.model.v1.GetTransactionDetailsResponse;
import io.zdp.api.model.v1.ListTransactionsRequest;
import io.zdp.api.model.v1.ListTransactionsResponse;
import io.zdp.api.model.v1.PingResponse;
import io.zdp.api.model.v1.SubmitTransactionRequest;
import io.zdp.api.model.v1.SubmitTransactionResponse;
import io.zdp.api.model.v1.Urls;
import io.zdp.client.ZdpClient;
import io.zdp.common.crypto.CryptoUtils;

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

		return this.restTemplate.getForObject(uri, PingResponse.class);

	}

	@Override
	public GetFeeResponse getFee() throws Exception {

		final URI uri = new URI(hostUrl + Urls.URL_GET_TX_FEE);

		return restTemplate.getForObject(uri, GetFeeResponse.class);

	}

	@Override
	public GetNewAccountResponse getNewAccount() throws Exception {

		final URI uri = new URI(hostUrl + Urls.URL_GET_NEW_ACCOUNT);

		return restTemplate.getForObject(uri, GetNewAccountResponse.class);

	}

	@Override
	public GetPublicKeyResponse getPublicKey(String privateKeyB58) throws Exception {

		final URI uri = new URI(hostUrl + Urls.URL_GET_PUBLIC_KEY);

		final GetPublicKeyRequest req = new GetPublicKeyRequest(privateKeyB58);

		GetPublicKeyResponse response = restTemplate.postForObject(uri, req, GetPublicKeyResponse.class);

		return response;

	}

	@Override
	public GetAddressResponse getAddress(String privateKeyB58, String publicKeyB58) throws Exception {

		final URI uri = new URI(hostUrl + Urls.URL_GET_ADDRESS);

		final GetAddressRequest req = new GetAddressRequest();
		req.setPublicKey(publicKeyB58);
		req.setSignature(CryptoUtils.sign(Base58.decode(privateKeyB58), publicKeyB58));

		final GetAddressResponse response = restTemplate.postForObject(uri, null, GetAddressResponse.class);

		return response;
	}

	@Override
	public GetBalanceResponse getBalance(String privateKeyB58, String publicKeyB58) throws Exception {

		final URI uri = new URI(hostUrl + Urls.URL_GET_BALANCE);

		final GetBalanceRequest req = new GetBalanceRequest();
		req.setPublicKey(publicKeyB58);
		req.setSignature(CryptoUtils.sign(Base58.decode(privateKeyB58), publicKeyB58));

		final GetBalanceResponse response = restTemplate.postForObject(uri, null, GetBalanceResponse.class);

		return response;

	}

	@Override
	public SubmitTransactionResponse transfer(String privateKeyB58, String publicKeyB58, String from, String to, BigDecimal amount, String memo) throws Exception {

		URI uri = new URI(hostUrl + Urls.URL_TRANSFER);

		log.debug("transfer: " + uri);

		SubmitTransactionRequest req = new SubmitTransactionRequest();

		req.setAmount(amount.toPlainString());
		req.setFromAddress(from);
		req.setMemo(memo);
		req.setPublicKey(publicKeyB58);
		req.setRequestUuid(UUID.randomUUID().toString());
		req.setToAddress(to);
		req.setSignature(CryptoUtils.sign(Base58.decode(privateKeyB58), publicKeyB58));

		return restTemplate.postForObject(uri, req, SubmitTransactionResponse.class);
	}

	@Override
	public GetTransactionDetailsResponse getTransactionDetails(String uuid) throws Exception {

		final URI uri = new URI(hostUrl + Urls.URL_GET_TX_DETAILS + uuid);

		final GetTransactionDetailsResponse response = restTemplate.getForObject(uri, GetTransactionDetailsResponse.class);

		return response;
	}

	@Override
	public ListTransactionsResponse getTransactions(String privateKeyB58, String publicKeyB58, String fromAddress, String toAddress, String memo, int page, int pageSize) throws Exception {

		URI uri = new URI(hostUrl + Urls.URL_GET_ACCOUNT_TRANSACTIONS);

		log.debug("getTransactions: " + uri);

		ListTransactionsRequest req = new ListTransactionsRequest();

		req.setFrom(fromAddress);
		req.setTo(toAddress);
		req.setMemo(memo);

		req.setPage(page);
		req.setPageSize(pageSize);

		if (publicKeyB58 != null) {
			req.setPublicKey(publicKeyB58);
			req.setSignature(CryptoUtils.sign(Base58.decode(privateKeyB58), publicKeyB58));

		}

		return restTemplate.postForObject(uri, req, ListTransactionsResponse.class);

	}

	@Override
	public CountTransactionsResponse getTransactionsCount(String privateKeyB58, String publicKeyB58, String fromAddress, String toAddress, String memo) throws Exception {

		URI uri = new URI(hostUrl + Urls.URL_COUNT_ACCOUNT_TRANSACTIONS);

		log.debug("Count: " + uri);

		ListTransactionsRequest req = new ListTransactionsRequest();

		req.setFrom(fromAddress);
		req.setTo(toAddress);
		req.setMemo(memo);

		if (publicKeyB58 != null) {
			req.setPublicKey(publicKeyB58);
			req.setSignature(CryptoUtils.sign(Base58.decode(privateKeyB58), publicKeyB58));

		}

		return restTemplate.postForObject(uri, req, CountTransactionsResponse.class);
	}

}
