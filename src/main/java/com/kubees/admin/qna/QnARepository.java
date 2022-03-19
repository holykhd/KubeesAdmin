package com.kubees.admin.qna;

import com.kubees.domain.QnA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface QnARepository extends JpaRepository<QnA, Long> {
}
