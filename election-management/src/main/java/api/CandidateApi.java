package api;

import api.dto.out.Candidate;
import domain.CandidateService;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/candidates")
public class CandidateApi {
    private final CandidateService service;

    public CandidateApi(CandidateService service) {
        this.service = service;
    }

    @POST
    public void create(api.dto.in.CreateCandidate dto) {
        service.save(dto.toDomain());
    }

    @PUT
    @Path("/{id}")
    public Candidate update(@PathParam("id") String id, api.dto.in.UpdateCandidate dto) {
        service.save(dto.toDomain(id));
        return api.dto.out.Candidate.fromDomain(service.findById(id));
    }

    @GET
    @Produces("application/json")
    public List<Candidate> list(@QueryParam("page") @DefaultValue("0") int page, 
                                 @QueryParam("size") @DefaultValue("10") int size) {
        return service.findAll(page, size).stream().map(Candidate::fromDomain).toList();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        if (service.delete(id)) {
        	// Retorna 204 No Content se a remoção for bem-sucedida
            return Response.noContent().build(); 
        } else {
        	// Retorna 404 Not Found se o candidato não for encontrado
            return Response.status(Response.Status.NOT_FOUND).build(); 
        }
    }
}
