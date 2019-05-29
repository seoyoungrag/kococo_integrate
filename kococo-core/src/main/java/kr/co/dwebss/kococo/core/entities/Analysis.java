package kr.co.dwebss.kococo.core.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Analysis generated by hbm2java
 */
@Entity
@Table(name = "analysis", catalog = "kococo")
public class Analysis extends ResourceSupport implements java.io.Serializable {

	private Integer analysisId;
	private Record record;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime analysisStartD;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime analysisStartDt;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime analysisEndD;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime analysisEndDt;
	private String analysisFileNm;
	private String analysisFileAppPath;
	private Character analysisServerUploadYn='N';
	private String analysisServerUploadPath;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime analysisServerUploadDt;
	private Character claimYn='N';
	private Integer claimReasonCd;
	private String claimContents;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime claimRegistDt;
	private List<AnalysisDetails> analysisDetailsList = new ArrayList<AnalysisDetails>(0);

	public Analysis() {
	}

	public Analysis(LocalDateTime analysisStartDt, LocalDateTime analysisEndDt,
			String analysisFileNm, String analysisFileAppPath, List<AnalysisDetails> analysisDetailsList) {
		this.analysisStartDt = analysisStartDt;
		this.analysisEndDt = analysisEndDt;
		this.analysisFileNm = analysisFileNm;
		this.analysisFileAppPath = analysisFileAppPath;
		this.analysisDetailsList = analysisDetailsList;
	}
	
	public Analysis(Record record, LocalDateTime analysisStartD, LocalDateTime analysisStartDt, LocalDateTime analysisEndD, LocalDateTime analysisEndDt,
			String analysisFileNm, String analysisFileAppPath, Character analysisServerUploadYn,
			String analysisServerUploadPath, LocalDateTime analysisServerUploadDt, Character claimYn, Integer claimReasonCd,
			String claimContents, LocalDateTime claimRegistDt, List<AnalysisDetails> analysisDetailsList) {
		this.record = record;
		this.analysisStartD = analysisStartD;
		this.analysisStartDt = analysisStartDt;
		this.analysisEndD = analysisEndD;
		this.analysisEndDt = analysisEndDt;
		this.analysisFileNm = analysisFileNm;
		this.analysisFileAppPath = analysisFileAppPath;
		this.analysisServerUploadYn = analysisServerUploadYn;
		this.analysisServerUploadPath = analysisServerUploadPath;
		this.analysisServerUploadDt = analysisServerUploadDt;
		this.claimYn = claimYn;
		this.claimReasonCd = claimReasonCd;
		this.claimContents = claimContents;
		this.claimRegistDt = claimRegistDt;
		this.analysisDetailsList = analysisDetailsList;
	}


	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ANALYSIS_ID", unique = true, nullable = false)
	public Integer getAnalysisId() {
		return this.analysisId;
	}

