package com.assignment.gateway.controller;

import com.assignment.gateway.dto.Account;
import com.assignment.gateway.dto.CreateAccountRequest;
import com.assignment.gateway.dto.Customer;
import com.assignment.gateway.dto.Transaction;
import com.assignment.gateway.service.GatewayService;
import com.assignment.gateway.validation.TransactionValid;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling requests related to accounts, customers, and transactions.
 */
@RestController
@RequestMapping("${application.basepath}")
@CrossOrigin(origins = "*")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    /**
     * Creates a new account.
     *
     * @param createAccountRequest the request containing account details
     * @return the created Account
     */
    @PostMapping("/create-account")
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        return new ResponseEntity<>(gatewayService.createAccount(createAccountRequest), HttpStatus.OK);
    }

    /**
     * Retrieves customer information by ID.
     *
     * @param customerId the ID of the customer
     * @return the Customer details
     */
    @GetMapping("/customer-info")
    public ResponseEntity<Customer> getCustomer(@RequestParam(name = "customer-id") Integer customerId) {
        return new ResponseEntity<>(gatewayService.getCustomerById(customerId), HttpStatus.OK);
    }

    /**
     * Creates a new transaction.
     *
     * @param transaction the transaction details
     * @return the list of transactions including the new one
     */
    @PostMapping("new-transaction")
    public ResponseEntity<List<Transaction>> newTransaction(@RequestBody @TransactionValid @Valid Transaction transaction) {
        return new ResponseEntity<>(gatewayService.addTransaction(transaction), HttpStatus.OK);
    }
}
