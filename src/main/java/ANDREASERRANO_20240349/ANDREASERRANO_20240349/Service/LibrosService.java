package ANDREASERRANO_20240349.ANDREASERRANO_20240349.Service;

import ANDREASERRANO_20240349.ANDREASERRANO_20240349.Entity.LibrosEntity;
import ANDREASERRANO_20240349.ANDREASERRANO_20240349.Exception.ExceptionBookNotFound;
import ANDREASERRANO_20240349.ANDREASERRANO_20240349.Exception.ExceptionBookNotRegister;
import ANDREASERRANO_20240349.ANDREASERRANO_20240349.Models.DTO.LibrosDTO;
import ANDREASERRANO_20240349.ANDREASERRANO_20240349.Repository.LibrosRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LibrosService {

    @Autowired
    LibrosRepository repository;

    public List<LibrosDTO> ObtenerLibros(){
        List<LibrosEntity> lista = repository. findAll();
        return lista.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    private LibrosDTO convertirADTO(LibrosEntity librosEntity) {
        LibrosDTO dto = new LibrosDTO();
        dto.setId_libros(librosEntity.getId_libros());
        dto.setTitulo(librosEntity.getTitulo());
        dto.setIsbn(librosEntity.getIsbn());
        dto.setAño_publicacion(librosEntity.getAño_publicacion());
        dto.setGenero(librosEntity.getGenero());
        dto.setAutor_id(librosEntity.getAutor_id());
        return dto;
    }


    public LibrosDTO InsertarDatos(LibrosDTO data){
        if(data == null || data.getTitulo() == null || data.getTitulo().isEmpty()||
        data.getAutor_id() == null || data.getGenero() == null || data.getGenero().isEmpty()){
            throw new IllegalArgumentException("Libro,Titulo de Libro ,ISBN Y ID_Autor no pueden ser nulos");
        }
        try{
            LibrosEntity entity = convertirAEntity(data);
            LibrosEntity librosguardados = repository.save(entity);
            return convertirADTO(librosguardados);
        }catch (Exception e){
            log.error("Error al registrar el libro: " + e.getMessage());
            throw new ExceptionBookNotRegister("Error alregistrar libro");
        }
    }

    private LibrosEntity convertirAEntity(LibrosDTO data) {
        LibrosEntity entity =new LibrosEntity();
        entity.setId_libros(data.getId_libros());
        entity.setTitulo(data.getTitulo());
        entity.setIsbn(data.getIsbn());
        entity.setAño_publicacion(data.getAño_publicacion());
        entity.setGenero(data.getGenero());
        entity.setAutor_id(data.getAutor_id());
        return entity;
    }


    public LibrosDTO actualizarLibro(Long id_Libro, @Valid LibrosDTO json){
        //verificar existencia
        LibrosEntity existente = repository.findById(id_Libro).orElseThrow(() -> new ExceptionBookNotFound("Libro no encontrado"));
        existente.setId_libros(json.getId_libros());
        existente.setTitulo(json.getTitulo());
        existente.setIsbn(json.getIsbn());
        existente.setAño_publicacion(json.getAño_publicacion());
        existente.setGenero(json.getGenero());
        existente.setAutor_id(json.getAutor_id());
        //guardar nuevos valores
        LibrosEntity libroActualizado = repository.save(existente);
        //convertir entity a dto
        return convertirADTO(libroActualizado);
    }



    public boolean removerLibro(Long id_libro){
        try{
            LibrosEntity existente = repository.findById(id_libro).orElse(null);
            if (existente != null){
                repository.deleteById(id_libro);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException) {
            ("No se encontro libro con el id  " + id_libro "para eliminar " + 1));}
    }

    //Metodo search

    public LibrosDTO getSolutionByID(Long id_libro){
        Optional<LibrosEntity> libroOptional = accesLibrosRepository.FindById(id_libro);
        if (libroOptional.isPresent()){
            return convertirADTO(libroOptional.get());
        }else {
            throw new EntityNotFoundException("No se encontro libro con el id: " + id_libro)
        }
    }
}
