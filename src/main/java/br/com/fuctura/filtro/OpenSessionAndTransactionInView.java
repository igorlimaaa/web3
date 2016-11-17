package br.com.fuctura.filtro;

import br.com.fuctura.dao.HibernateUtil;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author aula
 */
@WebFilter("/*")
public class OpenSessionAndTransactionInView implements Filter {

    private SessionFactory factory;

    public OpenSessionAndTransactionInView() {
        factory = HibernateUtil.getSessionFactory();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
      
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       // System.out.println("FILTRO DAO");
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            chain.doFilter(request, response);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
          //  System.out.println("FECHOU SESSION");
            session.close();

        }
    }

    @Override
    public void destroy() {
      
    }

}
