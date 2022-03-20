package com.kubees.admin.qna;


import com.kubees.admin.qna.form.SearchForm;
import com.kubees.domain.*;
import com.kubees.domain.enumType.QnAAnswerStatus;
import com.kubees.domain.enumType.QnATypeStatus;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
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
import java.util.List;

import static com.kubees.domain.QQnA.qnA;

@Slf4j
@Service
@RequiredArgsConstructor
public class QnAService {

    @PersistenceContext
    EntityManager em;
    JPAQueryFactory queryFactory;

    private final QnARepository qnARepository;

    public QnA getQnaProcessor(Long id) {
        return qnARepository.findById(id).orElse(null);
    }

    @Transactional
    public Page<QnA> getQnaListProcessor(SearchForm searchForm, Pageable pageable) {

        queryFactory = new JPAQueryFactory(em);

        QueryResults<QnA> results = queryFactory.selectFrom(qnA)
                .where(searchStatusEq(searchForm.getAnswerStatus()),
                        searchTypeEq(searchForm.getSearchType()),
                        searchKeywordContain(searchForm.getKeyword()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<QnA> content = results.getResults();
        log.info("resultResult ={}", content);
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression searchStatusEq(Enum answerStatusCond) {
        return answerStatusCond != null ? qnA.answerStatus.eq((QnAAnswerStatus) answerStatusCond) : null;
    }

    private BooleanExpression searchTypeEq(Enum searchTypeCond) {
        return searchTypeCond != null ? qnA.type.eq((QnATypeStatus) searchTypeCond) : null;
    }

    private Predicate searchKeywordContain(String keywordCond) {
        return keywordCond != null ? qnA.title.contains(keywordCond) : null;
    }
}
