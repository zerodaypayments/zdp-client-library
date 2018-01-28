package io.zdp.client.impl;

import java.net.URI;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.zdp.api.model.AddressDetailsRequest;
import io.zdp.api.model.AddressDetailsResponse;
import io.zdp.api.model.BalanceRequest;
import io.zdp.api.model.BalanceResponse;
import io.zdp.api.model.FloatValue;
import io.zdp.api.model.TransactionDetailsRequest;
import io.zdp.api.model.TransferDetails;
import io.zdp.api.model.TransactionListRequest;
import io.zdp.api.model.TransferRequest;
import io.zdp.api.model.TransferResponse;
import io.zdp.client.ZdpClient;
import io.zdp.common.crypto.Signer;

@Component
public class ZdpClientImpl implements ZdpClient {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private RestTemplate restTemplate;

	@Value("${zdp.api.host}")
	private String hostUrl;

	private static final String URL_PING = "/ping";
	private static final String URL_ADDRESS = "/api/v1/address";
	private static final String URL_TRANSFER = "/api/v1/transfer";
	private static final String URL_GET_ADDRESS_BALANCE = "/api/v1/address/balance";
	private static final String URL_GET_ADDRESSES_BALANCES = "/api/v1/address/balances";
	private static final String URL_GET_TX_DETAILS = "";
	private static final String URL_GET_TX_FEE = "/api/v1/fee";
	private static final String URL_GET_ADDRESS_TX_LIST = "";

	@PostConstruct
	public void init() {

		log.debug("Host url: " + hostUrl);

		restTemplate = new RestTemplate();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zerodaypayments.client.ZdpClient#getAddress()
	 */
	@Override
	public AddressDetailsResponse getAddress() throws Exception {

		URI uri = new URI(hostUrl + URL_ADDRESS);

		log.debug("Get new address: " + uri);

		return restTemplate.postForObject(uri, new AddressDetailsRequest(), AddressDetailsResponse.class);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zerodaypayments.client.ZdpClient#getAddress(java.lang.String)
	 */
	@Override
	public AddressDetailsResponse getAddress(String secret) throws Exception {

		URI uri = new URI(hostUrl + URL_ADDRESS);

		log.debug("Get new address: " + uri);

		return restTemplate.postForObject(uri, new AddressDetailsRequest(secret), AddressDetailsResponse.class);

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
	public TransferResponse transfer(PrivateKey privateKey, String from, String to, double amount, String fromRef, String toRef) throws Exception {

		URI uri = new URI(hostUrl + URL_TRANSFER);

		log.debug("transfer: " + uri);

		TransferRequest req = new TransferRequest();
		req.setDate(new Date());

		req.setFromAddress(from);
		req.setToAddress(to);

		req.setAmountToSend(amount);

		req.setRecipientReference(toRef);
		req.setSenderReference(fromRef);

		byte[] signature = Signer.sign(privateKey, DigestUtils.sha256Hex(from + to + amount));
		req.setSignature(signature);

		return restTemplate.postForObject(uri, req, TransferResponse.class);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zerodaypayments.client.ZdpClient#getAddressesBalances(java.util.Map)
	 */
	@Override
	public List<BalanceResponse> getAddressesBalances(Map<String, PrivateKey> map) throws Exception {

		URI uri = new URI(hostUrl + URL_GET_ADDRESSES_BALANCES);

		log.debug("getAddressesBalances: " + uri);

		List<BalanceRequest> request = new ArrayList<>();

		for (final String uuid : map.keySet()) {

			final byte[] signature = Signer.sign(map.get(uuid), DigestUtils.sha256Hex(uuid));

			final BalanceRequest br = new BalanceRequest(uuid, signature);

			request.add(br);

		}

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		final HttpEntity<List<BalanceRequest>> requestEntity = new HttpEntity<>(request, headers);

		final ResponseEntity<List<BalanceResponse>> resp = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<BalanceResponse>>() {
		});

		return resp.getBody();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zerodaypayments.client.ZdpClient#getAddressBalance(java.security.
	 * PrivateKey, java.lang.String)
	 */
	@Override
	public BalanceResponse getAddressBalance(PrivateKey privateKey, String uuid) throws Exception {

		URI uri = new URI(hostUrl + URL_GET_ADDRESS_BALANCE);

		log.debug("getAddressBalance: " + uri);

		byte[] signature = Signer.sign(privateKey, DigestUtils.sha256Hex(uuid));

		return restTemplate.postForObject(uri, new BalanceRequest(uuid, signature), BalanceResponse.class);

	}

	@Override
	public TransferDetails getTransaction(TransactionDetailsRequest req) throws Exception {
		return null;
	}

	@Override
	public List<TransferDetails> getTransactions(TransactionListRequest req) throws Exception {
		// TODO Auto-generated method stub
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
	public float getFee() throws Exception {

		final URI uri = new URI(hostUrl + URL_GET_TX_FEE);

		log.debug("Get fee: " + uri);

		return restTemplate.getForObject(uri, FloatValue.class).getValue();

	}

}
