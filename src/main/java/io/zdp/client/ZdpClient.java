package io.zdp.client;

import java.math.BigDecimal;

import io.zdp.api.model.v1.CountResponse;
import io.zdp.api.model.v1.GetAddressResponse;
import io.zdp.api.model.v1.GetBalanceResponse;
import io.zdp.api.model.v1.GetFeeResponse;
import io.zdp.api.model.v1.GetNewAccountResponse;
import io.zdp.api.model.v1.GetPublicKeyResponse;
import io.zdp.api.model.v1.GetTransactionDetailsResponse;
import io.zdp.api.model.v1.ListTransactionsResponse;
import io.zdp.api.model.v1.PingResponse;
import io.zdp.api.model.v1.SubmitTransactionResponse;
import io.zdp.api.model.v1.ledger.ListAllTransactionsRequest;
import io.zdp.api.model.v1.ledger.ListAccountsRequest;
import io.zdp.api.model.v1.ledger.ListAccountsResponse;

public interface ZdpClient {

	/**
	 * Ping network
	 */
	PingResponse ping() throws Exception;

	/**
	 * Get current transaction fee from network
	 */
	GetFeeResponse getFee() throws Exception;

	/**
	 * Get new account private/public keys. The account doesn't exist on the network
	 * but its information can be used to send transfers to.
	 */
	GetNewAccountResponse getNewAccount() throws Exception;

	/**
	 * Get public key for a private key
	 */
	GetPublicKeyResponse getPublicKey(String privateKeyB58) throws Exception;

	/**
	 * Get unique address for an account
	 */
	GetAddressResponse getAddress(String privateKeyB58) throws Exception;

	/**
	 * Get account balance
	 */
	GetBalanceResponse getBalance(String privateKeyB58) throws Exception;

	/**
	 * Submit transfer request (synchronous)
	 */
	SubmitTransactionResponse transfer(String privateKeyB58, String from, String to,
			BigDecimal amount, String memo) throws Exception;

	/**
	 * Get transaction details by tx uuid
	 */
	GetTransactionDetailsResponse getTransactionDetails(String uuidOrAddress) throws Exception;

	/**
	 * Get transaction headers
	 */
	ListTransactionsResponse getTransactions(String privateKeyB58, int page, int pageSize)
			throws Exception;

	/**
	 * Count transaction headers
	 */
	CountResponse getTransactionsCount(String privateKeyB58) throws Exception;

	/**
	 * Public ledger: count accounts
	 */
	long countAccounts() throws Exception;

	/**
	 * Public ledger: count all transactions
	 */
	long countTransactions() throws Exception;

	/**
	 * Public ledger: list accounts (sorted by largest balance desc)
	 */
	ListAccountsResponse listAccounts(ListAccountsRequest req) throws Exception;

	/**
	 * Public ledger: list all transactions (sorted by date desc)
	 */
	ListTransactionsResponse listTransactions(ListAllTransactionsRequest req) throws Exception;

}
