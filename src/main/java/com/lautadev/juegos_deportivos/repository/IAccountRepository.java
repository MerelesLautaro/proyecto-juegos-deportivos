package com.lautadev.juegos_deportivos.repository;

import com.lautadev.juegos_deportivos.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository  extends JpaRepository<Account,Long> {
}
