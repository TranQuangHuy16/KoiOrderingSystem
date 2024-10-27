package com.project.KoiOrderingSystem.repository;

import com.project.KoiOrderingSystem.entity.Account;
import com.project.KoiOrderingSystem.entity.Role;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByUsername(String username);

    Account findAccountById(long id);

    Account findAccountByEmail(String email);

    Account findAccountByRole(Role role);

    @Query("select count(a) from Account a where a.role = :role")
    long countCustomerByRole(@Param("role") Role role);
}
