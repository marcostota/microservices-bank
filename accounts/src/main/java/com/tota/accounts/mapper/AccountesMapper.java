package com.tota.accounts.mapper;

import com.tota.accounts.dto.AccountsDto;
import com.tota.accounts.entity.Accounts;

public class AccountesMapper {

    public static AccountsDto toAccountsDto(Accounts accounts, AccountsDto dto) {
        dto.setAccountNumber(accounts.getAccountNumber());
        dto.setAccountType(accounts.getAccountType());
        dto.setBranchAddress(accounts.getBranchAddress());
        return dto;
    }

    public static Accounts toAccounts(AccountsDto dto, Accounts accounts) {
        accounts.setAccountNumber(dto.getAccountNumber());
        accounts.setAccountType(dto.getAccountType());
        accounts.setBranchAddress(dto.getBranchAddress());
        return accounts;

    }
}
