package kr.wordme.model.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pronunciation")
@NoArgsConstructor
@Getter
public class Pronunciation {

	@Id
	@Column(name = "id")
	private UUID id;

	@Column(name = "word_spelling", unique = true)
	private String wordSpelling;

	@OneToMany(
		mappedBy = "pronunciation",
		fetch = FetchType.LAZY,
		cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE},
		orphanRemoval = true
	)
	private List<PronunciationFile> pronunciationFiles;
}
