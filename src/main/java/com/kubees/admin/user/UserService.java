package com.kubees.admin.user;

import com.kubees.admin.user.form.BlockForm;
import com.kubees.admin.user.form.FeedForm;
import com.kubees.admin.user.form.SearchForm;
import com.kubees.domain.*;
import com.kubees.domain.enumType.UserRole;
import com.kubees.domain.enumType.UserStatus;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
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
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

import static com.kubees.domain.QAccount.account;
import static com.kubees.domain.QAttachFeed.attachFeed;
import static com.kubees.domain.QBlock.block;
import static com.kubees.domain.QFeed.feed;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    @PersistenceContext
    EntityManager em;
    JPAQueryFactory queryFactory;

    @Transactional(readOnly = true)
    public Page<Account> searchUserList(SearchForm searchForm, Pageable pageable) {

        queryFactory = new JPAQueryFactory(em);

        // searchType과 keyword로 검색하기
        QueryResults<Account> results = queryFactory.selectFrom(account)
                .where(
                        searchTypeContain(
                                searchForm.getSearchType(),
                                searchForm.getKeyword()),
                        roleUser(UserRole.ROLE_USER)
                )
                .orderBy(account.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Account> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    @Transactional(readOnly = true)
    public Page<Account> searchUserDeprecatedList(SearchForm searchForm, Pageable pageable) {
        queryFactory = new JPAQueryFactory(em);
        QueryResults<Account> results = queryFactory.selectFrom(account)
                .where(
                        searchTypeContain(
                                searchForm.getSearchType(),
                                searchForm.getKeyword()),
                        roleUser(UserRole.ROLE_USER),
                        account.userStatus.eq(UserStatus.D)
                )
                .orderBy(account.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Account> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    @Transactional(readOnly = true)
    public Page<Account> searchUserBlockList(SearchForm searchForm, Pageable pageable) {
        queryFactory = new JPAQueryFactory(em);
        QueryResults<Account> results = queryFactory.selectFrom(account)
                .where(
                        searchTypeContain(
                                searchForm.getSearchType(),
                                searchForm.getKeyword()),
                        roleUser(UserRole.ROLE_USER)
                )
                .orderBy(account.createdAt.desc())
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

    @Transactional(readOnly = true)
    public List<FeedForm> findByUserFeedListProcessor(String email, boolean feedStatus) {

        List<Feed> feedList = feedRepository.findByUserEmailAndFeedStatusOrderByIdDesc(email, feedStatus);
        List<FeedForm> feedFormList = new ArrayList<>();
        for (Feed feed : feedList) {
            FeedForm feedForm = new FeedForm();
            queryFactory = new JPAQueryFactory(em);
            AttachFeed attachFeedResult = queryFactory.selectFrom(attachFeed)
                    .where(attachFeed.feedId.eq(feed.getId()), attachFeed.fileSize.gt(10))
                    .orderBy(attachFeed.attachFeedOid.asc())
                    .fetchFirst();
            feedForm.setId(feed.getId());
            feedForm.setFeedStatus(feed.isFeedStatus());
            feedForm.setContents(feed.getContents());
            feedForm.setBgColor(feed.getBgColor());
            if (attachFeedResult != null) {
                feedForm.setImagePath(attachFeedResult.getSavedPath() + File.separator + attachFeedResult.getSavedFileName());
                feedForm.setAttachFeedOid(attachFeedResult.getAttachFeedOid());
            }
            feedFormList.add(feedForm);
        }
        return feedFormList;
    }

    public int getFeedCount(String email) {
        queryFactory = new JPAQueryFactory(em);
        List<Feed> fetch = queryFactory.selectFrom(feed)
                .where(feed.userEmail.eq(email))
                .fetch();
        return fetch.size();

    }

    public Feed getFeedProcessor(Long id) {
        return feedRepository.findById(id).orElse(null);
    }

    @Transactional
    public Feed changeFeedStatusProcessor(Feed feed, boolean feedStatus) {
        feedStatus = feedStatus == true ? false : true;
        feed.setFeedStatus(feedStatus);
        Feed savedFeed = feedRepository.save(feed);
        return feed;
    }

    public List<Feed> getFeedStatusProcessor(String email, boolean feedStatus) {
        queryFactory = new JPAQueryFactory(em);
        List<Feed> feedList = queryFactory.selectFrom(feed)
                .where(feed.userEmail.eq(email), feed.feedStatus.eq(feedStatus))
                .fetch();
        return feedList;
    }

    @Transactional
    public void changeAccountStatus(Long id, String userStatus) {
        UserStatus userStatusValue = userStatus.equals("Y") ? UserStatus.D : UserStatus.Y;
        Account account = userRepository.findById(id).orElse(null);

        account.setUserStatus(userStatusValue);
        account.setId(id);
        account.setModifiedAt(LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public List<BlockForm> getBlockToUserList(Long id) {
        queryFactory = new JPAQueryFactory(em);
//        Account findByAccount = userRepository.findByEmail(email);

//        Long id = findByAccount.getId();

        List<BlockForm> fetch = queryFactory.select(Projections.fields(BlockForm.class,
                        account.id.as("id"),
                        account.userId.as("userId"),
                        account.profileId.as("profileId"),
                        account.characterId.as("characterId"),
                        account.email.as("email"),
                        account.nickname.as("nickname"),
                        account.name.as("name"),
                        account.phone.as("phone"),
                        account.userStatus.as("userStatus"),
                        account.userDescription.as("userDescription"),
                        block.fromUser.id.as("fromUserId"),
                        block.toUser.id.as("toUserId"),
                        block.createdAt.as("createdAt")))
                .from(block)
                .leftJoin(account).on(block.toUser.id.eq(account.id))
                .where(
                        block.fromUser.id.eq(id),
                        account.roles.eq(String.valueOf(UserRole.ROLE_USER))
                )
                .fetch();
        return fetch;
    }
}
