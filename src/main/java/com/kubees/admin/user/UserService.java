package com.kubees.admin.user;

import com.kubees.admin.user.form.SearchForm;
import com.kubees.domain.Account;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.kubees.domain.QAccount.account;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @PersistenceContext
    EntityManager em;
    JPAQueryFactory queryFactory;

    public List<Account> searchUserList(SearchForm searchForm) {

        queryFactory = new JPAQueryFactory(em);

        // searchType과 keyword로 검색하기
        List<Account> accountList = queryFactory.selectFrom(account)
                .where(searchTypeContain(searchForm.getSearchType(), searchForm.getKeyword()))
                .fetch();
        return accountList;
    }

    // 연락처(phone), 이름(name), 닉네임(nickname), 계정(email)
    private BooleanExpression searchTypeContain(String typeCondition, String keywordCondition) {
        BooleanExpression booleanExpression = null;
        keywordCondition = keywordCondition != null ? keywordCondition : "";

        if (typeCondition != null && typeCondition.equals("phone")) {
            booleanExpression = typeCondition != null ? account.phone.contains(keywordCondition) : null;
        }
        if (typeCondition != null && typeCondition.equals("name")) {
            booleanExpression = typeCondition != null ? account.name.contains(keywordCondition) : null;
        }
        if (typeCondition != null && typeCondition.equals("email")) {
            booleanExpression = typeCondition != null ? account.email.contains(keywordCondition) : null;
        }
        if (typeCondition != null && typeCondition.equals("nickname")) {
            booleanExpression = typeCondition != null ? account.nickname.contains(keywordCondition) : null;
        }
        return booleanExpression;
    }
}
