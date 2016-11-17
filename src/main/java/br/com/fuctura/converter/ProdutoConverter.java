/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fuctura.converter;

import br.com.fuctura.controller.VendaController;
import br.com.fuctura.model.Produto;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;

/**
 *
 * @author aula
 */
@FacesConverter("produto")
public class ProdutoConverter implements Converter {

    
    @Override
    public Object getAsObject(FacesContext context, 
            UIComponent component,
            String value) {
          if (value != null) {
            for (Produto produto : VendaController.PRODUTOS) {
                if (value.equals(String.valueOf(produto.getCodigo()))) {
                    return produto;
                }
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        if (value != null && !value.equals("")) {
            Produto produto = (Produto) value;
            return String.valueOf(produto.getCodigo());
        }
        return null;
    }

}
