package io.zdp.client;

import java.math.BigDecimal;

import io.zdp.api.model.v1.GetBalanceResponse;
import io.zdp.api.model.v1.GetFeeResponse;
import io.zdp.api.model.v1.GetNewAccountResponse;
import io.zdp.api.model.v1.GetTransactionDetailsResponse;
import io.zdp.api.model.v1.PingResponse;
import io.zdp.api.model.v1.TransferResponse;
import io.zdp.crypto.mnemonics.Mnemonics.Language;

public interface ZdpClient {

	String getHostUrl();
	
	void setHostUrl(String url);
		
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
	GetNewAccountResponse getNewAccount(String curve, Language language) throws Exception;

	/**
	 * Get account balance
	 */
	GetBalanceResponse getBalance(String privateKeyB58, String curve) throws Exception;

	/**
	 * Submit transfer request (synchronous)
	 */
	TransferResponse transfer(String privateKeyB58, String curve, String from, String to, BigDecimal amount, String memo) throws Exception;

	/**
	 * Get transaction details by tx uuid
	 */
	GetTransactionDetailsResponse getTransactionDetails(String uuidOrAddress) throws Exception;

}
