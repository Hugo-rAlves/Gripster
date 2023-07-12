package org.ufrpe.inovagovlab.decisoestce.model.dto;

import java.util.ArrayList;
import java.util.List;

public class ProcessoDTO {
   private InformationBoardDTO informationBoard;
   private String superSumario;
   private TextBoard textBoard;
   private List<IndicadorDTO> indicadores;
   private List<String> highlights;
   private List<DicionarioDTO> dicionarios;

   public InformationBoardDTO getInformationBoard() {
      return informationBoard;
   }

   public void setInformationBoard(InformationBoardDTO informationBoard) {
      this.informationBoard = informationBoard;
   }

   public String getSuperSumario() {
      return superSumario;
   }

   public void setSuperSumario(String superSumario) {
      this.superSumario = superSumario;
   }

   public TextBoard getTextBoard() {
      return textBoard;
   }

   public void setTextBoard(TextBoard textBoard) {
      this.textBoard = textBoard;
   }
   public void setTextBoard(List<String> textos, String tipo) {
      if (this.textBoard == null){
         this.textBoard = new TextBoard();
      }
      textBoard.setTexto(textos, tipo);

      this.textBoard = textBoard;
   }

   public List<IndicadorDTO> getIndicadores() {
      return indicadores;
   }

   public void setIndicadores(List<IndicadorDTO> indicadores) {
      this.indicadores = indicadores;
   }

   public List<String> getHighlights() {
      return highlights;
   }

   public void setHighlights(List<String> highlights) {
      this.highlights = highlights;
   }

   public List<DicionarioDTO> getDicionarios() {
      return dicionarios;
   }

   public void setDicionarios(List<DicionarioDTO> dicionarios) {
      this.dicionarios = dicionarios;
   }
   public void addDicionarios(List<DicionarioDTO> dicionarios) {
      if(this.dicionarios == null){
         this.dicionarios = new ArrayList<>();
      }
      for (DicionarioDTO dicionarioDTO : dicionarios){
         if (!this.exists(dicionarioDTO.getPalavra()))
            this.dicionarios.add(dicionarioDTO);
      }
   }

   private boolean exists(String palavra){
      for (DicionarioDTO dicionario :
              this.dicionarios) {
         if(dicionario.getPalavra().equals(palavra)){
            return true;
         }
      }
      return false;
   }

   public void addHighlights(List<String> highlights) {
      if(this.highlights == null){
         this.highlights = new ArrayList<>();
      }
      for (String highlight : highlights){
         if (!this.highlights.contains(highlight))
            this.highlights.add(highlight);
      }
   }


}
