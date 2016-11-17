/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fuctura.dao;

import br.com.fuctura.model.Usuario;
import java.io.Serializable;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author aula
 */
public class UsuarioDAO implements Serializable{

    private SessionFactory factory;

    public UsuarioDAO() {
        factory = HibernateUtil.getSessionFactory();
    }

    public boolean fazerLogin(Usuario usuario) {
        try {
            Session session = factory.openSession();
            Criteria criteria = session.createCriteria(Usuario.class);

            Criterion login = Restrictions.eq("login", usuario.getLogin());
            Criterion senha = Restrictions.eq("senha", usuario.getSenha());
           
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(login);
            conjunction.add(senha);
            criteria.add(conjunction);
            Usuario usr = (Usuario) criteria.uniqueResult();
           
            return (usr != null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
