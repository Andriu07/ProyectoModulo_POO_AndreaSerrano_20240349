package ANDREASERRANO_20240349.ANDREASERRANO_20240349.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

@Entity
@Table(name = "LIBROS")
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class LibrosEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_libros")
    @SequenceGenerator(name = "seq_libros", sequenceName = "seq_libros",allocationSize = 1)
    private Long id;

    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "AÑO_PUBLICACION")
    private String año_publicacion;

    @Column(name = "GENERO")
    private String genero;

    @Column(name = "ID_AUTOR")
    private Long id_autor;

}
