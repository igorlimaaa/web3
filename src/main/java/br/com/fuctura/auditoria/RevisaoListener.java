/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fuctura.auditoria;

import org.hibernate.envers.RevisionListener;

/**
 *
 * @author aula
 */
public class RevisaoListener implements RevisionListener{

    @Override
    public void newRevision(Object revisionEntity) {
         Revisao revisao = (Revisao) revisionEntity;
         revisao.setUsuario(10);
    };
    
}
