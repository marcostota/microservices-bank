package com.tota.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {

    @NotEmpty(message = "Account number cannot be empty")
    @Pattern(regexp = "^[0-9]{12}$", message = "Account number must be 12 digits")
    private Long accountNumber;
    private String accountType;
    private String branchAddress;
}
