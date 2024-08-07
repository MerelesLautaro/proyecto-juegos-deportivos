package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.model.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    public Account saveAccount(Account account);
    public List<Account> getAccounts();
    public Optional<Account> findAccount(Long id);
    public void deleteAccount(Long id);
    public Account editAccount(Long id,Account account);
    //public String encriptPassword(String password);
}
