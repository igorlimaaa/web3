/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fuctura.dao;

import br.com.fuctura.model.Produto;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;

/**
 *
 * @author aula
 */
public class ProdutoDAO implements Serializable {

    private SessionFactory factory;

    public ProdutoDAO() {
        factory = HibernateUtil.getSessionFactory();
    }

    public void inserir(Produto produto) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.save(produto);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();

        }
    }

    public void excluir(Produto produto) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.delete(produto);
            session.getTransaction().commit();//executa o commit 
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public Produto getProduto(int id) {
        try {
            Session session = factory.openSession();
            return (Produto) session.get(Produto.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Produto> pesquisarTodos() {
        try {
            Session session = factory.openSession();
            Criteria criteria = session.createCriteria(Produto.class);
            criteria.createAlias("tipoProduto", "tipo");
            criteria.addOrder(Order.asc("tipoProduto"));
            criteria.addOrder(Order.asc("tipo.descricao"));
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void atualizar(Produto produto) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.update(produto);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

}
