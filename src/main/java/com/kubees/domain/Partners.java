package com.kubees.domain;

import com.kubees.domain.enumType.PartnerStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
@NoArgsConstructor
public class Partners {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "partner_company_name")
    private String partnerCompanyName;          // 업체명

    @Column(name = "partner_id", unique = true)
    private String partnerId;                   // 파트너 아이디

    @Column(name = "partner_password")
    private String partnerPassword;             // 비밀번호

    @Column(name = "partner_user_name")
    private String partnerUserName;             // 담당자명

    @Column(name = "partner_phone")
    private String partnerPhone;                // 전화번호

    @Column(name = "partner_email")
    private String partnerEmail;                // 이메일

    @Column(name = "partner_created")
    private LocalDateTime partnerCreated;              // 등록일

    @Column(name = "partner_created_user_id")
    private String partnerCreatedUserId;        // 등록자 아이디

    @Column(name = "partner_update")
    private LocalDateTime partnerUpdate;               // 수정일

    @Column(name = "partner_update_user_id")
    private String partnerUpdateUserId;         // 수정자 아이디

    @Column(name = "partner_status")
    @Enumerated(EnumType.STRING)
    private PartnerStatus partnerStatus;        // 파트너 상태(USE : 사용중, BLOCK : 차단)

    @Column(name = "block_reason")
    private String blockReason;                 // 차단당한 이유

    @Column(name = "partner_hit")
    private int partnerHit;                     // 조회수

    @PrePersist
    public void DefaultCreate() {
        this.partnerCreated = LocalDateTime.now();
        this.partnerStatus = PartnerStatus.USE;
    }
}
