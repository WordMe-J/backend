package kr.wordme.model.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mock_exam_question")
@NoArgsConstructor
@Getter
public class MockExamQuestion {

	@Id
	private UUID id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mock_exam_id")
	private MockExam mockExam;

	@Column(name = "question_text", columnDefinition = "TEXT")
	private String questionText;

	@OneToMany(
		mappedBy = "mockExamQuestion",
		fetch = FetchType.LAZY,
		cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE},
		orphanRemoval = true
	)
	private List<MockExamDistractor> distractors;
}
