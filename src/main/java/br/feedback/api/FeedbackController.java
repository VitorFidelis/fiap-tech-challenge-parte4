package br.feedback.api;

import br.feedback.domain.Feedback;
import br.feedback.dto.FeedbackRequest;
import br.feedback.service.FeedbackService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

@Path("/feedbacks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FeedbackController {

    @Inject
    private FeedbackService feedbackService;

    private static final org.jboss.logging.Logger LOGGER = org.jboss.logging.Logger.getLogger("AppLifeCycleBean");

    @POST
    public Response criar(FeedbackRequest request){

        LOGGER.log(Logger.Level.INFO, "Criando avaliacao");
        try {
            Feedback feedback = feedbackService.avaliar(request.descricao(), request.nota());
            return Response.status(Response.Status.CREATED).entity(feedback).build();
        } catch (IllegalArgumentException ex){
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

}
