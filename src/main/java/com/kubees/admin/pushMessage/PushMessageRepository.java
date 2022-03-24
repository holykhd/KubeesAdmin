package com.kubees.admin.pushMessage;

import com.kubees.domain.PushMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface PushMessageRepository extends JpaRepository<PushMessage, Long> {
}
