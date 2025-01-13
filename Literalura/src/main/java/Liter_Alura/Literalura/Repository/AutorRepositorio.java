package Liter_Alura.Literalura.Repository;

import Liter_Alura.Literalura.Model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepositorio extends JpaRepository<Autor, Long> {

    Autor findByAutor(String nome);


    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoFalecimento IS NULL OR a.anoFalecimento >= :ano)")
    List<Autor> buscarAutoresPorAno(@Param("ano") Integer ano);
}




