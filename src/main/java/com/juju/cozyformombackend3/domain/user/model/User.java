package com.juju.cozyformombackend3.domain.user.model;

import com.juju.cozyformombackend3.global.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import java.util.Date;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Table(name = "user", uniqueConstraints = {
				@UniqueConstraint(columnNames = {"nickname", "email"},
								name = "nickname_email_unique")})
// TODO: id 컬럼명 변경할지 생각해보기
@Entity
public class User extends BaseEntity {

	@Column(name = "user_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(name = "user_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private UserType userType;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "nickname", nullable = false)
	private String nickname;

	@Column(name = "profile_image_url", nullable = false)
	private String profileImageUrl;

	@Temporal(TemporalType.DATE)
	private Date birth;

	@Column(name = "email", nullable = false)
	private String email;

	@Builder
	public User(UserType userType, String name, String nickname, String profileImageUrl, Date birth,
					String email) {
		this.userType = userType;
		this.name = name;
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
		this.birth = birth;
		this.email = email;
	}

	@Override
	public void delete() {

	}
}
