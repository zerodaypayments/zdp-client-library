package io.zdp.client;

import java.math.BigDecimal;

import io.zdp.api.model.AddressResponse;
import io.zdp.api.model.BalanceResponse;
import io.zdp.api.model.FeeResponse;
import io.zdp.api.model.Key;
import io.zdp.api.model.PingResponse;
import io.zdp.api.model.SecretKey;
import io.zdp.api.model.TransactionHeadersResponse;
import io.zdp.api.model.TransferDetails;
import io.zdp.api.model.TransferDetailsList;
import io.zdp.api.model.TransferResponse;

public interface ZdpClient {

	/**
	 * Generate seed (for new accounts)
	 */
	SecretKey generateSeed() throws Exception;
	
	/**
	 * Ping API
	 */
	PingResponse ping() throws Exception;

	/**
	 * Get Transaction fee from server
	 */
	FeeResponse getFee() throws Exception;

	/**
	 * Submit transfer request
	 */
	TransferResponse transfer(byte[] publicKey, byte[] privateKey, String from, String to, BigDecimal amount, String memo) throws Exception;

	/**
	 * Get account balance
	 */
	BalanceResponse getAccountBalance(byte[] publicKey, byte[] privateKey) throws Exception;

	/**
	 * Get transaction details by tx uuid
	 */
	TransferDetails getTransaction(String uuid) throws Exception;

	/**
	 * Get transaction details by FROM address hash
	 */
	TransferDetailsList getTransactionByFromAddress(String addrHash) throws Exception;

	/**
	 * Get transaction details by TO address hash
	 */
	TransferDetailsList getTransactionByToAddress(String addrHash) throws Exception;

	/**
	 * Get transaction details by account 
	 */
	TransactionHeadersResponse getTransactionHeaders(byte[] publicKey, byte[] privateKey) throws Exception;

	/**
	 * Get public key for address generation
	 */
	Key getPublicKey() throws Exception;

	/**
	 * Get address
	 */
	AddressResponse getAddress(byte[] publicKey) throws Exception;
	
}
