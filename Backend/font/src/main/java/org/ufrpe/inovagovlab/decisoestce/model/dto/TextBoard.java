package org.ufrpe.inovagovlab.decisoestce.model.dto;

import java.util.List;

public class TextBoard {
    private List<String> pontosDestaque;
    private TextoCompletoDTO textoCompleto;

    public TextBoard() {
    }

    public List<String> getPontosDestaque() {
        return pontosDestaque;
    }

    public void setPontosDestaque(List<String> pontosDestaque) {
        this.pontosDestaque = pontosDestaque;
    }
    public TextoCompletoDTO getTextoCompleto() {
        return textoCompleto;
    }

    public void setTexto(TextoCompletoDTO textoCompleto) {
        this.textoCompleto = textoCompleto;
    }
    public void setTexto(List<String> textos, String tipo) {
        if(textoCompleto == null){
            textoCompleto = new TextoCompletoDTO();
        }
        if (tipo == "c"){
            this.textoCompleto.setConsiderandos(textos);
        }else if (tipo == "r"){
            this.textoCompleto.setRecomendacoes(textos);
        }else if(tipo == "d"){
            this.textoCompleto.setDeterminacoes(textos);
        }else if(tipo.equals("s")){
            this.setPontosDestaque(textos);
        }
    }
}
