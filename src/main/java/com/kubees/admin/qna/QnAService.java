package com.kubees.admin.qna;

import com.kubees.domain.QnA;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QnAService {

    private final QnARepository qnARepository;

    public QnA getQnaProcessor(Long id) {
        return qnARepository.findById(id).orElse(null);
    }

    public List<QnA> getQnaListProcessor() {
        return qnARepository.findAll();
    }
}
