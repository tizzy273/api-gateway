package com.assignent.gateway.controller;

import com.assignent.gateway.dto.Account;
import com.assignent.gateway.dto.CreateAccountRequest;
import com.assignent.gateway.dto.Customer;
import com.assignent.gateway.dto.Transaction;
import com.assignent.gateway.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${application.basepath}")
@CrossOrigin(origins = "*")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    @PostMapping("/create-account")
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest createAccountRequest){
        return new ResponseEntity<>(gatewayService.createAccount(createAccountRequest), HttpStatus.OK);
    }

    @GetMapping("/customer-info")
    public ResponseEntity<Customer> getCustomer(@RequestParam(name = "customer-id")Integer customerId){
        return new ResponseEntity<>(gatewayService.getCustomerById(customerId), HttpStatus.OK);
    }

    @PostMapping("new-transaction")
        public ResponseEntity<List<Transaction>> newTransaction(@RequestBody Transaction transaction){
        return new ResponseEntity<>(gatewayService.addTransaction(transaction), HttpStatus.OK);
    }


    @GetMapping("/customer-accounts")
    public ResponseEntity<List<Account>> getAccountsByCustomerId(@RequestParam(name = "customer-id")Integer customerId){
        return new ResponseEntity<>(gatewayService.getAccountsByCustomerId(customerId), HttpStatus.OK);
    }





}
