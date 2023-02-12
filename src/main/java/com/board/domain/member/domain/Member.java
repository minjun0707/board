package com.board.domain.member.domain;

import com.board.global.common.EntityDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends EntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Column(nullable = false, unique = true, length = 30)
	private String email;

	@Column(nullable = false, length = 20)
	private String password;

	@Column(nullable = false, length = 20)
	private String memberName;

	@Column(nullable = false, unique = true, length = 20)
	private String nickname;

	@Column(length = 20)
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Builder
	public Member(String email, String password, String memberName, String nickname, String phoneNumber) {
		this.email = email;
		this.password = password;
		this.memberName = memberName;
		this.nickname = nickname;
		this.phoneNumber = phoneNumber;
		this.role = Role.SILVER;
	}

	public void updatePassword(String password) {
		this.password = password;
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updatePhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void deletePhoneNumber() {
		this.phoneNumber = null;
	}

}


