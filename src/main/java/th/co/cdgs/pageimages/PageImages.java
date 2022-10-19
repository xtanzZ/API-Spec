package th.co.cdgs.pageimages;





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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


import th.co.cdgs.programs.Programs;
import th.co.cdgs.uispecs.UISpecs;

@Entity
@Table(name = "pageimages")
public class PageImages {

	@Id
	@SequenceGenerator(name = "pageImagesSequence", sequenceName = "pageimages_id_seq", allocationSize = 1,
	initialValue = 6)
	@GeneratedValue(generator = "pageImagesSequence")
	@Column(name = "pageimage_id")
	private Integer pageImageId;

	@Column(name = "pageimage_name", length = 30)
	private String pageImageName;

	@Column(name = "picture", length = 100)
	private String picture;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "program_id")
	private Programs program;
	
//	@OneToMany(
//			fetch = FetchType.EAGER,
//			mappedBy = "uispecs",
//			cascade = CascadeType.ALL,
//			orphanRemoval = true
//			)
//	private List<UISpecs> uispec;
//	
//	
//	public List<UISpecs> getUispec() {
//		return uispec;
//	}
//
//	public void setUispec(List<UISpecs> uispec) {
//		this.uispec = uispec;
//	}

	public Integer getPageImageId() {
		return pageImageId;
	}

	public void setPageImageId(Integer pageImageId) {
		this.pageImageId = pageImageId;
	}

	public String getPageImageName() {
		return pageImageName;
	}

	public void setPageImageName(String pageImageName) {
		this.pageImageName = pageImageName;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Programs getProgram() {
		return program;
	}

	public void setProgram(Programs program) {
		this.program = program;
	}
	

	
	
	



}
