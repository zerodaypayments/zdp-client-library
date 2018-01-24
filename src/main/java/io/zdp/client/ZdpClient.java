package io.zdp.client;

import java.security.PrivateKey;
import java.util.List;
import java.util.Map;

import io.zdp.api.model.AddressDetailsResponse;
import io.zdp.api.model.BalanceResponse;
import io.zdp.api.model.TransactionDetailsRequest;
import io.zdp.api.model.TransactionDetailsResponse;
import io.zdp.api.model.TransactionListRequest;
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
	TransferResponse transfer(PrivateKey privateKey, String from, String to, double amount, String fromRef, String toRef) throws Exception;

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
	TransactionDetailsResponse getTransaction(TransactionDetailsRequest req) throws Exception;

	/**
	 * List transactions by "from/to" address, sort by date
	 */
	List<TransactionDetailsResponse> getTransactions(TransactionListRequest req) throws Exception;

}
