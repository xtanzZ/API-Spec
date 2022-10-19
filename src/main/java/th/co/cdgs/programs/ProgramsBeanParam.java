package th.co.cdgs.programs;

import javax.ws.rs.QueryParam;

public class ProgramsBeanParam {
	
	 	@QueryParam(value = "programName")
	    private String programName;

	    @QueryParam(value = "systemAnalystName")
	    private String systemAnalystName;

	    @QueryParam(value = "statusId")
	    private Integer statusId;

		
		
		public String getProgramName() {
			return programName;
		}

		public void setProgramName(String programName) {
			this.programName = programName;
		}

		public String getSystemAnalystName() {
			return systemAnalystName;
		}

		public void setSystemAnalystName(String systemAnalystName) {
			this.systemAnalystName = systemAnalystName;
		}

		public Integer getStatusId() {
			return statusId;
		}

		public void setStatusId(Integer statusId) {
			this.statusId = statusId;
		}

}
