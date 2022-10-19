package th.co.cdgs.status;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("status")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class StatusResource {

    @Inject
    EntityManager entityManager;

    @GET
    public List<Status> get() {
        return entityManager.createQuery("from Status", Status.class).getResultList();
    }

}