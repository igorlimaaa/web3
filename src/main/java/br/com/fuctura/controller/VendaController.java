/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fuctura.controller;

import br.com.fuctura.dao.ItensVendaDAO;
import br.com.fuctura.dao.ProdutoDAO;
import br.com.fuctura.dao.VendaDAO;
import br.com.fuctura.model.ItensVenda;
import br.com.fuctura.model.Produto;
import br.com.fuctura.model.Venda;
import br.com.fuctura.relatorio.Relatorio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author aula
 */
@ManagedBean
@SessionScoped
public class VendaController implements Serializable {

    private Venda venda;
    private ItensVenda itensVenda;
    private Produto produto;

    private VendaDAO vendaDAO;
    private ProdutoDAO produtoDAO;
    private ItensVendaDAO itensVendaDAO;

    private List<Venda> vendaList;
    private List<ItensVenda> carrinho;
    public static List<Produto> PRODUTOS;
    private Relatorio relatorio;

    private Boolean desabilitar = true;
    private String tipoRel;

    public VendaController() {
        vendaDAO = new VendaDAO();
        produtoDAO = new ProdutoDAO();
        itensVendaDAO = new ItensVendaDAO();
        PRODUTOS = new ArrayList<Produto>();
        relatorio = new Relatorio();
    }

    private void lista() {
        PRODUTOS.clear();
        for (Produto p : produtoDAO.pesquisarTodos()) {
            PRODUTOS.add(p);
        }
    }

    public void novo(ActionEvent actionEvent) {
        lista();
        carrinho = new ArrayList<ItensVenda>();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formCadastro:tabProduto");
        context.update("formCadastro:produto");
        venda = new Venda();
        itensVenda = new ItensVenda();
    }

    public void gravar(ActionEvent actionEvent) {
        boolean gravar = true;
        String descProduto = "";
        for (ItensVenda iv : carrinho) {
            descProduto = iv.getProduto().getDescricao();
            if (iv.getQuantidade() > iv.getProduto().getQuantidade()) {
                gravar = false;
                break;
            }
        }
        if (gravar) {
            vendaDAO.save(venda, carrinho);
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("dialogCadastrarVenda.hide()");
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Produto fora de estoque: " + descProduto, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public List<ItensVenda> adicionarProdutos() {
        ItensVenda iv = new ItensVenda();
        iv.setProduto(produto);
        iv.setQuantidade(itensVenda.getQuantidade());
        carrinho.add(iv);
        desabilitar = false;
        return carrinho;
    }

    public void gerarRelatorio() {
        List<Venda> listaVenda = null;
        listaVenda = vendaDAO.getList(venda.getCodigo());

        List<ItensVenda> listagemSubRel = itensVendaDAO.getListByVenda(venda);
        HashMap paramRel = new HashMap();
        paramRel.put("titulo", "Venda: " + venda.getCodigo());
        String nomeRelatorio = "relVendas";
        String subNomeRelatorio = "relItensVenda";
        relatorio.gerarRelatorioSub(nomeRelatorio, paramRel, listaVenda, 
                listagemSubRel, subNomeRelatorio, tipoRel);
    }

    public List<Produto> getProdutos() {
        return PRODUTOS;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public List<Venda> getVendaList() {
        vendaList = vendaDAO.getList();
        return vendaList;
    }

    public List<ItensVenda> getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(List<ItensVenda> carrinho) {
        this.carrinho = carrinho;
    }

    public ItensVenda getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(ItensVenda itensVenda) {
        this.itensVenda = itensVenda;
    }

    public Boolean getDesabilitar() {
        return desabilitar;
    }

    public void setDesabilitar(Boolean desabilitar) {
        this.desabilitar = desabilitar;
    }

    public String getTipoRel() {
        return tipoRel;
    }

    public void setTipoRel(String tipoRel) {
        this.tipoRel = tipoRel;
    }
    
    

}
