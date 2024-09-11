package com.assignment.gateway.validation;

import com.assignment.gateway.dto.Transaction;
import com.assignment.gateway.exception.BadRequestException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;



public class TransactionValidator implements ConstraintValidator<TransactionValid, Transaction> {

    @Override
    public boolean isValid(Transaction transaction, ConstraintValidatorContext constraintValidatorContext) {

        amountChecks(transaction);

        if(transaction.getAccountId() == null){
            throw new BadRequestException("You have to define the account ID of your transaction");
        }

        if(transaction.getType() == null){
            throw new BadRequestException("You have to define the type of your transaction");
        }

        return true;
    }

    private void amountChecks(Transaction transaction) {
        if(transaction.getAmount() == null){
            throw new BadRequestException("Your have to define an amount for your transaction");
        }
        if(transaction.getAmount() == 0){
            throw new BadRequestException("Your transaction cannot have a value of 0");
        }
        if(transaction.getAmount() < 0){
            throw new BadRequestException("Your transaction cannot have a negative value");
        }
    }


}
