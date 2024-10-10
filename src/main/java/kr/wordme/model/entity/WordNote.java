package kr.wordme.model.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "word_note")
@NoArgsConstructor
@Getter
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

	@OneToMany(mappedBy = "wordNote", fetch = FetchType.LAZY)
	private List<WordNoteWord> wordNoteWords;
}
