package kr.wordme.model.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "word_note")
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WordNote {

	@Id
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "initial_creator_id")
	private Member initialCreator;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private Member owner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "word_category_id")
	private WordCategory wordCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "forked_word_note_id")
	private WordNote forkedWordNote;

	@Column(name = "title")
	private String title;

	@Column(name = "delete_sign")
	private Boolean deleteSign;

	@Column(name = "created_at", updatable = false)
	@CreationTimestamp
	private Timestamp createdAt;

	@Column(name = "modified_at")
	@UpdateTimestamp
	private Timestamp modifiedAt;

	@OneToMany(
		mappedBy = "wordNote",
		fetch = FetchType.LAZY,
		cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE},
		orphanRemoval = true
	)
	private List<WordNoteWord> wordNoteWords;

	public static WordNote of(Member initialCreator, Member owner, WordCategory wordCategory, WordNote forkedNote,String title) {
		return WordNote.builder()
				.id(UUID.randomUUID())
				.deleteSign(false)
				.initialCreator(initialCreator)
				.owner(owner)
				.wordCategory(wordCategory)
				.forkedWordNote(forkedNote)
				.title(title)
				.build();
	}
}
