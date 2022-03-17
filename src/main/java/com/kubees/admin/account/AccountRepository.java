package com.kubees.admin.account;

import com.kubees.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);

}
