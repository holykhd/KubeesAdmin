package com.kubees.admin.partners;

import com.kubees.domain.Partners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface PartnersRepository extends JpaRepository<Partners, Long> {
    boolean existsByPartnerId(String partnerId);

    Partners findByPartnerId(String partnerId);
}
