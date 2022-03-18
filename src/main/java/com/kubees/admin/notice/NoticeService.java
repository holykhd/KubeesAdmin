package com.kubees.admin.notice;

import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.admin.notice.form.NoticeForm;
import com.kubees.domain.Notice;
import com.kubees.domain.QAttach;
import com.kubees.domain.QNotice;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static com.kubees.domain.QAttach.attach;
import static com.kubees.domain.QNotice.notice;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    @PersistenceContext
    EntityManager em;
    JPAQueryFactory jpaQueryFactory;


    private final NoticeRepository noticeRepository;
    public void createNoticeProcessor(NoticeForm noticeForm, PrincipalDetails principalDetails) {
        log.info("noticeForm ={}", noticeForm);
        Notice notice = new Notice();
        notice.setTitle(noticeForm.getTitle());
        notice.setContents(noticeForm.getContents());
        notice.setCreatedId(principalDetails.getAccount().getUserId());

        noticeRepository.save(notice);


        // 글 등록 (즉시 등록, 예약등록기능)
        // 글 바로 등록하기

        // 첨부파일 등록
    }

    public List<Notice> getNoticeProcessor() {
        return noticeRepository.findAll();

    }

    public Notice getNoticeDetailProcessor(Long id) {
        jpaQueryFactory = new JPAQueryFactory(em);
        return null;
    }
}
