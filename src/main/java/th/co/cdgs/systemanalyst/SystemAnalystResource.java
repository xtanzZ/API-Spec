package th.co.cdgs.systemanalyst;

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

@Path("systemanalyst")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class SystemAnalystResource {
	 @Inject
	    EntityManager entityManager;
	    
	    @GET
	    public List<SystemAnalyst> get() {
	        return entityManager.createQuery("from SystemAnalyst", SystemAnalyst.class).getResultList();
	    }
	    
	    @GET
		@Path("{id}")
		public SystemAnalyst getSingle(@PathParam("id") Integer id) {
			SystemAnalyst entity = entityManager.find(SystemAnalyst.class, id);

			if (entity == null) {
				throw new WebApplicationException("SystemAnalyst with id of " + id + " does not exist.", Status.NOT_FOUND);
			}
			return entity;
		}

		@POST
		@Transactional
		public Response create(SystemAnalyst systemanalyst) {
			if (systemanalyst.getSystemAnalystId() != null) {
				systemanalyst.setSystemAnalystId(null);
			}
			entityManager.persist(systemanalyst);
			return Response.status(Status.CREATED).entity(systemanalyst).build();
		}

		@PUT
		@Path("{id}")
		@Transactional
		public Response update(@PathParam("id") Integer id, SystemAnalyst systemanalyst) {
			SystemAnalyst entity = entityManager.find(SystemAnalyst.class, id);
			if (entity == null) {
				throw new WebApplicationException("SystemAnalyst with id of " + id + " does not exist.", Status.NOT_FOUND);
			}
			entity.setSystemAnalystName(systemanalyst.getSystemAnalystName());
			return Response.ok(entity).build();
		}

		@DELETE
		@Path("{id}")
		@Transactional
		public Response delete(@PathParam("id") Integer id) {
			SystemAnalyst entity = entityManager.getReference(SystemAnalyst.class, id);
			if (entity == null) {
				throw new WebApplicationException("SystemAnalyst with id of " + id + " does not exist.", Status.NOT_FOUND);
			}
			entityManager.remove(entity);
			return Response.ok().build();
		}

		@DELETE
		@Transactional
		public Response deleteList(List<Integer> ids) {
			for (Integer id : ids) {
				SystemAnalyst entity = entityManager.getReference(SystemAnalyst.class, id);
				if (entity == null) {
					throw new WebApplicationException("SystemAnalyst with id of " + id + " does not exist.", Status.NOT_FOUND);
				}
				entityManager.remove(entity);
			}
			return Response.ok().build();
		}
}
