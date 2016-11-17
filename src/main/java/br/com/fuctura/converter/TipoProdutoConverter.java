/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fuctura.converter;

import br.com.fuctura.controller.ProdutoController;
import br.com.fuctura.model.TipoProduto;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;

/**
 *
 * @author aula
 */
@FacesConverter("tipoproduto")
public class TipoProdutoConverter implements Converter {

    
    @Override
    public Object getAsObject(FacesContext context, 
            UIComponent component,
            String value) {
          if (value != null) {
            for (TipoProduto tipo : ProdutoController.tipos) {
                if (value.equals(String.valueOf(tipo.getCodigo()))) {
                    return tipo;
                }
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        if (value != null && !value.equals("")) {
            TipoProduto tipo = (TipoProduto) value;
            return String.valueOf(tipo.getCodigo());
        }
        return null;
    }

}
