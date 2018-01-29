package io.zdp.client;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.util.List;
import java.util.Map;

import io.zdp.api.model.AddressDetailsResponse;
import io.zdp.api.model.BalanceResponse;
import io.zdp.api.model.TransactionListRequest;
import io.zdp.api.model.TransferDetails;
import io.zdp.api.model.TransferResponse;

public interface ZdpClient {

	/**
	 * Ping API
	 */
	long ping() throws Exception;

	/**
	 * Get Transaction fee from server
	 */
	float getFee() throws Exception;

	/**
	 * Create new address
	 */
	AddressDetailsResponse getAddress() throws Exception;

	/**
	 * Get address details by secret key
	 */
	AddressDetailsResponse getAddress(String secret) throws Exception;

	/**
	 * Submit transfer request
	 */
	TransferResponse transfer(PrivateKey privateKey, String from, String to, BigDecimal amount, String fromRef, String toRef) throws Exception;

	/**
	 * Get address balance
	 */
	BalanceResponse getAddressBalance(PrivateKey privateKey, String uuid) throws Exception;

	/**
	 * Get addresses balance
	 */
	List<BalanceResponse> getAddressesBalances(Map<String, PrivateKey> map) throws Exception;

	/**
	 * Get transaction details by tx uuid
	 */
	TransferDetails getTransaction(String uuid) throws Exception;

	/**
	 * List transactions by "from/to" address, sort by date
	 */
	List<TransferDetails> getTransactions(TransactionListRequest req) throws Exception;

}
