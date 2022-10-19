package th.co.cdgs.formtype;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "formtype")
public class FormType {

	@Id
	@SequenceGenerator(name = "formtypeSequence", sequenceName = "formtype_id_seq", allocationSize = 1,
	initialValue = 10)
	@GeneratedValue(generator = "formtypeSequence")
	@Column(name = "formtype_id")
	private Integer formtypeId;

	@Column(name = "formtype_name", length = 30)
	private String formtypeName;

	public Integer getFormtypeId() {
		return formtypeId;
	}

	public void setFormtypeId(Integer formtypeId) {
		this.formtypeId = formtypeId;
	}

	public String getFormtypeName() {
		return formtypeName;
	}

	public void setFormtypeName(String formtypeName) {
		this.formtypeName = formtypeName;
	}

	
	
	

}
