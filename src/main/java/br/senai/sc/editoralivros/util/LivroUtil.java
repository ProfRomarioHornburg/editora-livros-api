package br.senai.sc.editoralivros.util;

import br.senai.sc.editoralivros.dto.LivroDTO;
import br.senai.sc.editoralivros.dto.LivroDTOCadastro;
import br.senai.sc.editoralivros.model.entity.Livro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LivroUtil {
    private ObjectMapper objectMapper = new ObjectMapper();


    public Livro convertJsonToModel(String livroJson) {
        LivroDTOCadastro livroDTOCadastro = convertJsonToDto(livroJson);
        return convertDtoToModel(livroDTOCadastro);
    }

    private LivroDTOCadastro convertJsonToDto(String livroJson){
        try {
            return this.objectMapper.readValue(livroJson, LivroDTOCadastro.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Livro convertDtoToModel(@Valid LivroDTOCadastro livroDTOCadastro){
        return this.objectMapper.convertValue(livroDTOCadastro, Livro.class);
    }


}
