package io.zdp.client.impl;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.zdp.api.model.v1.GetBalanceRequest;
import io.zdp.api.model.v1.GetBalanceResponse;
import io.zdp.api.model.v1.GetFeeResponse;
import io.zdp.api.model.v1.GetNewAccountResponse;
import io.zdp.api.model.v1.GetTransactionDetailsResponse;
import io.zdp.api.model.v1.PingResponse;
import io.zdp.api.model.v1.TransferRequest;
import io.zdp.api.model.v1.TransferResponse;
import io.zdp.api.model.v1.Urls;
import io.zdp.client.ZdpClient;
import io.zdp.crypto.key.ZDPKeyPair;
import io.zdp.crypto.mnemonics.Mnemonics.Language;
import io.zdp.model.network.NetworkNode;

@Component
public class ZdpClientImpl implements ZdpClient {

	private final Logger log = LoggerFactory.getLogger( this.getClass() );

	private RestTemplate restTemplate;

	private NetworkNode networkNode;

	@PostConstruct
	public void init ( ) {

		restTemplate = new RestTemplate();

	}

	public String getHostUrl ( ) {
		if ( networkNode == null ) {
			log.error( "Network node is not set up yet" );
			throw new RuntimeException( "Network node is not set up yet" );
		}
		return networkNode.getHttpBaseUrl();
	}

	@Override
	public PingResponse ping ( ) throws Exception {

		URI uri = new URI( getHostUrl() + Urls.URL_PING );

		log.trace( "ping: " + uri );

		return this.restTemplate.getForObject( uri, PingResponse.class );

	}

	@Override
	public GetFeeResponse getFee ( ) throws Exception {

		final URI uri = new URI( getHostUrl() + Urls.URL_GET_TX_FEE );

		return restTemplate.getForObject( uri, GetFeeResponse.class );

	}

	@Override
	public GetNewAccountResponse getNewAccount ( String curve, Language language ) throws Exception {

		final URI uri = new URI( getHostUrl() + Urls.URL_GET_NEW_ACCOUNT );

		return restTemplate.getForObject( uri, GetNewAccountResponse.class );

	}

	@Override
	public GetBalanceResponse getBalance ( String privateKeyB58, String curve ) throws Exception {

		final URI uri = new URI( getHostUrl() + Urls.URL_GET_BALANCE );

		ZDPKeyPair kp = ZDPKeyPair.createFromPrivateKeyBase58( privateKeyB58, curve );

		log.debug( "getBalance: " + uri );

		final GetBalanceRequest req = new GetBalanceRequest();
		req.setAccountUuid( kp.getZDPAccount().getUuid() );

		final GetBalanceResponse response = restTemplate.postForObject( uri, req, GetBalanceResponse.class );

		return response;

	}

	@Override
	public TransferResponse transfer ( String privateKeyB58, String curve, String from, String to, BigDecimal amount, String memo ) throws Exception {

		URI uri = new URI( getHostUrl() + Urls.URL_TRANSFER );

		ZDPKeyPair kp = ZDPKeyPair.createFromPrivateKeyBase58( privateKeyB58, curve );

		log.debug( "transfer: " + uri );

		TransferRequest req = new TransferRequest();

		req.setAmount( amount.toPlainString() );
		req.setFrom( from );
		req.setMemo( memo );
		req.setRequestUuid( UUID.randomUUID().toString() );
		req.setPublicKey( kp.getPublicKeyAsBase58() );
		req.setTo( to );

		req.setSignature( kp.sign( req.getUniqueTransactionUuid() ) );

		return restTemplate.postForObject( uri, req, TransferResponse.class );
	}

	@Override
	public GetTransactionDetailsResponse getTransactionDetails ( String uuid ) throws Exception {

		final URI uri = new URI( getHostUrl() + Urls.URL_GET_TX_DETAILS + uuid );

		final GetTransactionDetailsResponse response = restTemplate.getForObject( uri, GetTransactionDetailsResponse.class );

		return response;
	}

	@Override
	public void setNetworkNode ( NetworkNode node ) {
		this.networkNode = node;
	}

}
