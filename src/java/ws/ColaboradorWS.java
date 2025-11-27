
package ws;

import dominio.ColaboradorImp;
import dto.RSColaborador;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("autenticacion")
public class ColaboradorWS {
    private RSColaborador autenticarColaborador(){
        return null;
    }
    
    @Path("administracion/colaborador")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RSColaborador autenticacionColaborador(
        @FormParam("noPersonal") String  noPersonal,
        @FormParam("password") String  password ){
        if(noPersonal!= null && !noPersonal.isEmpty()&&
                (password != null && !password.isEmpty())){
            return ColaboradorImp.autenticarAdministracion(noPersonal, password);
        }
        throw new BadRequestException();
    }
}
