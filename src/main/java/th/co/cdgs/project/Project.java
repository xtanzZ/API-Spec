package th.co.cdgs.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Entity
@Table(name = "project")
public class Project {

    @Id
    @SequenceGenerator(name = "projectSequence", sequenceName = "project_id_seq",
            allocationSize = 1, initialValue = 5)
    @GeneratedValue(generator = "projectSequence")
    @Column(name = "project_id")
    private Integer projectId;
    
    @Column(name = "project_name", length = 100)
    private String projectName;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	
    
}
