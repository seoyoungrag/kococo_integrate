package kr.co.dwebss.kococo.core.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.ResourceSupport;

/**
 * Record generated by hbm2java
 */
@Entity
@Table(name = "record", catalog = "kococo")
public class Record extends ResourceSupport implements java.io.Serializable {

	private String userAppId;
	private Integer recordId;
	private User user;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'")
	private LocalDate recordStartD;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime recordStartDt;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'")
	private LocalDate recordEndD;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime recordEndDt;
	private List<Consulting> consultings = new ArrayList<Consulting>(0);
	private List<Analysis> analysisList = new ArrayList<Analysis>(0);
	private Code sleepStatusCd;
	private Integer sleepStatusCdId;

	public Record() {
	}

	public Record(String userAppId, LocalDateTime recordStartDt, LocalDateTime recordEndDt, List<Analysis> analysisList) {
		this.userAppId = userAppId;
		this.recordStartDt = recordStartDt;
		this.recordEndDt = recordEndDt;
		this.analysisList = analysisList;
	}
	
	public Record(User user, LocalDate recordStartD, LocalDateTime recordStartDt, LocalDate recordEndD, LocalDateTime recordEndDt,
			List<Analysis> analysisList, List<Consulting> consultings, Code sleepStatusCd) {
		this.user = user;
		this.recordStartD = recordStartD;
		this.recordStartDt = recordStartDt;
		this.recordEndD = recordEndD;
		this.recordEndDt = recordEndDt;
		this.consultings = consultings;
		this.analysisList = analysisList;
		this.sleepStatusCd = sleepStatusCd;
	}


	public Record(String consultingTitle, String consultingContents, int recordId, List<Analysis> analysisList) {
		this.recordId = recordId;
		this.analysisList = analysisList;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "RECORD_ID", unique = true, nullable = false)
	public Integer getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SLEEP_STATUS_CD", insertable = false, updatable = false)
	public Code getSleepStatusCd() {
		return this.sleepStatusCd;
	}

	public void setSleepStatusCd(Code sleepStatusCd) {
		this.sleepStatusCd = sleepStatusCd;
	}

	@Column(name = "SLEEP_STATUS_CD", length = 11)
	public Integer getSleepStatusCdId() {
		return this.sleepStatusCdId;
	}

	public void setSleepStatusCdId(Integer sleepStatusCdId) {
		this.sleepStatusCdId = sleepStatusCdId;
	}

	@Column(name = "USER_APP_ID", length = 36)
	public String getUserAppId() {
		return this.userAppId;
	}

	public void setUserAppId(String userAppId) {
		this.userAppId = userAppId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_APP_ID", insertable = false, updatable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "RECORD_START_D", length = 10)
	public LocalDate getRecordStartD() {
		if(this.recordStartD==null&&this.recordStartDt!=null) {
			this.recordStartD = this.recordStartDt.toLocalDate();
		}
		return this.recordStartD;
	}

	public void setRecordStartD(LocalDate recordStartD) {
		this.recordStartD = recordStartD;
	}

	@Column(name = "RECORD_START_DT", length = 19)
	public LocalDateTime getRecordStartDt() {
		return this.recordStartDt;
	}

	public void setRecordStartDt(LocalDateTime recordStartDt) {
		this.recordStartDt = recordStartDt;
	}

	@Column(name = "RECORD_END_D", length = 10)
	public LocalDate getRecordEndD() {
		if(this.recordEndD==null&&this.recordEndDt!=null) {
			this.recordEndD = this.recordEndDt.toLocalDate();
		}
		return this.recordEndD;
	}

	public void setRecordEndD(LocalDate recordEndD) {
		this.recordEndD = recordEndD;
	}

	@Column(name = "RECORD_END_DT", length = 19)
	public LocalDateTime getRecordEndDt() {
		return this.recordEndDt;
	}

	public void setRecordEndDt(LocalDateTime recordEndDt) {
		this.recordEndDt = recordEndDt;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "record")
	@OrderBy(value = "consultingRegistDt ASC")
	public List<Consulting> getConsultings() {
		return this.consultings;
	}

	public void setConsultings(List<Consulting> consultings) {
		
        if (this.consultings != null) {
            this.consultings.forEach(consulting -> consulting.setRecord(null));
        }
        if (consultings != null) {
        	consultings.forEach(consulting -> consulting.setRecord(this));
        }
        
		this.consultings = consultings;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "record")
	@OrderBy(value = "analysisStartDt ASC")
	public List<Analysis> getAnalysisList() {
		return this.analysisList;
	}

	public void setAnalysisList(List<Analysis> analysisList) {
		
        if (this.analysisList != null) {
            this.analysisList.forEach(analysis -> analysis.setRecord(null));
        }
        if (analysisList != null) {
        	analysisList.forEach(analysis -> analysis.setRecord(this));
        }
        
		this.analysisList = analysisList;
	}
	
}
