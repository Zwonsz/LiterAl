package Liter_Alura.Literalura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "livros")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @Column
    private Double numeroDownloads;

    @Column
    private String titulo;

    @Column
    private String idioma;

    public Livro() {}

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();

        if (dadosLivro.autores() != null && !dadosLivro.autores().isEmpty()) {
            DadosAutor dadosAutor = dadosLivro.autores().get(0);  // Primeiro autor
            this.autor = new Autor(dadosAutor.nome(), dadosAutor.nascimento(), dadosAutor.morte());
        } else {
            this.autor = null;  // Nenhum autor informado
        }

        this.idioma = (dadosLivro.idioma() != null && !dadosLivro.idioma().isEmpty())
                ? dadosLivro.idioma().get(0)
                : "Idioma não informado";

        this.numeroDownloads = dadosLivro.numeroDownload() != null
                ? dadosLivro.numeroDownload()
                : 0.0;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Double getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Double numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", autor=" + (autor != null ? autor.getNome() : "Não informado") +
                ", titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", numeroDownloads=" + numeroDownloads +
                '}';
    }
}
