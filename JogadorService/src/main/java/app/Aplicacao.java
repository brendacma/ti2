package app;

// dependencias
import static spark.Spark.*;
import service.JogadorService;

public class Aplicacao 
{
	private static JogadorService jogadorService = new JogadorService( );
	
    public static void main(String[] args) 
    {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/jogador/insert", (request, response) -> jogadorService.insert(request, response));

        get("/jogador/:codigo", (request, response) -> jogadorService.get(request, response));
        
        get("/jogador/list/:orderby", (request, response) -> jogadorService.getAll(request, response));

        get("/jogador/update/:codigo", (request, response) -> jogadorService.getToUpdate(request, response));
        
        post("/jogador/update/:codigo", (request, response) -> jogadorService.update(request, response));
        
        get("/jogador/delete/:codigo", (request, response) -> jogadorService.delete(request, response));
            
    } // end main ( )
} // end class ( )