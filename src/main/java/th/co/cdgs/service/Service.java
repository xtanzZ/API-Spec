package th.co.cdgs.service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "service")
public class Service {

	@Id
	@SequenceGenerator(name = "serviceSequence", sequenceName = "service_id_seq", allocationSize = 1,
	initialValue = 9)
	@GeneratedValue(generator = "serviceSequence")
	@Column(name = "service_id")
	private Integer serviceId;

	@Column(name = "method_name", length = 30)
	private String methodName;
	
	@Column(name = "input_parameter" , length = 300)
	private String inputParameter;
	
	@Column(name = "example_response", length = 1000)
	private String exampleResponse;
	
	@Column(name = "example_picture")
	private String examplePicture;
	
	@Column(name = "detail", length = 1000)
	private String detail;

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getInputParameter() {
		return inputParameter;
	}

	public void setInputParameter(String inputParameter) {
		this.inputParameter = inputParameter;
	}

	public String getExampleResponse() {
		return exampleResponse;
	}

	public void setExampleResponse(String exampleResponse) {
		this.exampleResponse = exampleResponse;
	}

	public String getExamplePicture() {
		return examplePicture;
	}

	public void setExamplePicture(String examplePicture) {
		this.examplePicture = examplePicture;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	
	
}