package kr.wordme.model.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "mock_exam")
@NoArgsConstructor
@Getter
public class MockExam {

	@Id
	private UUID id;

	@Column(name = "title")
	private String title;

	@Column(name = "created_at", updatable = false)
	@CreationTimestamp
	private Timestamp createdAt;

	@Column(name = "modified_at")
	@UpdateTimestamp
	private Timestamp modifiedAt;

	@Column(name = "time_limit_in_minutes")
	private Integer timeLimitInMinutes;

	@OneToMany(
		mappedBy = "mockExam",
		fetch = FetchType.LAZY,
		cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE},
		orphanRemoval = true
	)
	private List<MockExamQuestion> questions;
}
