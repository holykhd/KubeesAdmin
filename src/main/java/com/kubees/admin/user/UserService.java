package com.kubees.admin.user;

import com.kubees.admin.user.form.SearchForm;
import com.kubees.domain.Account;
import com.kubees.domain.UserRole;
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

import static com.kubees.domain.QAccount.account;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @PersistenceContext
    EntityManager em;
    JPAQueryFactory queryFactory;

    public Page<Account> searchUserList(SearchForm searchForm, Pageable pageable) {

        queryFactory = new JPAQueryFactory(em);

        // searchType과 keyword로 검색하기
        QueryResults<Account> results = queryFactory.selectFrom(account)
                .where(searchTypeContain(searchForm.getSearchType(), searchForm.getKeyword()), roleUser(UserRole.ROLE_USER))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Account> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    // 회원 정보는 ROLE_USER 만 출력되게 한다.
    private BooleanExpression roleUser(UserRole roleUser) {
        return account.roles.eq(String.valueOf(roleUser));
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
