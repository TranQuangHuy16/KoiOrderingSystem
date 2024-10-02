package com.project.KoiOrderingSystem.repository;

import com.project.KoiOrderingSystem.entity.Account;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByUsername(String username);

    Account findAccountById(long id);

    Account findAccountByEmail(String email);
}
