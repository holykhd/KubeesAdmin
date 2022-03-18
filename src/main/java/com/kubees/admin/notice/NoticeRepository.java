package com.kubees.admin.notice;

import com.kubees.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
