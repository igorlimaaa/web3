package br.com.fuctura.service;

import br.com.fuctura.dao.ProdutoDAO;
import br.com.fuctura.model.Produto;
import br.com.fuctura.model.TipoProduto;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


@Path("/olaMundo")
public class OlaMundo {
    
    private ProdutoDAO produtoDAO;
    
    public OlaMundo (){
        produtoDAO = new ProdutoDAO();
    }
    
    @GET
    @Path("/hello")
    @Produces("text/plain")
    public String Hello(){
        System.out.println("Fora isso tudo certo neh? ...");
        return "Hello World!!! Isto eh um teste\n";
    }  
    @GET
    @Path("/hello2")
    @Produces("text/plain")
    public String Hello2(){
        System.out.println("Fora isso tudo certo neh? ...");
        return "Hello World!!! Isto eh um teste2\n";
    }
    
    @GET
    @Path("/produtos")
    @Produces("application/json")
    public List<Produto> getProdutos(){
        System.out.println("teste3");
        return produtoDAO.pesquisarTodos();
    }   
    
    @GET
    @Path("/produtos/{id}")
    @Produces("application/json")
    @Consumes("text/xml")
    public Produto getProduto (@PathParam("id") int id){
        return produtoDAO.getProduto(id);
    }
    
    @POST
    @Path("/addProduto")
    @Consumes("application/json;charset=UTF-8")
    @Produces("application/json;charset=UTF-8")
    public String gravar (Produto produto){
        produtoDAO.inserir(produto);
        return "Produto Adicionado com Sucesso\n";
    }
    
}
