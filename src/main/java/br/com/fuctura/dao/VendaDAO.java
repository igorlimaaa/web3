/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fuctura.dao;

import br.com.fuctura.model.ItensVenda;
import br.com.fuctura.model.Venda;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author aula
 */
public class VendaDAO {

    private SessionFactory factory;

    public VendaDAO() {
        factory = HibernateUtil.getSessionFactory();
    }

    
    public List<Venda> getList() {
        try {
            Session session = factory.openSession();
            Criteria criteria = session.createCriteria(Venda.class);
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
 
    public List<Venda> getList(int codigo) {
        try {
            Session session = factory.openSession();
            Criteria criteria = session.createCriteria(Venda.class);
            criteria.add(Restrictions.eq("codigo", codigo)); 
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Venda getVenda(int id) {
        try {
            Session session = factory.openSession();
            return (Venda) session.get(Venda.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int save(Venda venda, List<ItensVenda> listItensVenda) {
        Session session = factory.openSession();
        try {
            double total = 0;
            session.beginTransaction();
            venda.setDataVenda(new Date());
            session.save(venda);

            for (ItensVenda itensVenda : listItensVenda) {
                total += (itensVenda.getQuantidade() * 
                        itensVenda.getProduto().getPreco());
                itensVenda.setVenda(venda);
                session.save(itensVenda);
            }
            venda.setTotal(total);
            session.update(venda);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return venda.getCodigo();
    }

    
}
