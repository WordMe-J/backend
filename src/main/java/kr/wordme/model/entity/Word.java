package kr.wordme.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "word")
@NoArgsConstructor
@Getter
public class Word {

	@Id
	@Column(name = "word_spelling")
	private String wordSpelling;

	@Column(name = "pronunciation_file_name", nullable = false)
	private String pronunciationFileName;

	@Column(name = "pronunciation_url", nullable = false)
	private String pronunciationUrl;

}
