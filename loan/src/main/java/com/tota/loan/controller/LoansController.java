package com.tota.loan.controller;

import com.tota.loan.dto.LoansContactInfoDto;
import com.tota.loan.dto.LoansDto;
import com.tota.loan.dto.ResponseDto;
import com.tota.loan.service.LoanService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api", produces = "application/json")
@Validated
public class LoansController {

    @Value("${build.version}")
    private String buildVersion;

    private final LoanService loanService;

    private final LoansContactInfoDto loansContactInfoDto;

    public LoansController(LoanService loanService, LoansContactInfoDto loansContactInfoDto) {
        this.loanService = loanService;
        this.loansContactInfoDto = loansContactInfoDto;
    }


    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam String mobileNumber){
        loanService.createLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("201", "Loan created successfully"));
    }

    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoan(@RequestParam String mobileNumber){
        LoansDto loansDto = loanService.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoan(@RequestBody LoansDto loansDto){
        boolean isUpdated = loanService.updateLoan(loansDto);
        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("200", "Loan updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("400", "Failed to update loan"));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoan(@RequestParam String mobileNumber){
        boolean isDeleted = loanService.deleteLoan(mobileNumber);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("200", "Loan deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("400", "Failed to delete loan"));
        }
    }

    @GetMapping("/buidinfo")
    public ResponseEntity<String> getBuildInfo(){
        return ResponseEntity.status(HttpStatus.OK).body( buildVersion);
    }

    @GetMapping("/contact-info")
    public ResponseEntity<LoansContactInfoDto> getContactInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(loansContactInfoDto);
    }
}
