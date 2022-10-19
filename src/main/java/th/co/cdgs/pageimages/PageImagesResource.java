package th.co.cdgs.pageimages;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.BeanParam;
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

import th.co.cdgs.programs.Programs;
import th.co.cdgs.programs.ProgramsBeanParam;
import th.co.cdgs.uispecs.UISpecs;

@Path("pageimages")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class PageImagesResource {

	@Inject
	EntityManager entityManager;

	@GET
	public List<PageImages> get() {
		return entityManager.createQuery("from PageImages", PageImages.class).getResultList();
	}

	@GET
	@Path("{id}")
	public PageImages getSingle(@PathParam("id") Integer id) {
		PageImages entity = entityManager.find(PageImages.class, id);

		if (entity == null) {
			throw new WebApplicationException("PageImages with id of " + id + " does not exist.", Status.NOT_FOUND);
		}
		return entity;
	}

	@GET
	@Path("queryPageImagesByCondition")
	public List<PageImages> getByCondition(@BeanParam PageImagesBeanParam condition) {
		StringBuilder jpql = new StringBuilder("from PageImages e where 1=1 ");
		if (condition.getProgramName() != null) {
			jpql.append("and e.program.programName like :programName ");
		}
		Query query = entityManager.createQuery(jpql.toString(), PageImages.class);
		if (condition.getProgramName() != null) {
			query.setParameter("programName", condition.getProgramName());
		}
		@SuppressWarnings("unchecked")
		final List<PageImages> rs = query.getResultList();
		return rs;
	}

	@GET
	@Path("copy/{programId}")
	@Transactional
	public Response copy(@PathParam("programId") Integer programId) {

		Programs pro = entityManager.createQuery("from Programs pro where pro.programId = :programId", Programs.class)
				.setParameter("programId", programId).getSingleResult();
		Programs program = createProgram(pro);

		List<PageImages> piList = entityManager
				.createQuery("from PageImages pi where pi.program.programId = :programId", PageImages.class)
				.setParameter("programId", programId).getResultList();

		for (PageImages pi : piList) {
			PageImages pageImage = createPageImages(pi, program);
			List<UISpecs> uiList = entityManager
					.createQuery("from UISpecs ui where ui.pageimage.pageImageId = :pageImageId", UISpecs.class)
					.setParameter("pageImageId", pi.getPageImageId()).getResultList();
			for (UISpecs ui : uiList) {
				createUISpecs(ui, pageImage);
			}

		}

		return Response.ok().build();
	}

	private Programs createProgram(Programs programs) {
		Programs p = new Programs();
		p.setProgramName(programs.getProgramName());
		p.setProject(programs.getProject());
		p.setStatus(programs.getStatus());
		p.setSystem(programs.getSystem());
		p.setSystemAnalyst(programs.getSystemAnalyst());
		entityManager.persist(p);
		return p;
	}

	private PageImages createPageImages(PageImages pageimages, Programs program) {
		PageImages pi = new PageImages();
		pi.setPageImageName(pageimages.getPageImageName());
		pi.setPicture(pageimages.getPicture());
		pi.setProgram(program);
		entityManager.persist(pi);
		return pi;
	}

	private UISpecs createUISpecs(UISpecs uispecs, PageImages pageImage) {
		UISpecs ui = new UISpecs();
		ui.setLabel(uispecs.getLabel());
		ui.setAttribure(uispecs.getAttribure());
		ui.setFormType(uispecs.getFormType());
		ui.setDetail(uispecs.getDetail());
		ui.setEvent(uispecs.getEvent());
		ui.setPageimage(uispecs.getPageimage());
		ui.setService(uispecs.getService());
		ui.setPageimage(pageImage);
		entityManager.persist(ui);
		return ui;
	}

	@POST
	@Transactional
	public Response create(PageImages pageimages) {
		if (pageimages.getPageImageId() != null) {
			pageimages.setPageImageId(null);
		}
		entityManager.persist(pageimages);
		return Response.status(Status.CREATED).entity(pageimages).build();
	}

	@PUT
	@Path("{id}")
	@Transactional
	public Response update(@PathParam("id") Integer id, PageImages pageimages) {
		PageImages entity = entityManager.find(PageImages.class, id);
		if (entity == null) {
			throw new WebApplicationException("PageImages with id of " + id + " does not exist.", Status.NOT_FOUND);
		}
		entity.setPageImageName(pageimages.getPageImageName());
		entity.setPicture(pageimages.getPicture());
		entity.setProgram(pageimages.getProgram());
		return Response.ok(entity).build();
	}

	@DELETE
	@Path("{id}")
	@Transactional
	public Response delete(@PathParam("id") Integer id) {
		PageImages entity = entityManager.getReference(PageImages.class, id);
		if (entity == null) {
			throw new WebApplicationException("PageImages with id of " + id + " does not exist.", Status.NOT_FOUND);
		}
		entityManager.remove(entity);
		return Response.ok().build();
	}

	@DELETE
	@Transactional
	public Response deleteList(List<Integer> ids) {
		for (Integer id : ids) {
			PageImages entity = entityManager.getReference(PageImages.class, id);
			if (entity == null) {
				throw new WebApplicationException("PageImages with id of " + id + " does not exist.", Status.NOT_FOUND);
			}
			entityManager.remove(entity);
		}
		return Response.ok().build();
	}
	// upload

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
