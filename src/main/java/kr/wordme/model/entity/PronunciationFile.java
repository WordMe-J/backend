package kr.wordme.model.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
	name = "pronunciation_file",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"pronunciation_id", "pronunciation_category_id"})
	}
)
@NoArgsConstructor
@Getter
public class PronunciationFile {

	@Id
	@Column(name = "id")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "pronunciation_id")
	private Pronunciation pronunciation;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pronunciation_category_id")
	private PronunciationCategory pronunciationCategory;

	@Column(name = "pronunciation_file_name", nullable = false)
	private String pronunciationFileName;

	@Column(name = "pronunciation_url", nullable = false)
	private String pronunciationUrl;
}
