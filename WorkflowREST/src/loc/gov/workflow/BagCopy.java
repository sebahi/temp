package loc.gov.workflow;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/copy")
public class BagCopy {

	@POST
    public void bagCopy(@FormParam("bagId") String bagId, @FormParam("numberOfCopies") String numberOfCopies, @HeaderParam("Content-Type") String contentType) {
		System.out.println("There bag "+bagId+ " have been copied successfully!");
		System.out.println("There are "+numberOfCopies+ " copies have been printed.");
    }
}
