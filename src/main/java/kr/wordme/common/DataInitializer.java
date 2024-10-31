package kr.wordme.common;


import kr.wordme.model.entity.Member;
import kr.wordme.model.entity.WordCategory;
import kr.wordme.model.entity.WordNote;
import kr.wordme.model.enums.WordCategoryType;
import kr.wordme.repository.MemberRepository;
import kr.wordme.repository.WordCategoryRepository;
import kr.wordme.repository.WordNoteRepository;
import kr.wordme.repository.WordRepository;
import kr.wordme.service.WordNoteService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner{

    @Value("${initial.member.email}")
    private String initUserEmail;

    @Value("${initial.member.password}")
    private String initUserPassword;

    @Value("${initial.member.nickname}")
    private String initUserNickname;

    private final WordRepository wordRepository;
    private final MemberRepository memberRepository;
    private final WordCategoryRepository wordCategoryRepository;
    private final WordNoteRepository wordNoteRepository;
    private final WordNoteService wordNoteService;

    @Override
    public void run(String... args) throws Exception {

        if (wordRepository.count() == 0) {
            // 초기 데이터 추가 예시
            Member member = Member.of(initUserNickname,initUserEmail,initUserPassword);
            memberRepository.save(member);

            WordCategory wordCategory = WordCategory.of(WordCategoryType.BUSINESS.getTypeName());
            wordCategoryRepository.save(wordCategory);

            WordNote wordNote = WordNote.of(member,member,wordCategory,null,"Business Word Note");
            wordNoteRepository.save(wordNote);

            wordNoteService.saveWordsFromFile(wordNoteService.loadJsonFile(),wordNote);
        }


    }


}
