package th.co.cdgs.programs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import th.co.cdgs.project.Project;
import th.co.cdgs.status.Status;
import th.co.cdgs.system.System;
import th.co.cdgs.systemanalyst.SystemAnalyst;

@Entity
@Table(name = "programs")
public class Programs {

	@Id
	@SequenceGenerator(name = "programsSequence", sequenceName = "programs_id_seq", allocationSize = 1,
	initialValue = 5)
	@GeneratedValue(generator = "programsSequence")
	@Column(name = "program_id")
	private Integer programId;
	
	@Column(name = "program_name", length = 100)
	private String programName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id")
	private Project project;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "system_id")
	private System system;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "systemanalyst_id")
	private SystemAnalyst systemAnalyst;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "status_id")
	private Status status;

	public Integer getProgramId() {
		return programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public System getSystem() {
		return system;
	}

	public void setSystem(System system) {
		this.system = system;
	}

	public SystemAnalyst getSystemAnalyst() {
		return systemAnalyst;
	}

	public void setSystemAnalyst(SystemAnalyst systemAnalyst) {
		this.systemAnalyst = systemAnalyst;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	
	






}
