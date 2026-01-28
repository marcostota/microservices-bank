package com.tota.loan.service.impl;

import com.tota.loan.dto.LoansDto;
import com.tota.loan.entity.Loans;
import com.tota.loan.exception.LoanAlreadyExistsException;
import com.tota.loan.exception.ResourceNotFoundException;
import com.tota.loan.mapper.LoansMapper;
import com.tota.loan.repository.LoansRepository;
import com.tota.loan.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {

    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNUmber){
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNUmber);
        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already exists with mobilenumber : " + mobileNUmber);
        }
        loansRepository.save(createNewLoan(mobileNUmber));

    }

    private Loans createNewLoan(String mobileNUmber) {
        Loans loans = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        loans.setLoanNumber(Long.toString(randomLoanNumber));
        loans.setMobileNumber(mobileNUmber);
        loans.setLoanType("Home Loan");
        loans.setTotalLoan(1_00_000);
        loans.setAmountPaid(0);
        loans.setOutstandingAmount(1_00_000);
        return loans;
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByMobileNumber(loansDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", loansDto.getMobileNumber())
        );
        LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }

}
