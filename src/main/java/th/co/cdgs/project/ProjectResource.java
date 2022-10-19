package th.co.cdgs.project;

import java.util.List;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


@Path("project")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ProjectResource {

    @Inject
    EntityManager entityManager;
    
    @GET
    public List<Project> get() {
        return entityManager.createQuery("from Project", Project.class).getResultList();
    }
    
    @GET
	@Path("{id}")
	public Project getSingle(@PathParam("id") Integer id) {
		Project entity = entityManager.find(Project.class, id);

		if (entity == null) {
			throw new WebApplicationException("Project with id of " + id + " does not exist.", Status.NOT_FOUND);
		}
		return entity;
	}

	@POST
	@Transactional
	public Response create(Project project) {
		if (project.getProjectId() != null) {
			project.setProjectId(null);
		}
		entityManager.persist(project);
		return Response.status(Status.CREATED).entity(project).build();
	}

	@PUT
	@Path("{id}")
	@Transactional
	public Response update(@PathParam("id") Integer id, Project project) {
		Project entity = entityManager.find(Project.class, id);
		if (entity == null) {
			throw new WebApplicationException("Project with id of " + id + " does not exist.", Status.NOT_FOUND);
		}
		entity.setProjectName(project.getProjectName());
		return Response.ok(entity).build();
	}

	@DELETE
	@Path("{id}")
	@Transactional
	public Response delete(@PathParam("id") Integer id) {
		Project entity = entityManager.getReference(Project.class, id);
		if (entity == null) {
			throw new WebApplicationException("Project with id of " + id + " does not exist.", Status.NOT_FOUND);
		}
		entityManager.remove(entity);
		return Response.ok().build();
	}

	@DELETE
	@Transactional
	public Response deleteList(List<Integer> ids) {
		for (Integer id : ids) {
			Project entity = entityManager.getReference(Project.class, id);
			if (entity == null) {
				throw new WebApplicationException("Project with id of " + id + " does not exist.", Status.NOT_FOUND);
			}
			entityManager.remove(entity);
		}
		return Response.ok().build();
	}
    
}

