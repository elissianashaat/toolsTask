package app;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


import ejbs.Calculation;

@Stateless
@Path("cal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CalculationService {

	@PersistenceContext(unitName = "elissia")
	private EntityManager entityManager;
    @POST
    @Path("calc")
    public String createCalculation(Calculation calculation) {
       
            int result;
            switch (calculation.getOperation()) {
                case "+":
                    result = calculation.getNumber1() + calculation.getNumber2();
                    break;
                case "-":
                    result = calculation.getNumber1() - calculation.getNumber2();
                    break;
                case "*":
                    result = calculation.getNumber1() * calculation.getNumber2();
                    break;
                case "/":
                    result = calculation.getNumber1() / calculation.getNumber2();
                    break;
               default: 
            	   throw new IllegalArgumentException("invalid operation");
            	   
            }
            entityManager.persist(calculation);
            return "result: " + result; 
    }
    
	@GET
    @Path("calculations")
    public List<Calculation> getAllCalculations() {
        try {
        	
            
            TypedQuery<Calculation> query = entityManager.createQuery("SELECT c FROM calculation c", Calculation.class);

            
            List<Calculation> calculations = query.getResultList();
            return calculations;
        } catch (Exception e) {
            e.printStackTrace();
            
            return null;
        }
        }
}

