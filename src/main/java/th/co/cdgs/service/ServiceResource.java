package th.co.cdgs.service;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("service")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ServiceResource {

	@Inject
	EntityManager entityManager;

	@GET
	public List<Service> get() {
		return entityManager.createQuery("from Service", Service.class).getResultList();
	}

	@GET
	@Path("{id}")
	public Service getSingle(@PathParam("id") Integer id) {
		Service entity = entityManager.find(Service.class, id);

		if (entity == null) {
			throw new WebApplicationException("Service with id of " + id + " does not exist.", Status.NOT_FOUND);
		}
		return entity;
	}

	@POST
	@Transactional
	public Response create(Service service) {
		if (service.getServiceId() != null) {
			service.setServiceId(null);
		}
		entityManager.persist(service);
		return Response.status(Status.CREATED).entity(service).build();
	}

	@PUT
	@Path("{id}")
	@Transactional
	public Response update(@PathParam("id") Integer id, Service service) {
		Service entity = entityManager.find(Service.class, id);
		if (entity == null) {
			throw new WebApplicationException("Service with id of " + id + " does not exist.", Status.NOT_FOUND);
		}
		entity.setMethodName(service.getMethodName());
		entity.setInputParameter(service.getInputParameter());
		entity.setExampleResponse(service.getExampleResponse());
		entity.setExamplePicture(service.getExamplePicture());
		entity.setDetail(service.getDetail());
		return Response.ok(entity).build();
	}

	@DELETE
	@Path("{id}")
	@Transactional
	public Response delete(@PathParam("id") Integer id) {
		Service entity = entityManager.getReference(Service.class, id);
		if (entity == null) {
			throw new WebApplicationException("Service with id of " + id + " does not exist.", Status.NOT_FOUND);
		}
		entityManager.remove(entity);
		return Response.ok().build();
	}

	@DELETE
	@Transactional
	public Response deleteList(List<Integer> ids) {
		for (Integer id : ids) {
			Service entity = entityManager.getReference(Service.class, id);
			if (entity == null) {
				throw new WebApplicationException("Service with id of " + id + " does not exist.", Status.NOT_FOUND);
			}
			entityManager.remove(entity);
		}
		return Response.ok().build();
	}
	
	//upload
	@POST
	@Path("upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA + ";charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON)
	public Response upload(@MultipartForm MultipartFormDataInput input) throws Exception {
		final Map<String, List<InputPart>> multipart = input.getFormDataMap();
		final String fileName = input.getFormDataPart("fileName", String.class, null);
		final List<InputPart> files = multipart.get("files");
		for (final InputPart inputPart : files) {
			final java.nio.file.Path tempFile = inputPart.getBody(File.class, null).toPath();
			final java.nio.file.Path file = Paths.get("c:/tm/" + fileName);
			if (Files.exists(file)) {
				Files.delete(file);
			}
			Files.copy(tempFile, file);
		}
		return Response.ok().build();
	}

	@GET
	@Path("image/{fileName}")
	public Response readFile(@PathParam(value = "fileName") String fileName) throws Exception {
		final java.nio.file.Path file = Paths.get("c:/tm/" + fileName);
		if (Files.exists(file)) {
			final StreamingOutput stream = (OutputStream output) -> Files.copy(file, output);
			return Response.ok(stream).header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
					.header(HttpHeaders.CONTENT_DISPOSITION, fileName).build();
		}
		return Response.noContent().build();
	}
}
