package io.zdp.client;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import io.zdp.api.model.BalanceResponse;
import io.zdp.api.model.BalancesResponse;
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
	BigDecimal getFee() throws Exception;

	/**
	 * Submit transfer request
	 */
	TransferResponse transfer(byte[] publicKey, byte[] privateKey, String to, BigDecimal amount, String fromRef, String toRef) throws Exception;

	/**
	 * Get address balance
	 */
	BalanceResponse getAddressBalance(byte[] publicKey, byte[] privateKey) throws Exception;

	/**
	 * Get addresses balance
	 */
	BalancesResponse getAddressesBalances(List<Pair<byte[], byte[]>> keyPairs) throws Exception;

	/**
	 * Get transaction details by tx uuid
	 */
	TransferDetails getTransaction(String uuid) throws Exception;

}
