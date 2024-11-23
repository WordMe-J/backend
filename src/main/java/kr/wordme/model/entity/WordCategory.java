package kr.wordme.model.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "word_category")
@NoArgsConstructor
@Getter
public class WordCategory {

	@Id
	@Column(name = "id")
	private UUID id;

	@Column(name = "category_name")
	private String categoryName;

	@OneToMany(mappedBy = "wordCategory", fetch = FetchType.LAZY)
	private List<WordNote> wordNotes;
}
