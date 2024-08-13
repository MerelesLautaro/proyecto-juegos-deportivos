package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.model.Account;
import com.lautadev.juegos_deportivos.model.Enroller;
import com.lautadev.juegos_deportivos.model.Role;
import com.lautadev.juegos_deportivos.repository.IAccountRepository;
import com.lautadev.juegos_deportivos.repository.IEnrollerRepository;
import com.lautadev.juegos_deportivos.throwable.EntityNotFoundException;
import com.lautadev.juegos_deportivos.util.NullAwareBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private IEnrollerRepository enrollerRepository;

    @Autowired
    private IUserDetailsService userDetailsService;

    @Override
    public Account saveAccount(Account account) {
        Set<Role> roleList = new HashSet<>();

        account.setPassword(this.encriptPassword(account.getPassword()));

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
        Account account = this.findAccount(id).orElseThrow(() -> new EntityNotFoundException("Entity not found")) ;

        Long enrollerId = userDetailsService.getCurrentEnrollerId();
        boolean isAdmin = userDetailsService.hasRoleAdmin();
        Enroller enroller = enrollerRepository.findById(enrollerId).orElseThrow(() -> new EntityNotFoundException("Entity not found")) ;

        if(!isAdmin && !account.getId().equals(enroller.getAccount().getId())){
            throw new AccessDeniedException("You are not authorized to delete this account");
        }

        accountRepository.deleteById(id);
    }

    @Override
    public Account editAccount(Long id,Account account) {
        Account accountEdit = this.findAccount(id).orElseThrow(() -> new EntityNotFoundException("Entity not found")) ;

        Long enrollerId = userDetailsService.getCurrentEnrollerId();
        boolean isAdmin = userDetailsService.hasRoleAdmin();
        Enroller enroller = enrollerRepository.findById(enrollerId).orElseThrow(() -> new EntityNotFoundException("Entity not found")) ;

        if(!isAdmin && !accountEdit.getId().equals(enroller.getAccount().getId())){
            throw new AccessDeniedException("You are not authorized to edit this account");
        }

        NullAwareBeanUtils.copyNonNullProperties(account,accountEdit);

        return this.saveAccount(accountEdit);
    }

    @Override
    public String encriptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
