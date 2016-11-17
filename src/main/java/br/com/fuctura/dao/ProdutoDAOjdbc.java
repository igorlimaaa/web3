/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fuctura.dao;

import br.com.fuctura.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aula
 */
public class ProdutoDAOjdbc {

    private Connection con;

    public ProdutoDAOjdbc() throws SQLException {
        Conexao conexao = new Conexao();
        this.con = conexao.getConexao();
        //new Conexao().getConexao();
    }

    public void inserir(Produto produto) {
        try {
            String sql = "insert into produto"
                    + " (codigo, descricao, preco, quantidade, data_cadastro) "
                    + "values (?,?,?,?,?)";
            PreparedStatement stm = con.prepareStatement(sql);
         
            stm.setInt(1, produto.getCodigo());
            stm.setString(2, produto.getDescricao());
            stm.setDouble(3, produto.getPreco());
            stm.setInt(4, produto.getQuantidade());
            java.sql.Date dataBanco = new java.sql.Date(
                    produto.getDataCadastro().getTime()); 
            stm.setDate(5, dataBanco);
            stm.execute();
    //        con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void atualizar(Produto produto) {
        try {
            String sql = "update produto set descricao = ?, preco = ?, quantidade = ? "
                    + " where codigo = ?";
            PreparedStatement stm = con.prepareStatement(sql);

            stm.setString(1, produto.getDescricao());
            stm.setDouble(2, produto.getPreco());
            stm.setInt(3, produto.getQuantidade());
            stm.setInt(4, produto.getCodigo());
            stm.execute();
//            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void excluir(Produto produto) {
        try {
            String sql = "delete from produto where codigo = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            
            stm.setInt(1, produto.getCodigo());
            stm.execute();
 //           con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public List<Produto> pesquisarTodos() {
        try {
            List<Produto> produtoList = new ArrayList<Produto>();
            String sql = "select * from produto";
            PreparedStatement stm = con.prepareStatement(sql);
            
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setCodigo(rs.getInt("codigo"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setDataCadastro(rs.getDate("data_cadastro"));
                produtoList.add(produto);
            }
          //  con.close();
            return produtoList;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
