package com.kubees.admin.user;

import com.kubees.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);
}
