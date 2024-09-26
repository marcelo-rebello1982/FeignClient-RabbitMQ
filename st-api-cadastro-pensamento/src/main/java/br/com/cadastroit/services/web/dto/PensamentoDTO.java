package br.com.cadastroit.services.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PensamentoDTO  {
	
	private Long id;
	
	private String conteudo;
	
	private String autoria;
	
	private String modelo;
	
	private PessoaDTO pessoa;

}
