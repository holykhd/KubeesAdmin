package com.kubees.admin.pushMessage;

import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.admin.pushMessage.form.PushMessageForm;
import com.kubees.admin.pushMessage.form.SearchForm;
import com.kubees.domain.PushMessage;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.kubees.domain.QPushMessage.pushMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushMessageService {
    private final PushMessageRepository pushMessageRepository;

    @PersistenceContext
    EntityManager em;
    JPAQueryFactory queryFactory;

    @Transactional
    public void createPushMessageProcessor(PrincipalDetails principalDetails, PushMessageForm pushMessageForm) throws ParseException {
        PushMessage pushMessage = new PushMessage();
        pushMessage.setTitle(pushMessageForm.getTitle());
        pushMessage.setContents(pushMessageForm.getContents());
        pushMessage.setPublishTime(pushMessageForm.getPublishTime());
        pushMessage.setPublishDate(pushMessageForm.getPublishDate());
        pushMessage.setPublishHour(pushMessageForm.getPublishHour());
        pushMessage.setPublishMinutes(pushMessageForm.getPublishMinutes());
        pushMessage.setCreatedId(principalDetails.getUsername());
        pushMessage.setBtnNameFirst(pushMessageForm.getBtnNameFirst());
        pushMessage.setBtnLinkFirst(pushMessageForm.getBtnLinkFirst());
        pushMessage.setBtnUseFirst(pushMessageForm.getBtnUseFirst());
        pushMessage.setBtnNameSecond(pushMessageForm.getBtnNameSecond());
        pushMessage.setBtnLinkSecond(pushMessageForm.getBtnLinkSecond());
        pushMessage.setBtnUseSecond(pushMessageForm.getBtnUseSecond());
        pushMessage.setBtnNameThird(pushMessageForm.getBtnNameThird());
        pushMessage.setBtnLinkThird(pushMessageForm.getBtnLinkThird());
        pushMessage.setBtnUseThird(pushMessageForm.getBtnUseThird());

        if (pushMessageForm.getPublishTime().equals("reservation")) {
            String datePattern = pushMessageForm.getPublishDate() + " " + pushMessageForm.getPublishHour() + ":" + pushMessageForm.getPublishMinutes();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = simpleDateFormat.parse(datePattern);
            String openMilliSecond = String.valueOf(date.getTime());
            pushMessage.setOpenDate(openMilliSecond);
        }


        if (pushMessageForm.getPublishTime().equals("now")) {
            pushMessage.setOpenFlag("Y");
        } else {
            pushMessage.setOpenFlag("N");
        }


        pushMessageRepository.save(pushMessage);

    }

    @Transactional
    public Page<PushMessage> getPushMessageProcessor(SearchForm searchForm, Pageable pageable) throws ParseException {
        // 현재 시간 값을 밀리세컨으로 구한다.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String nowDate = sdf.format(new Date());
        Date date = sdf.parse(nowDate);
        long nowMillisecond = date.getTime();

        queryFactory = new JPAQueryFactory(em);

        // 예약 등록인 글들을 조회해서 예약 시간보다 현재 시간이 큰 값이 있을 경우 open_flag를 N에서 Y로 변경하기
        List<PushMessage> reservationList = queryFactory.selectFrom(pushMessage)
                .where(
                        pushMessage.publishTime.eq("reservation")
                                .and(pushMessage.openFlag.eq("N"))
                )
                .fetch();

        for (PushMessage pushMessage : reservationList) {
            // 등록 날짜가 오늘 날짜보다 작을경우(오픈일/시간일경우) 오픈 플래그를 N에서 Y로 변경한다.
            if (nowMillisecond > Long.parseLong(pushMessage.getOpenDate())) {
                pushMessage.setOpenFlag("Y");
            }
        }

        List<Map<String, Object>> reservationPushMessageList = new ArrayList<>();
        for (PushMessage pushMessage : reservationList) {
            Map<String, Object> map = new HashMap<>();

            String pushMessageReservationDate = pushMessage.getPublishDate() + " " + pushMessage.getPublishHour() + ":" + pushMessage.getPublishMinutes();
            map.put("publishDate", pushMessage.getPublishDate());
            map.put("publishHour", pushMessage.getPublishHour());
            map.put("publishMinutes", pushMessage.getPublishMinutes());

            Date publishDate = sdf.parse(pushMessageReservationDate);
            map.put("milliTime", publishDate.getTime());
            map.put("publishTime", publishDate);
            reservationPushMessageList.add(map);
        }

        QueryResults<PushMessage> result = queryFactory.selectFrom(pushMessage)
                .where(searchTypeEq(searchForm.getSearchType(), searchForm.getKeyword()))
                .offset(pageable.getOffset())
                .orderBy(pushMessage.createdAt.desc())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<PushMessage> pushMessagesList = result.getResults();
        long total = result.getTotal();

        return new PageImpl<>(pushMessagesList, pageable, total);
    }

    private BooleanExpression searchTypeEq(String searchTypeCond, String keywordCond) {
        if (searchTypeCond != null && searchTypeCond.equals("title")) {
            return pushMessage.title.contains(keywordCond);
        }
        if (searchTypeCond != null && searchTypeCond.equals("message")) {
            return pushMessage.contents.contains(keywordCond);
        }
        return null;
    }

    @Transactional
    public void deleteProcessor(Long id) {
        pushMessageRepository.deleteById(id);
    }

    @Transactional
    public void editPushMessageProcessor(PrincipalDetails principalDetails, PushMessageForm pushMessageForm, Long id) throws ParseException {
        PushMessage pushMessage = pushMessageRepository.findById(id).orElse(null);

        pushMessage.setTitle(pushMessageForm.getTitle());
        pushMessage.setContents(pushMessageForm.getContents());
        pushMessage.setPublishTime(pushMessageForm.getPublishTime());
        pushMessage.setPublishDate(pushMessageForm.getPublishDate());
        pushMessage.setPublishHour(pushMessageForm.getPublishHour());
        pushMessage.setPublishMinutes(pushMessageForm.getPublishMinutes());
        pushMessage.setCreatedId(principalDetails.getUsername());
        pushMessage.setBtnNameFirst(pushMessageForm.getBtnNameFirst());
        pushMessage.setBtnLinkFirst(pushMessageForm.getBtnLinkFirst());
        pushMessage.setBtnUseFirst(pushMessageForm.getBtnUseFirst());
        pushMessage.setBtnNameSecond(pushMessageForm.getBtnNameSecond());
        pushMessage.setBtnLinkSecond(pushMessageForm.getBtnLinkSecond());
        pushMessage.setBtnUseSecond(pushMessageForm.getBtnUseSecond());
        pushMessage.setBtnNameThird(pushMessageForm.getBtnNameThird());
        pushMessage.setBtnLinkThird(pushMessageForm.getBtnLinkThird());
        pushMessage.setBtnUseThird(pushMessageForm.getBtnUseThird());

        if (pushMessageForm.getPublishTime().equals("reservation")) {
            String datePattern = pushMessageForm.getPublishDate() + " " + pushMessageForm.getPublishHour() + ":" + pushMessageForm.getPublishMinutes();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = simpleDateFormat.parse(datePattern);
            String openMilliSecond = String.valueOf(date.getTime());
            pushMessage.setOpenDate(openMilliSecond);
        }

    }
}
