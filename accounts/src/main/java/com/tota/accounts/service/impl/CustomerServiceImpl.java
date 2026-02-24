package com.tota.accounts.service.impl;

import com.tota.accounts.dto.AccountsDto;
import com.tota.accounts.dto.CardsDto;
import com.tota.accounts.dto.CustomerDetailsDto;
import com.tota.accounts.dto.LoansDto;
import com.tota.accounts.entity.Accounts;
import com.tota.accounts.entity.Customer;
import com.tota.accounts.exception.ResourceNotFoundException;
import com.tota.accounts.mapper.AccountesMapper;
import com.tota.accounts.mapper.CustomerMapper;
import com.tota.accounts.repository.AccountsRepository;
import com.tota.accounts.repository.CustomerRepository;
import com.tota.accounts.service.ICustomerService;
import com.tota.accounts.service.client.CardsFeignClient;
import com.tota.accounts.service.client.LoansFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl  implements ICustomerService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;

    public CustomerServiceImpl(AccountsRepository accountsRepository, CustomerRepository customerRepository,
                                CardsFeignClient cardsFeignClient, LoansFeignClient loansFeignClient) {
        this.accountsRepository = accountsRepository;
        this.customerRepository = customerRepository;
        this.cardsFeignClient = cardsFeignClient;
        this.loansFeignClient = loansFeignClient;
    }


    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountesMapper.toAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoan(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoReponseEntity = cardsFeignClient.fetchCard(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoReponseEntity.getBody());

        return customerDetailsDto;
    }
}
