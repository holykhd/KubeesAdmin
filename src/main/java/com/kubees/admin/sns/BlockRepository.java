package com.kubees.admin.sns;

import com.kubees.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface BlockRepository extends JpaRepository<Block, Long> {

    Block findByFromUserIdAndToUserId(Long fromUserId, Comparable<Long> toUserId);

    void deleteByFromUserIdAndToUserId(Long fromUserId, Long toUserId);
}
