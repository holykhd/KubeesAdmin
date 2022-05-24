package com.kubees.admin.attach;

import com.kubees.admin.attach.form.AttachFeedForm;
import com.kubees.admin.attach.form.AttachImg;
import com.kubees.domain.Attach;
import com.kubees.domain.AttachFeed;
import com.kubees.domain.QAttach;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

import static com.kubees.domain.QAttach.attach;
import static com.kubees.domain.QAttachFeed.attachFeed;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachFeedService {
    private final EntityManager em;
    JPAQueryFactory queryFactory;

    public AttachFeedForm getAttachFeedProcessor(Long attachFeedOid) {
        queryFactory = new JPAQueryFactory(em);
        AttachFeed attachFeedResult = queryFactory.selectFrom(attachFeed)
                .where(attachFeed.attachFeedOid.eq(attachFeedOid))
                .fetchOne();

        return AttachFeedForm.builder()
                .attachFeedOid(attachFeedResult.getAttachFeedOid())
                .originalFileName(attachFeedResult.getOriginalFileName())
                .savedFileName(attachFeedResult.getSavedFileName())
                .savedPath(attachFeedResult.getSavedPath())
                .thumbFileName(attachFeedResult.getThumbFileName())
                .build();
    }

    public AttachFeed selectAttachImgById(Long attachImgOid) {
        queryFactory = new JPAQueryFactory(em);
        return queryFactory.selectFrom(attachFeed)
                .where(attachFeed.attachFeedOid.eq(attachImgOid))
                .fetchOne();

    }

    public Attach selectProfileImgById(Long attachOid) {
        queryFactory = new JPAQueryFactory(em);
        return queryFactory.selectFrom(attach)
                .where(attach.attachOid.eq(attachOid))
                .fetchOne();

    }
}
