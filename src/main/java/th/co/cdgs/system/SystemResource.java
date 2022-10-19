package th.co.cdgs.system;

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


@Path("system")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class SystemResource {

    @Inject
    EntityManager entityManager;
    
    @GET
    public List<System> get() {
        return entityManager.createQuery("from System", System.class).getResultList();
    }
    
    @GET
	@Path("{id}")
	public System getSingle(@PathParam("id") Integer id) {
		System entity = entityManager.find(System.class, id);

		if (entity == null) {
			throw new WebApplicationException("System with id of " + id + " does not exist.", Status.NOT_FOUND);
		}
		return entity;
	}

	@POST
	@Transactional
	public Response create(System system) {
		if (system.getSystemId() != null) {
			system.setSystemId(null);
		}
		entityManager.persist(system);
		return Response.status(Status.CREATED).entity(system).build();
	}

	@PUT
	@Path("{id}")
	@Transactional
	public Response update(@PathParam("id") Integer id, System system) {
		System entity = entityManager.find(System.class, id);
		if (entity == null) {
			throw new WebApplicationException("System with id of " + id + " does not exist.", Status.NOT_FOUND);
		}
		entity.setSystemName(system.getSystemName());
		return Response.ok(entity).build();
	}

	@DELETE
	@Path("{id}")
	@Transactional
	public Response delete(@PathParam("id") Integer id) {
		System entity = entityManager.getReference(System.class, id);
		if (entity == null) {
			throw new WebApplicationException("System with id of " + id + " does not exist.", Status.NOT_FOUND);
		}
		entityManager.remove(entity);
		return Response.ok().build();
	}

	@DELETE
	@Transactional
	public Response deleteList(List<Integer> ids) {
		for (Integer id : ids) {
			System entity = entityManager.getReference(System.class, id);
			if (entity == null) {
				throw new WebApplicationException("System with id of " + id + " does not exist.", Status.NOT_FOUND);
			}
			entityManager.remove(entity);
		}
		return Response.ok().build();
	}
    
}

