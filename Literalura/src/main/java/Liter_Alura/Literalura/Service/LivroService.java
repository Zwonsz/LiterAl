package Liter_Alura.Literalura.Service;

import Liter_Alura.Literalura.Model.Autor;
import Liter_Alura.Literalura.Model.Livro;
import Liter_Alura.Literalura.Model.DadosAutor;
import Liter_Alura.Literalura.Model.DadosLivro;
import Liter_Alura.Literalura.Repository.AutorRepositorio;
import Liter_Alura.Literalura.Repository.LivroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepositorio livroRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    public void salvarOuAtualizarAutor(Autor autor) {
        autorRepositorio.save(autor);  // Usando o save do JpaRepository, que já resolve salvar ou atualizar
    }

    public void processarLivro(DadosLivro dadosLivro) {
        System.out.println("Título: " + dadosLivro.titulo());
        if (dadosLivro.autores() != null && !dadosLivro.autores().isEmpty()) {
            DadosAutor dadosAutor = dadosLivro.autores().get(0);
            System.out.println("Autor recebido: " + dadosAutor.nome());
            Autor autor = obterAutor(dadosAutor);
            persistirLivro(dadosLivro, autor);
        } else {
            System.out.println("Autor: Não informado.");
        }

        System.out.println("Idioma: " + (dadosLivro.idioma() != null && !dadosLivro.idioma().isEmpty()
                ? dadosLivro.idioma().get(0)
                : "Não informado"));
        System.out.println("Número de Downloads: " + (dadosLivro.numeroDownload() != 0
                ? dadosLivro.numeroDownload()
                : "Não informado\n"));

    }

    public Autor obterAutor(DadosAutor dadosAutor) {
        // Tenta encontrar o autor no banco de dados usando o nome
        Autor autorExistente = autorRepositorio.findByAutor(dadosAutor.nome());

        if (autorExistente == null) {
            // Se o autor não existir, cria um novo autor
            autorExistente = new Autor(dadosAutor.nome(), dadosAutor.nascimento(), dadosAutor.morte());
            // Salva o novo autor no banco usando o método save do JpaRepository
            salvarOuAtualizarAutor(autorExistente);
        } else {
            // Se o autor já existir, apenas retorna o autor existente
            // Não há necessidade de salvar novamente
            System.out.println("Autor já existente: " + autorExistente.getNome());
        }

        return autorExistente;
    }

    private void persistirLivro(DadosLivro dadosLivro, Autor autor) {
        Livro livro = new Livro(dadosLivro);
        livro.setAutor(autor);
        livroRepositorio.save(livro);  // Salva o livro no banco
    }
}
//    public List<Livro> buscarLivrosPorIdioma(String idioma) {
//        return livroRepositorio.buscarLivrosPorIdioma(idioma);
//    }
//}


