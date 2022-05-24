package com.kubees.admin.sns.form;

import lombok.Data;

@Data
public class SearchForm {
    private String searchType;      // 검색 종류
    private String keyword;         // 검색어
}
