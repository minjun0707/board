package com.board.global.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.board.domain.member.domain.Member;
import com.board.domain.member.exception.MemberNotFoundException;
import com.board.domain.member.repository.MemberRepository;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class EntityDateTest {

	@Autowired
	MemberRepository memberRepository;

	private Member createMember() {
		Member member = Member.builder()
			.email("testEmail@naver.com")
			.password("aaa12345678")
			.nickname("testNickname")
			.memberName("testName")
			.phoneNumber("01012345678")
			.build();

		return member;
	}

	@DisplayName("엔티티의 생성시간과 수정시간")
	@Nested
	class EntityDate {

		@DisplayName("생성시간이 생성")
		@Test
		void createdDateTest() {
			//given
			Member member = createMember();

			//when
			Member savedMember = memberRepository.save(member);

			//then
			Assertions.assertNotNull(savedMember.getCreatedAt());
		}

		@DisplayName("수정시간이 생성")
		@Test
		void modifiedDateTest() {
			//given
			Member member = createMember();

			//when
			Member savedMember = memberRepository.save(member);

			savedMember.updatePassword("changedPassword");
			Member foundMember = memberRepository.findByEmail("testEmail@naver.com")
				.orElseThrow(MemberNotFoundException::new);

			//then
			Assertions.assertNotNull(foundMember.getModifiedAt());
			Assertions.assertNotEquals(foundMember.getCreatedAt(), foundMember.getModifiedAt());
		}
	}
}
