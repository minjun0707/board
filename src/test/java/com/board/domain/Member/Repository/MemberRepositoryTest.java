package com.board.domain.Member.Repository;

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

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

@DataJpaTest
public class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@PersistenceContext
	EntityManager em;

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

	@DisplayName("Member 저장 및 찾기")
	@Test
	void saveAndReadTest() {
		// given
		Member member = createMember();

		// when
		Member savedMember = memberRepository.save(member);
		Member foundMember = memberRepository.findById(savedMember.getId()).orElseThrow(MemberNotFoundException::new);

		// then
		Assertions.assertEquals(foundMember.getId(), savedMember.getId());
	}

	@DisplayName("Member 회원정보 변경 ")
	@Nested
	class updateMember {

		@DisplayName("닉네임 변경")
		@Test
		void updateNickNameTest() {
			// given
			String updatedNickname = "updateNickname";
			Member member = createMember();
			Member savedMember = memberRepository.save(member);

			// when
			savedMember.updateNickname(updatedNickname);
			Member foundMember = memberRepository.findById(savedMember.getId())
				.orElseThrow(MemberNotFoundException::new);

			// then
			Assertions.assertEquals(foundMember.getNickname(), updatedNickname);
		}

		@DisplayName("비밀번호 변경")
		@Test
		void updatePasswordTest() {
			// given
			String updatedPassword = "updatedPassword";
			Member member = createMember();
			Member savedMember = memberRepository.save(member);

			// when
			savedMember.updatePassword(updatedPassword);
			Member foundMember = memberRepository.findById(savedMember.getId())
				.orElseThrow(MemberNotFoundException::new);

			// then
			Assertions.assertEquals(foundMember.getPassword(), updatedPassword);
		}
	}

	@DisplayName("Member 조회")
	@Nested
	class findMember {

		@DisplayName("닉네임으로 조회")
		@Test
		void findByNickNameTest() {
			// given
			Member member = createMember();
			Member savedMember = memberRepository.save(member);

			// when
			Member foundMember = memberRepository.findByNickname(savedMember.getNickname())
				.orElseThrow(MemberNotFoundException::new);
			// then
			Assertions.assertEquals(foundMember.getId(), savedMember.getId());
		}

		@DisplayName("이메일로 조회")
		@Test
		void findByEmailTest() {
			// given
			Member member = createMember();
			Member savedMember = memberRepository.save(member);

			// when
			Member foundMember = memberRepository.findByEmail(savedMember.getEmail())
				.orElseThrow(MemberNotFoundException::new);
			// then
			Assertions.assertEquals(foundMember.getId(), savedMember.getId());
		}
	}

}
