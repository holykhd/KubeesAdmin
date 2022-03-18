package com.kubees.admin.account;

import com.kubees.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);

    // 파트너 신규 가입시 회원 테이블에 회원이 등록되어있는지 확인
    boolean existsByUserId(String partnerId);

    Account findByUserId(String partnerId);
}
