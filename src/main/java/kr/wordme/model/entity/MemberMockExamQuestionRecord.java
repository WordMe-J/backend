package kr.wordme.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_mock_exam_question_record")
@NoArgsConstructor
@Getter
public class MemberMockExamQuestionRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_mock_exam_record_id")
	private MemberMockExamRecord memberMockExamRecord;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mock_exam_question_id")
	private MockExamQuestion mockExamQuestion;

	@Column(name = "submitted_answer")
	private Integer submittedAnswer;

	@Column(name = "correct_sign", columnDefinition = "TINYINT(1)")
	private Boolean correctSign;
}
