package com.kubees.admin.notice;

import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.admin.notice.form.NoticeForm;
import com.kubees.admin.notice.form.SearchForm;
import com.kubees.domain.Notice;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

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
    public void createNoticeProcessor(NoticeForm noticeForm, PrincipalDetails principalDetails) throws ParseException {

        Notice notice = new Notice();
        notice.setTitle(noticeForm.getTitle());
        notice.setContents(noticeForm.getContents());
        notice.setCreatedId(principalDetails.getAccount().getUserId());
        notice.setPublishTime(noticeForm.getPublishTime());
        notice.setPublishDate(noticeForm.getPublishDate());
        notice.setPublishHour(noticeForm.getPublishHour());
        notice.setPublishMinutes(noticeForm.getPublishMinutes());
        notice.setCreatedId(principalDetails.getAccount().getUserId());

        if (noticeForm.getPublishTime().equals("reservation")) {
            String datePattern = noticeForm.getPublishDate() + " " + noticeForm.getPublishHour() + ":" + noticeForm.getPublishMinutes();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = simpleDateFormat.parse(datePattern);
            String openMilliSecond = String.valueOf(date.getTime());
            notice.setOpenDate(openMilliSecond);
        }


        if (noticeForm.getPublishTime().equals("now")) {
            notice.setOpenFlag("Y");
        } else {
            notice.setOpenFlag("N");
        }

        noticeRepository.save(notice);
    }

    @Transactional
    public Page<Notice> getNoticeProcessor(SearchForm searchForm, Pageable pageable) throws ParseException {
        // 현재 시간 값을 밀리세컨으로 구한다.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String nowDate = sdf.format(new Date());
        Date date = sdf.parse(nowDate);
        long nowMillisecond = date.getTime();

        queryFactory = new JPAQueryFactory(em);

        // 예약 등록인 글들을 조회해서 예약 시간보다 현재 시간이 큰 값이 있을 경우 open_flag를 N에서 Y로 변경하기
        List<Notice> reservationList = queryFactory
                .selectFrom(notice)
/*
                .select(
                        Projections.fields(
                                Notice.class
                                , notice.title
                                , notice.createdAt
                                , notice.publishTime
                                , notice.publishDate
                                , notice.publishHour
                                , notice.publishMinutes
                                , notice.openFlag
                                , notice.openDate
                                , notice.id
                        ))
 */
                .from(notice)
                .where(
                        notice.publishTime.eq("reservation")
                                .and(notice.openFlag.eq("N"))
                )
                .fetch();

        for (Notice notice : reservationList) {
            // 공지사항 등록 날짜가 오늘 날짜보다 작을경우(오픈일/시간일경우) 오픈 플래그를 N에서 Y로 변경한다.
            if (nowMillisecond > Long.parseLong(notice.getOpenDate())) {
                notice.setOpenFlag("Y");
            }
        }


        List<Map<String, Object>> reservationNoticeList = new ArrayList<>();
        for (Notice notice : reservationList) {
            Map<String, Object> map = new HashMap<>();
            String noticeReservationDate = notice.getPublishDate() + " " + notice.getPublishHour() + ":" + notice.getPublishMinutes();
            map.put("publishDate", notice.getPublishDate());
            map.put("publishHour", notice.getPublishHour());
            map.put("publishMinutes", notice.getPublishMinutes());

            Date publishDate = sdf.parse(noticeReservationDate);
            map.put("milliTime", publishDate.getTime());
            map.put("publishTime", publishDate);
            reservationNoticeList.add(map);
        }

        QueryResults<Notice> resultList = queryFactory
                .select(
                        Projections.fields(
                                Notice.class
                                , notice.title
                                , notice.createdAt
                                , notice.publishTime
                                , notice.publishDate
                                , notice.publishHour
                                , notice.publishMinutes
                                , notice.openFlag
                                , notice.id))
                .from(notice)
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
    @Transactional
    public void updateNoticeProcessor(PrincipalDetails principalDetails, NoticeForm noticeForm) throws ParseException {
        Notice notice = noticeRepository.findById(noticeForm.getId()).orElse(null);

        updateNotice(principalDetails, noticeForm, notice);

    }

    private void updateNotice(PrincipalDetails principalDetails, NoticeForm noticeForm, Notice notice) throws ParseException {

        notice.setTitle(noticeForm.getTitle());
        notice.setContents(noticeForm.getContents());
        notice.setPublishTime(noticeForm.getPublishTime());
        notice.setPublishDate(noticeForm.getPublishDate());
        notice.setPublishHour(noticeForm.getPublishHour());
        notice.setPublishMinutes(noticeForm.getPublishMinutes());
        notice.setCreatedAt(LocalDateTime.now());
        notice.setUpdatedId(principalDetails.getAccount().getUserId());

        if (noticeForm.getPublishTime().equals("reservation")) {
            String datePattern = noticeForm.getPublishDate() + " " + noticeForm.getPublishHour() + ":" + noticeForm.getPublishMinutes();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = simpleDateFormat.parse(datePattern);
            String openMilliSecond = String.valueOf(date.getTime());
            notice.setOpenDate(openMilliSecond);
        }

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

    @Transactional
    public void deleteNoticeProcessor(Long id) {
        noticeRepository.deleteById(id);
    }
}
