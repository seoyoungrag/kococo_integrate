package kr.co.dwebss.kococo.core.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.hateoas.ResourceSupport;

/**
 * User generated by hbm2java
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user", catalog = "kococo")
public class User extends ResourceSupport implements java.io.Serializable {

	private String userAppId;
	private Integer userAge;
	private Character userGender;
	private Integer userWeight;
	private Integer userHeight;
	@CreatedDate
	private LocalDateTime userRegistDt;
	private Set<Record> records = new HashSet<Record>(0);

	public User() {
	}

	public User(String userAppId) {
		this.userAppId = userAppId;
	}

	public User(String userAppId, Integer userAge, Character userGender, Integer userWeight, Integer userHeight,
			LocalDateTime userRegistDt, Set<Record> records) {
		this.userAppId = userAppId;
		this.userAge = userAge;
		this.userGender = userGender;
		this.userWeight = userWeight;
		this.userHeight = userHeight;
		this.userRegistDt = userRegistDt;
		this.records = records;
	}

	@Id
	@Column(name = "USER_APP_ID", unique = true, nullable = false, length = 36)
	public String getUserAppId() {
		return this.userAppId;
	}

	public void setUserAppId(String userAppId) {
		this.userAppId = userAppId;
	}

	@Column(name = "USER_AGE")
	public Integer getUserAge() {
		return this.userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

	@Column(name = "USER_GENDER", length = 1)
	public Character getUserGender() {
		return this.userGender;
	}

	public void setUserGender(Character userGender) {
		this.userGender = userGender;
	}

	@Column(name = "USER_WEIGHT")
	public Integer getUserWeight() {
		return this.userWeight;
	}

	public void setUserWeight(Integer userWeight) {
		this.userWeight = userWeight;
	}

	@Column(name = "USER_HEIGHT")
	public Integer getUserHeight() {
		return this.userHeight;
	}

	public void setUserHeight(Integer userHeight) {
		this.userHeight = userHeight;
	}

	@Column(name = "USER_REGIST_DT", length = 19)
	public LocalDateTime getUserRegistDt() {
		return this.userRegistDt;
	}

	public void setUserRegistDt(LocalDateTime userRegistDt) {
		this.userRegistDt = userRegistDt;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Record> getRecords() {
		return this.records;
	}

	public void setRecords(Set<Record> records) {
		this.records = records;
	}

}