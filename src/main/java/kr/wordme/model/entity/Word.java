package kr.wordme.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "word")
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Word {

    @Id
    @Column(name = "word_spelling")
    private String wordSpelling;

    @Column(name = "pronunciation_file_name", nullable = false)
    private String pronunciationFileName;

    @Column(name = "pronunciation_url", nullable = false)
    private String pronunciationUrl;

    public static Word of(String wordSpelling, String pronunciationFileName, String pronunciationUrl) {

        return Word.builder()
                .wordSpelling(wordSpelling)
                .pronunciationFileName(pronunciationFileName)
                .pronunciationUrl(pronunciationUrl)
                .build();

    }

}
