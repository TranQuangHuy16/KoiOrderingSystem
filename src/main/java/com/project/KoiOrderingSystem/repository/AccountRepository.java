package com.project.KoiOrderingSystem.repository;

import com.project.KoiOrderingSystem.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findAccountByUsername(String username);
}
