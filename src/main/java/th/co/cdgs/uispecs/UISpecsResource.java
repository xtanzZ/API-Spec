package th.co.cdgs.uispecs;

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



@Path("uispecs")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class UISpecsResource {
	
	@Inject
    EntityManager entityManager;
	
	  	@GET
	    public List<UISpecs> get() {
	        return entityManager.createQuery("from UISpecs", UISpecs.class).getResultList();
	    }

	    @GET
	    @Path("{id}")
	    public UISpecs getSingle(@PathParam("id") Integer id) {
	    	UISpecs entity = entityManager.find(UISpecs.class, id);

	        if (entity == null) {
	            throw new WebApplicationException("UISpecs with id of " + id + " does not exist.",
	                    Status.NOT_FOUND);
	        }
	        return entity;
	    }
	    
	    @GET
	    @Path("queryUISpecsByCondition")
	    public List<UISpecs> getByCondition(@BeanParam UISpecsBeanParam condition) {
	        StringBuilder jpql = new StringBuilder("from UISpecs e where 1=1 ");
	        if (condition.getPageImageName() != null) {
	            jpql.append("and e.pageimage.pageImageName like :pageImageName ");
	        }
	        Query query = entityManager.createQuery(jpql.toString(), UISpecs.class);
	        if (condition.getPageImageName() != null) {
	            query.setParameter("pageImageName", condition.getPageImageName());
	        }
	        @SuppressWarnings("unchecked")
			final List<UISpecs> rs = query.getResultList();
	        return rs;
	    }
	    
	    @POST
	    @Transactional
	    public Response create(UISpecs uispecs) {
	        if (uispecs.getUispecId() != null) {
	        	uispecs.setUispecId(null);
	        }
	        entityManager.persist(uispecs);
	        return Response.status(Status.CREATED).entity(uispecs).build();
	    }

	    @PUT
	    @Path("{id}")
	    @Transactional
	    public Response update(@PathParam("id") Integer id, UISpecs uispecs) {
	    	UISpecs entity = entityManager.find(UISpecs.class, id);
	        if (entity == null) {
	            throw new WebApplicationException("UISpecs with id of " + id + " does not exist.",
	                    Status.NOT_FOUND);
	        }
	        entity.setLabel(uispecs.getLabel());
	        entity.setAttribure(uispecs.getAttribure());
	        entity.setFormType(uispecs.getFormType());
	        entity.setDetail(uispecs.getDetail());
	        entity.setEvent(uispecs.getEvent());
	        entity.setPageimage(uispecs.getPageimage());
	        entity.setService(uispecs.getService());
	        
	        return Response.ok(entity).build();
	    }

	    @DELETE
	    @Path("{id}")
	    @Transactional
	    public Response delete(@PathParam("id") Integer id) {
	    	UISpecs entity = entityManager.getReference(UISpecs.class, id);
	        if (entity == null) {
	            throw new WebApplicationException("UISpecs with id of " + id + " does not exist.",
	                    Status.NOT_FOUND);
	        }
	        entityManager.remove(entity);
	        return Response.ok().build();
	    }

	    @DELETE
	    @Transactional
	    public Response deleteList(List<Integer> ids) {
	        for (Integer id : ids) {
	        	UISpecs entity = entityManager.getReference(UISpecs.class, id);
	            if (entity == null) {
	                throw new WebApplicationException(
	                        "UISpecs with id of " + id + " does not exist.", Status.NOT_FOUND);
	            }
	            entityManager.remove(entity);
	        }
	        return Response.ok().build();
	    }

}
