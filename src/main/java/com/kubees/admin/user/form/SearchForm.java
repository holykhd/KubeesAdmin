package com.kubees.admin.user.form;

import com.kubees.domain.Account;
import lombok.Data;

@Data
public class SearchForm extends Account {
    private String searchType;
    private String keyword;
    private boolean feedStatus;
}
