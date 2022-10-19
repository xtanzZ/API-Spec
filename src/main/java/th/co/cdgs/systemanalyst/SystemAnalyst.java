package th.co.cdgs.systemanalyst;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "systemanalyst")
public class SystemAnalyst {

	@Id
	@SequenceGenerator(name = "systemanalystSequence", sequenceName = "systemanalyst_id_seq", allocationSize = 1,
	initialValue = 5)
	@GeneratedValue(generator = "systemanalystSequence")
	@Column(name = "systemanalyst_id")
	private Integer systemAnalystId;

	@Column(name = "systemanalyst_name", length = 30)
	private String systemAnalystName;

	public Integer getSystemAnalystId() {
		return systemAnalystId;
	}

	public void setSystemAnalystId(Integer systemAnalystId) {
		this.systemAnalystId = systemAnalystId;
	}

	public String getSystemAnalystName() {
		return systemAnalystName;
	}

	public void setSystemAnalystName(String systemAnalystName) {
		this.systemAnalystName = systemAnalystName;
	}
	
	
	
	
	
	
	

	

	


}
