package kr.wordme.model.entity;

import java.sql.Timestamp;
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
@Table(name = "member_mock_exam_record")
@NoArgsConstructor
@Getter
public class MemberMockExamRecord {

	@Id
	private UUID id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mock_exam_id")
	private MockExam mockExam;

	@Column(name = "score")
	private Integer score;

	@Column(name = "start_time")
	private Timestamp startTime;

	@Column(name = "end_time")
	private Timestamp endTime;

	@OneToMany(
		mappedBy = "memberMockExamRecord",
		fetch = FetchType.LAZY,
		cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE},
		orphanRemoval = true
	)
	private List<MemberMockExamQuestionRecord> memberMockExamQuestionRecords;
}
