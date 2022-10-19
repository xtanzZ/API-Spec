package th.co.cdgs.mapper;

import java.util.HashMap;
import java.util.Map;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class ErrorMapper implements ExceptionMapper<Exception> {
	private static final Logger LOGGER = Logger.getLogger(ErrorMapper.class.getName());
//	@Inject
//	ObjectMapper objectMapper;

	@Override
	public Response toResponse(Exception exception) {
		LOGGER.error("Failed to handle request", exception);

		int code = 500;
		if (exception instanceof WebApplicationException) {
			code = ((WebApplicationException) exception).getResponse().getStatus();
		}

		Map<String,Object> exceptionJson = new HashMap<>();
		exceptionJson.put("exceptionType", exception.getClass().getName());
		exceptionJson.put("code", code);

		if (exception.getMessage() != null) {
			exceptionJson.put("error", exception.getMessage());
		}

		return Response.status(code).entity(exceptionJson).build();
	}

}
