package kr.wordme.model.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "word_note_word_tag")
@NoArgsConstructor
@Getter
public class WordNoteWordTag {

	@Id
	@Column(name = "id")
	private UUID id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "word_note_word_id")
	private WordNoteWord wordNoteWord;

	@Column(name = "tag_name")
	private String tagName;
}
