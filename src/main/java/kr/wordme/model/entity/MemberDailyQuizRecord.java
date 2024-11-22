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
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
	name = "member_daily_quiz_record",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"member_id", "daily_quiz_id"})
	}
)
@NoArgsConstructor
@Getter
public class MemberDailyQuizRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "daily_quiz_id")
	private DailyQuizWord dailyQuizWord;

	@Column(name = "submitted_answer")
	private Integer submittedAnswer;

	@Column(name = "correct_sign", columnDefinition = "TINYINT(1)", nullable = false)
	private Boolean correctSign;
}
