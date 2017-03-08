package loc.gov.workflow;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import loc.gov.model.Bag;

@Path("/bagId")
public class BagExistsCheck {

	@GET
    @Path("/{param}")
    public String bagExistCheck(@PathParam("param") String bagId) {
		System.out.println("GET Method Bag Id: "+bagId);
        Bag bag = new Bag();
        bag.setBagId("15");
        if(bag.getBagId() != null && !bag.getBagId().isEmpty()) {
        	if(bag.getBagId().contentEquals(bagId)){
        		return Boolean.TRUE.toString();
	        	}else{
	        		return Boolean.FALSE.toString();
	        	}
		}else {
			return Boolean.FALSE.toString();
		}
    }
	
	@POST
    //@Path("/{param}")
	@Consumes("application/x-www-form-urlencoded")
    public String bagExistCheck(@FormParam("bagId") String bagId,@HeaderParam("Content-Type") String contentType) {
		System.out.println("Receiving Request with Bag ID = " + bagId + ".");
        Bag bag = new Bag();
        bag.setBagId("15");
        if(bag.getBagId() != null && !bag.getBagId().isEmpty()) {
        	if(bag.getBagId().contentEquals(bagId)){
        		return Boolean.TRUE.toString();
	        	}else{
	        		return Boolean.FALSE.toString();
	        	}
		}else {
			return Boolean.FALSE.toString();
		}
    }
}
