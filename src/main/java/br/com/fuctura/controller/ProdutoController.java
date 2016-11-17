/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fuctura.controller;

import br.com.fuctura.dao.ProdutoDAO;
import br.com.fuctura.dao.TipoProdutoDAO;
import br.com.fuctura.model.Produto;
import br.com.fuctura.model.TipoProduto;
import br.com.fuctura.relatorio.Relatorio;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author aula
 */
@ManagedBean
@SessionScoped
public class ProdutoController {

    private Produto produto;
    private ProdutoDAO produtoDAO;
    private List<Produto> produtoList;
    private List<String> images;
    private Relatorio relatorio;
    private TipoProdutoDAO tipoProdutoDAO;
    public static List<TipoProduto> tipos;
    private TipoProduto tipoProduto;

    public ProdutoController() {
        produtoDAO = new ProdutoDAO();
        relatorio = new Relatorio();
        tipoProdutoDAO = new TipoProdutoDAO();
        tipos = new ArrayList<TipoProduto>();
    }

    private void lista() {
        tipos.clear();
        for (TipoProduto p : tipoProdutoDAO.pesquisarTodos()) {
            tipos.add(p);
        }
    }

    public List<Produto> getProdutoList() {
        produtoList = produtoDAO.pesquisarTodos();
        return produtoList;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    // Incluir novo produto
    public void novo(ActionEvent actionEvent) {
        lista();
        produto = new Produto();
    }

    // Incluir novo produto
    public void gravar(ActionEvent actionEvent) {
        produto.setTipoProduto(tipoProduto);
        produtoDAO.inserir(produto);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("dialogCadastrarProduto.hide()");
    }

  
    // Alterar novo produto
    public void inicioAlterarProduto(ActionEvent actionEvent) {
       //tipoProduto = produto.getTipoProduto();
      
    }
    
    // Alterar novo produto
    public void fimAlterarProduto(ActionEvent actionEvent) {
        produtoDAO.atualizar(produto);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("dialogCadastrarProduto.hide()");
    }

    // Excluir novo produto
    public void excluirProduto(ActionEvent actionEvent) {
        produtoDAO.excluir(produto);
    }

    public void doUpload(FileUploadEvent fileUploadEvent) {
        try {

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ServletContext context = (ServletContext) facesContext.getExternalContext().getContext();

            String realPath = context.getRealPath("/");

            // Aqui cria o diretorio caso não exista 
            File file = new File(realPath + "/resources/imagens/" + produto.getCodigo() + "/");
            file.mkdirs();

            InputStream inputStream = fileUploadEvent.getFile().getInputstream();

            OutputStream out = new FileOutputStream(new File(file,
                    fileUploadEvent.getFile().getFileName()));

            int read = 0;

            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {

                out.write(bytes, 0, read);

            }

            inputStream.close();

            out.flush();

            out.close();

        } catch (Exception ex) {
            System.out.println("Erro no upload de imagem" + ex);
        }
    }

    public void visualizarImagem() {
        getImages();
    }

    public List<String> getImages() {
        if (produto == null) {
            return null;
        }
        File arrayArquivos[] = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext context = (ServletContext) facesContext.getExternalContext().getContext();
        String realPath = context.getRealPath("/");
        File file = new File(realPath + "/resources/imagens/" + produto.getCodigo() + "/");
        if (file.isDirectory()) {
            arrayArquivos = file.listFiles();

            images = new ArrayList<String>();
            for (int i = 0; i < arrayArquivos.length; i++) {
                images.add(arrayArquivos[i].getName());
            }

        }
        return images;
    }

    public void gerarRelatorio() {
        try {
            List<TipoProduto> listagemResultado = tipoProdutoDAO.pesquisarTodos();
            HashMap paramRel = new HashMap();
            String nomeRelatorio = "relTipoProduto";
            relatorio.gerarRelatorio(nomeRelatorio, paramRel, listagemResultado);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  
    public void gerarRelatorioProduto() {
        try {
            List<Produto> listagemResultado = produtoDAO.pesquisarTodos();
            HashMap paramRel = new HashMap();
            String nomeRelatorio = "relProduto2";
            relatorio.gerarRelatorio(nomeRelatorio, paramRel, listagemResultado);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   public void gerarGraficoProduto() {
        try {
            List<Produto> listagemResultado = produtoDAO.pesquisarTodos();
            HashMap paramRel = new HashMap();
            String nomeRelatorio = "relGrafico";
            relatorio.gerarRelatorio(nomeRelatorio, paramRel, listagemResultado);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public List<TipoProduto> getTipos() {
        return tipos;
    }
    
    
    
    

}
