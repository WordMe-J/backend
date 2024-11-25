package kr.wordme.model.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Pronunciation_category")
@NoArgsConstructor
@Getter
public class PronunciationCategory {

	@Id
	@Column(name = "id")
	private UUID id;

	@Column
	private String nation;

	@Column
	private String gender;

	@Column
	private String pollySpeaker;

}
