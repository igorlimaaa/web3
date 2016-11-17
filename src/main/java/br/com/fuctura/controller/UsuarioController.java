/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fuctura.controller;

import br.com.fuctura.dao.UsuarioDAO;
import br.com.fuctura.model.Usuario;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author aula
 */
@Named
@RequestScoped
public class UsuarioController implements Serializable {

    @Inject
    private Usuario usuario;

    @Inject
    private UsuarioDAO usuarioDAO;

    public String fazerLogin() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (usuarioDAO.fazerLogin(usuario)) {
            usuario.setLogado(true);
            return "/index?faces-redirect=true";
        } else {
            usuario.setLogado(false);
            FacesMessage mensagem = new FacesMessage("Usuário/senha "
                    + "inválidos!");
            mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, mensagem);
        }
        return null;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        return "/login?faces-redirect=true";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
