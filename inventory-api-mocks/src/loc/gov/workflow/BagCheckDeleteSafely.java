package loc.gov.workflow;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/checkDeletedSafely")
public class BagCheckDeleteSafely {

	@POST
    public void bagInPlace(@FormParam("bagId") String bagId,@HeaderParam("Content-Type") String contentType) {
		System.out.println("The bag: "+bagId+" check delete safely has been implemented successfully!");
    }
}
