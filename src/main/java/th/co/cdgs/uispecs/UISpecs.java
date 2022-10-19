package th.co.cdgs.uispecs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import th.co.cdgs.formtype.FormType;
import th.co.cdgs.pageimages.PageImages;
import th.co.cdgs.service.Service;

@Entity
@Table(name = "uispecs")
public class UISpecs {

	@Id
	@SequenceGenerator(name = "uispecsSequence", sequenceName = "uispecs_id_seq", allocationSize = 1, initialValue = 28)
	@GeneratedValue(generator = "uispecsSequence")
	@Column(name = "uispec_id")
	private Integer uispecId;

	@Column(name = "label", length = 30)
	private String label;

	@Column(name = "attribure", length = 30)
	private String attribure;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "formtype_id")
	private FormType formType;

	@Column(name = "detail", length = 500)
	private String detail;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pageimage_id")
	private PageImages pageimage;

	@Column(name = "event", length = 1000)
	private String event;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "service_id")
	private Service service;

	public Integer getUispecId() {
		return uispecId;
	}

	public void setUispecId(Integer uispecId) {
		this.uispecId = uispecId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAttribure() {
		return attribure;
	}

	public void setAttribure(String attribure) {
		this.attribure = attribure;
	}

	public FormType getFormType() {
		return formType;
	}

	public void setFormType(FormType formType) {
		this.formType = formType;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public PageImages getPageimage() {
		return pageimage;
	}

	public void setPageimage(PageImages pageimage) {
		this.pageimage = pageimage;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

}