	public void setAnalysisId(Integer analysisId) {
		this.analysisId = analysisId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "RECORD_ID")
	public Record getRecord() {
		return this.record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	@Column(name = "ANALYSIS_START_D", length = 10)
	public LocalDateTime getAnalysisStartD() {
		if(this.analysisStartD==null&&this.analysisStartDt!=null) {
			this.analysisStartD = this.analysisStartDt;
		}
		return this.analysisStartD;
	}

	public void setAnalysisStartD(LocalDateTime analysisStartD) {
		this.analysisStartD = analysisStartD;
	}

	@Column(name = "ANALYSIS_START_DT", length = 19)
	public LocalDateTime getAnalysisStartDt() {
		return this.analysisStartDt;
	}

	public void setAnalysisStartDt(LocalDateTime analysisStartDt) {
		this.analysisStartDt = analysisStartDt;
	}

	@Column(name = "ANALYSIS_END_D", length = 10)
	public LocalDateTime getAnalysisEndD() {
		if(this.analysisEndD==null&&this.analysisEndDt!=null) {
			this.analysisEndD = this.analysisEndDt;
		}
		return this.analysisEndD;
	}

	public void setAnalysisEndD(LocalDateTime analysisEndD) {
		this.analysisEndD = analysisEndD;
	}

	@Column(name = "ANALYSIS_END_DT", length = 19)
	public LocalDateTime getAnalysisEndDt() {
		return this.analysisEndDt;
	}

	public void setAnalysisEndDt(LocalDateTime analysisEndDt) {
		this.analysisEndDt = analysisEndDt;
	}

	@Column(name = "ANALYSIS_FILE_NM", length = 60)
	public String getAnalysisFileNm() {
		return this.analysisFileNm;
	}

	public void setAnalysisFileNm(String analysisFileNm) {
		this.analysisFileNm = analysisFileNm;
	}

	@Column(name = "ANALYSIS_FILE_APP_PATH")
	public String getAnalysisFileAppPath() {
		return this.analysisFileAppPath;
	}

	public void setAnalysisFileAppPath(String analysisFileAppPath) {
		this.analysisFileAppPath = analysisFileAppPath;
	}

	@Column(name = "ANALYSIS_SERVER_UPLOAD_YN", length = 1)
	public Character getAnalysisServerUploadYn() {
		if(!Optional.ofNullable(this.analysisServerUploadPath).orElse("").equals("")) {
			return 'Y';
		}else {
			return this.analysisServerUploadYn;
		}
		
	}

	public void setAnalysisServerUploadYn(Character analysisServerUploadYn) {
		if(!Optional.ofNullable(this.analysisServerUploadPath).orElse("").equals("")) {
			this.analysisServerUploadYn = 'Y';
		}else {
			this.analysisServerUploadYn = analysisServerUploadYn;
		}
	}

	@Column(name = "ANALYSIS_SERVER_UPLOAD_PATH")
	public String getAnalysisServerUploadPath() {
		return this.analysisServerUploadPath;
	}

	public void setAnalysisServerUploadPath(String analysisServerUploadPath) {
		this.analysisServerUploadPath = analysisServerUploadPath;
	}

	@Column(name = "ANALYSIS_SERVER_UPLOAD_DT", length = 19)
	public LocalDateTime getAnalysisServerUploadDt() {
		return this.analysisServerUploadDt;
	}

	public void setAnalysisServerUploadDt(LocalDateTime analysisServerUploadDt) {
		this.analysisServerUploadDt = analysisServerUploadDt;
	}

	@Column(name = "CLAIM_YN", length = 1)
	public Character getClaimYn() {
		return this.claimYn;
	}

	public void setClaimYn(Character claimYn) {
		this.claimYn = claimYn;
	}

	@Column(name = "CLAIM_REASON_CD")
	public Integer getClaimReasonCd() {
		return this.claimReasonCd;
	}

	public void setClaimReasonCd(Integer claimReasonCd) {
		this.claimReasonCd = claimReasonCd;
	}

	@Column(name = "CLAIM_CONTENTS", length = 250)
	public String getClaimContents() {
		return this.claimContents;
	}

	public void setClaimContents(String claimContents) {
		this.claimContents = claimContents;
	}

	@Column(name = "CLAIM_REGIST_DT", length = 19)
	public LocalDateTime getClaimRegistDt() {
		return this.claimRegistDt;
	}

	public void setClaimRegistDt(LocalDateTime claimRegistDt) {
		this.claimRegistDt = claimRegistDt;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "analysis")
	public List<AnalysisDetails> getAnalysisDetailsList() {
		return analysisDetailsList;
	}

	public void setAnalysisDetailsList(List<AnalysisDetails> analysisDetailsList) {
        if (this.analysisDetailsList != null) {
            this.analysisDetailsList.forEach(analysisDetails -> analysisDetails.setAnalysis(null));
        }
        if (analysisDetailsList != null) {
        	analysisDetailsList.forEach(analysisDetails -> analysisDetails.setAnalysis(this));
        }
        
		this.analysisDetailsList = analysisDetailsList;
	}

}