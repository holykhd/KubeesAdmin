package com.kubees.admin.login;

import com.kubees.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface LoginRepository extends JpaRepository<Account, Long> {

    boolean existsByEmail(String email);
}
