package th.co.cdgs.status;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "status")
public class Status {

	@Id
	@SequenceGenerator(name = "statusSequence", sequenceName = "status_id_seq", allocationSize = 1,
	initialValue = 5)
	@GeneratedValue(generator = "statusSequence")
	@Column(name = "status_id")
	private Integer statusId;

	@Column(name = "status_name", length = 20)
	private String statusName;

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}


}
