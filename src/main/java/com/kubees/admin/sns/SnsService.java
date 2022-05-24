package com.kubees.admin.sns;

import com.kubees.admin.account.AccountRepository;
import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.admin.sns.form.SearchForm;
import com.kubees.admin.sns.form.SnsDetailForm;
import com.kubees.admin.sns.form.SnsList;
import com.kubees.domain.Account;
import com.kubees.domain.Block;
import com.kubees.domain.enumType.UserRole;
import com.kubees.domain.enumType.UserStatus;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
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
import java.util.*;

import static com.kubees.domain.QAccount.account;
import static com.kubees.domain.QFeed.feed;
import static com.kubees.domain.QSubscribe.subscribe;

@Slf4j
@Service
@RequiredArgsConstructor
public class SnsService {

    private final AccountRepository accountRepository;
    private final BlockRepository blockRepository;

    @PersistenceContext
    EntityManager em;
    JPAQueryFactory queryFactory;

    @Transactional
    public Page<SnsList> getSnsListProcessor(SearchForm searchForm, Pageable pageable) {
        queryFactory = new JPAQueryFactory(em);

        QueryResults<Account> accountQueryResults = queryFactory.selectFrom(account)
                .where(searchPhoneContain(searchForm.getKeyword()),
                        searchNameContain(searchForm.getKeyword()),
                        searchNicknameContain(searchForm.getKeyword()),
                        searchEmailContain(searchForm.getKeyword()),
                        roleUser(UserRole.ROLE_USER)
                )
                .orderBy(account.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Account> resultList = accountQueryResults.getResults();

        List<SnsList> list = new ArrayList<>();

        for (Account result : resultList) {
            SnsList snsList = new SnsList();

            snsList.setEmail(result.getEmail());
            snsList.setId(result.getId());
            snsList.setNickname(result.getNickname());
            snsList.setName(result.getName());
            snsList.setFollowCount(getSubscribeCount(result));
            snsList.setFeedCount(getFeedCount(result));

            list.add(snsList);
        }
        long total = accountQueryResults.getTotal();

        return new PageImpl<>(list, pageable, total);
    }

    private Predicate roleUser(UserRole roleUser) {
        return account.roles.eq(String.valueOf(roleUser));
    }


    private Long getFeedCount(Account result) {
        queryFactory = new JPAQueryFactory(em);
        return queryFactory.select(feed.count())
                .from(feed)
                .where(feed.userEmail.eq(result.getEmail()))
                .fetchOne();
    }

    private Long getSubscribeCount(Account result) {
        queryFactory = new JPAQueryFactory(em);
        return queryFactory.select(subscribe.count())
                .from(subscribe)
                .where(subscribe.fromUser.userId.eq(result.getUserId()))
                .fetchOne();
    }

    private BooleanExpression searchPhoneContain(String keywordCond) {
        return keywordCond != null ? account.phone.contains(keywordCond) : null;
    }

    private BooleanExpression searchNameContain(String keywordCond) {
        return keywordCond != null ? account.name.contains(keywordCond) : null;
    }

    private BooleanExpression searchNicknameContain(String keywordCond) {
        return keywordCond != null ? account.nickname.contains(keywordCond) : null;
    }

    private BooleanExpression searchEmailContain(String keywordCond) {
        return keywordCond != null ? account.email.contains(keywordCond) : null;
    }

    public List<SnsDetailForm> getUserDetailProcessor(Long id) {
        List<Account> fetchResult = queryFactory.selectFrom(account)
                .where(account.id.in(
                        JPAExpressions
                                .select(subscribe.toUser.id)
                                .from(subscribe)
                                .where(subscribe.fromUser.id.eq(id))
                )).fetch();

        List<SnsDetailForm> list = new ArrayList<>();
        for (Account account : fetchResult) {
            SnsDetailForm snsDetailForm = new SnsDetailForm();

            snsDetailForm.setEmail(account.getEmail());
            snsDetailForm.setName(account.getName());
            snsDetailForm.setProfileId(account.getProfileId());
            snsDetailForm.setCharacterId(account.getCharacterId());
            snsDetailForm.setUserId(account.getUserId());
            snsDetailForm.setUserStatus(account.getUserStatus());
            snsDetailForm.setUserDescription(account.getUserDescription());
            list.add(snsDetailForm);
        }
        return list;
    }

    public Account getUserInfo(Long id) {
        queryFactory = new JPAQueryFactory(em);
        return queryFactory.selectFrom(account)
                .where(account.id.eq(id))
                .fetchOne();
    }

    @Transactional
    public Map<String, Object> changeUserStatus(PrincipalDetails principalDetails, String email, String userStatus) {
        Enum status = userStatus.equals("Y") ? UserStatus.D : UserStatus.Y;
        Account account = accountRepository.findByEmail(email);
        account.setUserStatus((UserStatus) status);
        account.setStatusChangeEmail(principalDetails.getAccount().getEmail());
        account.setStatusModifiedAt(LocalDateTime.now());

        Map<String, Object> map = new HashMap<>();
        map.put("userStatus", account.getUserStatus());
        map.put("email", account.getEmail());
        return map;
    }

    @Transactional
    public void changeBlockFlagStatus(PrincipalDetails principalDetails, Long fromUserId, Long toUserId) {
//        Block findBlockUserInfo = blockRepository.findByFromUserIdAndToUserId(fromUserId, toUserId);
        UserRole role = UserRole.valueOf(principalDetails.getAccount().getRoles());

        //관리자만 수정을 할 수 있다.
        if(role == UserRole.ROLE_ADMIN){
            blockRepository.deleteByFromUserIdAndToUserId(fromUserId, toUserId);
        }
    }
}
