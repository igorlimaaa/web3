package br.com.fuctura.dao;

import br.com.fuctura.model.Produto;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ClienteDAO {
    private SessionFactory factory;
    
    public ClienteDAO(){
        factory = HibernateUtil.getSessionFactory();
    }
    
    public void cadastrarCliente(Produto produto) {
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
    
    public void excluirCliente(Produto produto) {
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
    
    
}
