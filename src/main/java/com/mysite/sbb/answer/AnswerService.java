package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.mysite.sbb.user.SiteUser;
import java.util.Optional;
import com.mysite.sbb.DataNotFoundException;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;


    public Answer create(Question question, String content, Optional<SiteUser> author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        if (author.isPresent()) {
            answer.setAuthor(author.get());
        } else {
            answer.setAuthor(null);
        }
        this.answerRepository.save(answer);
        return answer;
    }
    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }
    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }
    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }
    public void vote(Answer answer, Optional<SiteUser> siteUser) {
        if (siteUser.isPresent()) {
            answer.getVoter().add(siteUser.get());
        }
        this.answerRepository.save(answer);
    }
}
