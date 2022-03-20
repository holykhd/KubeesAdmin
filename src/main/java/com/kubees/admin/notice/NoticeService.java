package com.kubees.admin.notice;

import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.admin.notice.form.NoticeForm;
import com.kubees.admin.notice.form.SearchForm;
import com.kubees.domain.Notice;
import com.kubees.domain.QNotice;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.kubees.domain.QNotice.notice;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    @PersistenceContext
    EntityManager em;
    JPAQueryFactory queryFactory;


    private final NoticeRepository noticeRepository;
    public void createNoticeProcessor(NoticeForm noticeForm, PrincipalDetails principalDetails) {
        Notice notice = new Notice();
        notice.setTitle(noticeForm.getTitle());
        notice.setContents(noticeForm.getContents());
        notice.setCreatedId(principalDetails.getAccount().getUserId());

        noticeRepository.save(notice);

        // TODO LIST 글 등록 (즉시 등록, 예약등록기능)
        // 글 바로 등록하기
    }

    public Page<Notice> getNoticeProcessor(SearchForm searchForm, Pageable pageable) {
        queryFactory = new JPAQueryFactory(em);

        QueryResults<Notice> resultList = queryFactory.selectFrom(notice)
                .where(searchTypeContain(searchForm.getSearchType(), searchForm.getKeyword()))
                .offset(pageable.getOffset())
                .orderBy(notice.id.desc())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Notice> noticeList = resultList.getResults();
        long total = resultList.getTotal();
        return new PageImpl<>(noticeList, pageable, total);

    }

    private BooleanExpression searchTypeContain(String searchTypeCon, String keywordCond) {
        BooleanExpression booleanExpression = null;
        keywordCond = keywordCond != null ? keywordCond : null;

        if (searchTypeCon != null && searchTypeCon.equals("title")) {
            booleanExpression = searchTypeCon != null ? notice.title.contains(keywordCond) : null;
        }
        if (searchTypeCon != null && searchTypeCon.equals("contents")) {
            booleanExpression = searchTypeCon != null ? notice.contents.contains(keywordCond) : null;
        }
        return booleanExpression;
    }

    public Notice getNoticeDetailProcessor(Long id) {
        return noticeRepository.findById(id).orElse(null);
    }

    public NoticeForm findById(Long id) {
        Notice notice = noticeRepository.findById(id).orElse(null);

        return noticeFormSettingToNotice(notice);
    }

    private NoticeForm noticeFormSettingToNotice(Notice notice) {
        NoticeForm noticeForm = new NoticeForm();

        noticeForm.setId(notice.getId());
        noticeForm.setTitle(notice.getTitle());
        noticeForm.setContents(notice.getContents());
        noticeForm.setPublishTime(notice.getPublishTime());
        noticeForm.setPublishDate(notice.getPublishDate());
        noticeForm.setPublishHour(notice.getPublishHour());
        noticeForm.setPublishMinutes(notice.getPublishMinutes());
        noticeForm.setCreatedId(notice.getCreatedId());
        return noticeForm;
    }

    /**
     * 공지사항 수정하기
     */
    public void updateNoticeProcessor(PrincipalDetails principalDetails, NoticeForm noticeForm) {
        Notice notice = noticeRepository.findById(noticeForm.getId()).orElse(null);

        updateNotice(principalDetails, noticeForm, notice);

    }

    private void updateNotice(PrincipalDetails principalDetails, NoticeForm noticeForm, Notice notice) {
        notice.setTitle(noticeForm.getTitle());
        notice.setContents(noticeForm.getContents());
        notice.setPublishTime(noticeForm.getPublishTime());
        notice.setPublishDate(noticeForm.getPublishDate());
        notice.setPublishDate(noticeForm.getPublishDate());
        notice.setPublishHour(noticeForm.getPublishHour());
        notice.setPublishMinutes(noticeForm.getPublishMinutes());
        notice.setCreatedAt(LocalDateTime.now());
        notice.setUpdatedId(principalDetails.getAccount().getUserId());
    }

    @Transactional
    public Notice changeNoticeFlagProcessor(Notice notice, String openFlag) {
        if (openFlag.equals("Y")) {
            notice.setOpenFlag("N");
        } else {
            notice.setOpenFlag("Y");
        }
        return notice;
    }
}
