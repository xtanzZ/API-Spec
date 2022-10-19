package th.co.cdgs.pageimages;

import javax.ws.rs.QueryParam;

public class PageImagesBeanParam {
	
	@QueryParam(value = "programName")
    private String programName;

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}
	
	
}
