package com.infowaybr.infowaybank.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.infowaybr.infowaybank.models.BankAccount;
import com.infowaybr.infowaybank.models.Deposit;
import com.infowaybr.infowaybank.models.Transaction;
import com.infowaybr.infowaybank.models.Transfer;
import com.infowaybr.infowaybank.models.Withdrawal;
import com.infowaybr.infowaybank.payloads.TransferRequest;
import com.infowaybr.infowaybank.repositories.BankAccountRepository;
import com.infowaybr.infowaybank.repositories.DepositRepository;
import com.infowaybr.infowaybank.repositories.TransactionRepository;
import com.infowaybr.infowaybank.repositories.TransferRepository;
import com.infowaybr.infowaybank.repositories.WithdrawalRepository;
import com.infowaybr.infowaybank.security.CustomUserDetails;

@RestController
@RequestMapping("/api/bank-accounts")
public class BankAccountResource {

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Autowired
	private DepositRepository depositRepository;

	@Autowired
	private WithdrawalRepository withdrawalRepository;

	@Autowired
	private TransferRepository transferRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public BankAccount get() {
		Optional<BankAccount> bankAccountOptional = getBankAccountLogged();

		if (bankAccountOptional.isPresent()) {
			return bankAccountOptional.get();
			
		}
		throw new ResponseStatusException(HttpStatus.FORBIDDEN);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BankAccount create(@Valid @RequestBody BankAccount bankAccount) {
		return bankAccountRepository.save(bankAccount);
	}

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public BankAccount update(@Valid @RequestBody BankAccount bankAccount) {
		Optional<BankAccount> bankAccountOptional = getBankAccountLogged();

		if (bankAccountOptional.isPresent()) {
			bankAccount.setId(bankAccountOptional.get().getId());
			return bankAccountRepository.save(bankAccount);
			
		}
		throw new ResponseStatusException(HttpStatus.FORBIDDEN);
	}

	@PostMapping("/deposits")
	@ResponseStatus(HttpStatus.CREATED)
	public Deposit deposit(@Valid @RequestBody Deposit deposit) {
		Optional<BankAccount> bankAccountOptional = getBankAccountLogged();

		if (bankAccountOptional.isPresent()) {
			deposit.setBankAccount(bankAccountOptional.get());
			return depositRepository.save(deposit);
		}
		throw new ResponseStatusException(HttpStatus.FORBIDDEN);
	}

	@PostMapping("/withdraws")
	@ResponseStatus(HttpStatus.CREATED)
	public Withdrawal withdraw(@Valid @RequestBody Withdrawal withdrawal) {
		Optional<BankAccount> bankAccountOptional = getBankAccountLogged();

		if (bankAccountOptional.isPresent()) {
			BankAccount bankAccount = bankAccountOptional.get();
			if (bankAccount.getBalance() <  withdrawal.getValue()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo Insuficiente");
			}
			withdrawal.setBankAccount(bankAccount);
			return withdrawalRepository.save(withdrawal);
		}
		throw new ResponseStatusException(HttpStatus.FORBIDDEN);
	}

	@GetMapping("/transactions")
	@ResponseStatus(HttpStatus.OK)
	public List<Transaction> transactions() {
		Optional<BankAccount> bankAccountOptional = getBankAccountLogged();

		if (bankAccountOptional.isPresent()) {
			return transactionRepository.findByBankAccountOrderByCreatedDesc(bankAccountOptional.get());
		}
		throw new ResponseStatusException(HttpStatus.FORBIDDEN);
	}

	@PostMapping("/transfers")
	@ResponseStatus(HttpStatus.OK)
	public Transfer tranfer(@Valid @RequestBody TransferRequest transferRequest) {
		Optional<BankAccount> fromBankAccountOptional = getBankAccountLogged();

		if (fromBankAccountOptional.isPresent()) {
			BankAccount fromBankAccount = fromBankAccountOptional.get();
			
			if (fromBankAccount.getBalance() <  transferRequest.getValue()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo Insuficiente");
			}

			Optional<BankAccount> toBankAccountOptionalOptional = bankAccountRepository.findByUsername(transferRequest.getUsername());
			if (toBankAccountOptionalOptional.isPresent()) {
				List<Transaction> transactions = new ArrayList<Transaction>();
				
				Transaction fromTransaction = new Transaction("Transferência");
				fromTransaction.setValue(transferRequest.getValue() * -1);
				fromTransaction.setBankAccount(fromBankAccount);

				Transaction toTransaction = new Transaction("Transferência");
				toTransaction.setValue(transferRequest.getValue());
				toTransaction.setBankAccount(toBankAccountOptionalOptional.get());

				transactions.add(transactionRepository.save(fromTransaction));
				transactions.add(transactionRepository.save(toTransaction));

				Transfer transfer = new Transfer();

				transfer.setFrom(fromTransaction);
				transfer.setTo(toTransaction);

				return transferRepository.save(transfer);
			}
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Conta bancária de destino não existe.");
		}
		throw new ResponseStatusException(HttpStatus.FORBIDDEN);
	}

	private Optional<BankAccount> getBankAccountLogged() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = ((CustomUserDetails) auth.getPrincipal()).getUsername();
		return bankAccountRepository.findByUsername(username);
	}
}
