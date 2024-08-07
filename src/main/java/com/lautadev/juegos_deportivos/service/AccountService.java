package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.model.Account;
import com.lautadev.juegos_deportivos.model.Role;
import com.lautadev.juegos_deportivos.repository.IAccountRepository;
import com.lautadev.juegos_deportivos.util.NullAwareBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountService implements IAccountService{
    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IRoleService roleService;

    @Override
    public Account saveAccount(Account account) {
        Set<Role> roleList = new HashSet<>();

        //userSec.setPassword(this.encriptPassword(userSec.getPassword()));

        for(Role role: account.getRoleList()){
            Role readRole = roleService.findRole(role.getId()).orElse(null);
            if(readRole!=null){
                roleList.add(readRole);
            }
        }

        if(!roleList.isEmpty()){
            account.setRoleList(roleList);
            return accountRepository.save(account);
        }

        return account;
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> findAccount(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account editAccount(Long id,Account account) {
        Account accountEdit = this.findAccount(id).orElse(null);

        NullAwareBeanUtils.copyNonNullProperties(account,accountEdit);

        assert accountEdit != null;
        return this.saveAccount(accountEdit);
    }
}
