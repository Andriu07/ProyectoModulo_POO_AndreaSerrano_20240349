package ANDREASERRANO_20240349.ANDREASERRANO_20240349.Models.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;


@EqualsAndHashCode
@Getter
@Setter
@ToString
public class LibrosDTO {

    private Long id_libros;

    @NotBlank(message = "El titulo no puede estar vacio")
    private String titulo;

    @NotBlank(message = "ISBN no puede estar vacio")
    private String isbn;

    @NotNull
    private Long año_publicacion;

    @NotBlank
    private String genero;

    @NotNull(message = " Id_autor no puede contener valores nulos ")
    private BigInteger autor_id;
}
