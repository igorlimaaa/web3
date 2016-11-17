/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fuctura.dao;

import br.com.fuctura.model.TipoProduto;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author aula
 */
public class TipoProdutoDAO implements Serializable {

    private SessionFactory factory;

    public TipoProdutoDAO() {
        factory = HibernateUtil.getSessionFactory();
    }

    public List<TipoProduto> pesquisarTodos() {
        try {
            Session session = factory.openSession();
            Criteria criteria = session.createCriteria(TipoProduto.class);
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
