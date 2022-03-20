package com.kubees.admin.qna.form;

import com.kubees.domain.QnA;
import com.kubees.domain.enumType.QnAAnswerStatus;
import com.kubees.domain.enumType.QnATypeStatus;
import lombok.Data;

@Data
public class SearchForm extends QnA {
    private QnAAnswerStatus answerStatus;    // 검색타입 1(답변 상태)
    private QnATypeStatus searchType;      // 검색타입 2
    private String keyword;         // 검색어
}
