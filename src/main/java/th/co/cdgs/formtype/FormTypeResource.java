package th.co.cdgs.formtype;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import th.co.cdgs.project.Project;

@Path("formtype")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class FormTypeResource {
	

    @Inject
    EntityManager entityManager;
    
    @GET
    public List<FormType> get() {
        return entityManager.createQuery("from FormType", FormType.class).getResultList();
    }     

}
