package br.com.fuctura.service;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author igor_
*/


@ApplicationPath("servico")
public class App extends Application{
    
    @Override
    public Set<Class<?>> getClasses(){
        Set<Class<?>> set = new HashSet<Class<?>>();
        set.add(OlaMundo.class);
        return set;
    }
}
