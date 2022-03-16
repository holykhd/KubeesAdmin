package com.kubees.admin.partners.form;

import com.kubees.domain.PartnerStatus;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
public class PartnersForm {

    @NotBlank
    @Length(min = 2, max = 20)
    private String partnerCompanyName;          // 업체명

    @NotBlank
    @Length(min = 4, max = 25)
    private String partnerId;                   // 파트너 아이디

    @NotBlank
    @Length(min = 4, max = 25)
    @Pattern(regexp = "^[a-zA-Z0-9ㄱ-ㅎ가-힣_-]{3,30}$")
    private String partnerPassword;                   // 파트너 비밀번호

    @NotBlank
    @Length(min = 2, max = 20)
    private String partnerUserName;             // 담당자명

    private String partnerPhone;                // 전화번호

    @Email
    @NotBlank
    @Length(min = 10, max = 30)
    private String partnerEmail;                // 이메일

    @NotBlank
    @Length(min = 2, max = 20)
    private String partnerCreatedUserId;        // 등록자 아이디

    private LocalDateTime partnerCreated;              // 등록일

    private LocalDateTime partnerUpdate;               // 수정일

    private String partnerUpdateUserId;         // 수정자 아이디

    private PartnerStatus partnerStatus;        // 파트너 상태(USE : 사용중, BLOCK : 차단)

    private String blockReason;                 // 차단당한 이유

}
