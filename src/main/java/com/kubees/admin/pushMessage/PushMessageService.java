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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.kubees.domain.QNotice.notice;
import static com.kubees.domain.QPushMessage.pushMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushMessageService {
    private final PushMessageRepository pushMessageRepository;

    @PersistenceContext
    EntityManager em;
    JPAQueryFactory queryFactory;

    public void createPushMessageProcessor(PrincipalDetails principalDetails, PushMessageForm pushMessageForm) {
        PushMessage pushMessage = new PushMessage();
        pushMessage.setTitle(pushMessageForm.getTitle());
        pushMessage.setMessage(pushMessageForm.getMessage());
        pushMessage.setSendUser(pushMessageForm.getSendUser());
        pushMessage.setSendCycle(pushMessageForm.getSendCycle());
        pushMessage.setSendTime(pushMessageForm.getSendTime());
        pushMessage.setSendDate(pushMessageForm.getSendDate());
        pushMessage.setSendHour(pushMessageForm.getSendHour());
        pushMessage.setSendMinutes(pushMessageForm.getSendMinutes());
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
        pushMessageRepository.save(pushMessage);

    }

    public Page<PushMessage> getPushMessageProcessor(SearchForm searchForm, Pageable pageable) {
        queryFactory = new JPAQueryFactory(em);
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
            return pushMessage.message.contains(keywordCond);
        }
        return null;
    }

}
