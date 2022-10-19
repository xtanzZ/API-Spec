package th.co.cdgs.uispecs;

import javax.ws.rs.QueryParam;

public class UISpecsBeanParam {
	
	@QueryParam(value = "pageImageName")
    private String pageImageName;

	public String getPageImageName() {
		return pageImageName;
	}

	public void setPageImageName(String pageImageName) {
		this.pageImageName = pageImageName;
	}

	
}
