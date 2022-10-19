package th.co.cdgs.programs;

import java.util.List;

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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;




@Path("programs")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ProgramsResource {

    @Inject
    EntityManager entityManager;

    @GET
    public List<Programs> get() {
        return entityManager.createQuery("from Programs", Programs.class).getResultList();
    }
    
    @GET
    @Path("{id}")
    public Programs getSingle(@PathParam("id") Integer id) {
    	Programs entity = entityManager.find(Programs.class, id);

        if (entity == null) {
            throw new WebApplicationException("Programs with id of " + id + " does not exist.",
                    Status.NOT_FOUND);
        }
        return entity;
    }
    

    @GET
    @Path("queryProgramsByCondition")
    public List<Programs> getByCondition(@BeanParam ProgramsBeanParam condition) {
        StringBuilder jpql = new StringBuilder("from Programs e where 1=1 ");
        if (condition.getProgramName() != null) {
            jpql.append("and e.programName like :programName ");
        }
        if (condition.getSystemAnalystName() != null) {
            jpql.append("and e.systemAnalyst.systemAnalystName like :systemAnalystName ");
        }
        if (condition.getStatusId() != null) {
            jpql.append("and e.status.statusId = :statusId ");
        }
        Query query = entityManager.createQuery(jpql.toString(), Programs.class);
        if (condition.getProgramName() != null) {
            query.setParameter("programName", condition.getProgramName());
        }
        if (condition.getSystemAnalystName() != null) {
            query.setParameter("systemAnalystName", condition.getSystemAnalystName());
        }
        if (condition.getStatusId() != null) {
            query.setParameter("statusId", condition.getStatusId());
        }
        @SuppressWarnings("unchecked")
		final List<Programs> rs = query.getResultList();
        return rs;
    }

    @POST
    @Transactional
    public Response create(Programs programs) {
        if (programs.getProgramId() != null) {
        	programs.setProgramId(null);
        }
        entityManager.persist(programs);
        return Response.status(Status.CREATED).entity(programs).build();
    }
    
    @PUT
    @Path("{id}")
    @Transactional
    public Response update(@PathParam("id") Integer id, Programs programs) {
    	Programs entity = entityManager.find(Programs.class, id);
        if (entity == null) {
            throw new WebApplicationException("Programs with id of " + id + " does not exist.",
                    Status.NOT_FOUND);
        }
        entity.setProgramName(programs.getProgramName());
        entity.setProject(programs.getProject());
        entity.setSystem(programs.getSystem());
        entity.setSystemAnalyst(programs.getSystemAnalyst());
        entity.setStatus(programs.getStatus());
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") Integer id) {
    	Programs entity = entityManager.getReference(Programs.class, id);
        if (entity == null) {
            throw new WebApplicationException("Programs with id of " + id + " does not exist.",
                    Status.NOT_FOUND);
        }
        entityManager.remove(entity);
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    public Response deleteList(List<Integer> ids) {
        for (Integer id : ids) {
        	Programs entity = entityManager.getReference(Programs.class, id);
            if (entity == null) {
                throw new WebApplicationException(
                        "Programs with id of " + id + " does not exist.", Status.NOT_FOUND);
            }
            entityManager.remove(entity);
        }
        return Response.ok().build();
    }

}
