package th.co.cdgs.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "system")
public class System {

	@Id
	@SequenceGenerator(name = "systemSequence", sequenceName = "system_id_seq", allocationSize = 1,
	initialValue = 5)
	@GeneratedValue(generator = "systemSequence")
	@Column(name = "system_id")
	private Integer systemId;

	@Column(name = "system_name", length = 100)
	private String systemName;

	public Integer getSystemId() {
		return systemId;
	}

	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	


}
