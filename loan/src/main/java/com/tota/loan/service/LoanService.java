package com.tota.loan.service;

import com.tota.loan.dto.LoansDto;

public interface LoanService {

    void createLoan(String mobileNumber);
    LoansDto fetchLoan(String mobileNumber);
    boolean updateLoan(LoansDto loansDto);
    boolean deleteLoan(String mobileNumber);
}
