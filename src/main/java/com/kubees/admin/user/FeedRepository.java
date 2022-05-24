package com.kubees.admin.user;

import com.kubees.domain.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<Feed> findByUserEmailOrderByIdDesc(String email);

    List<Feed> findByUserEmailAndFeedStatusOrderByIdDesc(String email, boolean feedStatus);
}
