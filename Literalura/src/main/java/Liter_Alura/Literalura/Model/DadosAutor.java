package Liter_Alura.Literalura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAutor(@JsonAlias("name") String nome,
                        @JsonAlias("birth_year") Integer nascimento,
                        @JsonAlias("death_year") Integer morte) {
    @Override
    public String toString() { return "Autor = " + nome; }
}
