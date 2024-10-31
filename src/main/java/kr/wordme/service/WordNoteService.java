package kr.wordme.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import kr.wordme.model.entity.Word;
import kr.wordme.model.entity.WordNote;
import kr.wordme.model.entity.WordNoteWord;
import kr.wordme.model.entity.WordNoteWordMeaning;
import kr.wordme.repository.WordNoteWordMeaningRepository;
import kr.wordme.repository.WordNoteWordRepository;
import kr.wordme.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WordNoteService {

    private final WordRepository wordRepository;
    private final WordNoteWordRepository wordNoteWordRepository;
    private final WordNoteWordMeaningRepository wordNoteWordMeaningRepository;
    private final ResourceLoader resourceLoader;

    public List<Map<String, String>> loadJsonFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Resource resource = resourceLoader.getResource("classpath:static/content/words-and-meaning.json");
        // 파일 읽기 및 파싱
        return objectMapper.readValue(resource.getInputStream(), new TypeReference<List<Map<String, String>>>() {
        });
    }

    @Transactional
    public void saveWordsFromFile(List<Map<String, String>> wordData, WordNote wordNote) throws IOException {

        // 각 Word 및 WordNoteWord 생성 및 저장
        for (Map<String, String> wordEntry : wordData) {
            String wordSpelling = wordEntry.get("word");
            String meaning = wordEntry.get("meaning");

            // 임의의 pronunciation data 설정
            String pronunciationFileName = wordSpelling + ".mp3";
            String pronunciationUrl = "https://example.com/pronunciations/" + pronunciationFileName;

            // Word 생성
            Word word = Word.of(wordSpelling, pronunciationFileName, pronunciationUrl);
            wordRepository.save(word);

            // WordNoteWord 생성
            WordNoteWord wordNoteWord = WordNoteWord.of(wordNote, word);
            wordNoteWordRepository.save(wordNoteWord);

            WordNoteWordMeaning wordNoteWordMeaning = WordNoteWordMeaning.of(wordNoteWord, meaning);
            wordNoteWordMeaningRepository.save(wordNoteWordMeaning);
        }
    }
}
