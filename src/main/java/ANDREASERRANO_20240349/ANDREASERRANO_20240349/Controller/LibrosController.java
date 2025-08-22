package ANDREASERRANO_20240349.ANDREASERRANO_20240349.Controller;

import ANDREASERRANO_20240349.ANDREASERRANO_20240349.Exception.ExceptionDatosDuplicados;
import ANDREASERRANO_20240349.ANDREASERRANO_20240349.Models.DTO.LibrosDTO;
import ANDREASERRANO_20240349.ANDREASERRANO_20240349.Service.LibrosService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiLibros")
public class LibrosController {

    //inyectamos el sevrice
    @Autowired
    LibrosService service;

    //obtenemos datos
    @GetMapping("/ObtenerLibros")
    public List<LibrosDTO> ObtenerLibros(){
        return service.ObtenerLibros();
    }



    //Insertamos datos
    @PostMapping("/registrarLibro")
    public ResponseEntity<?> nuevoLibro(@Valid @RequestBody LibrosDTO json, HttpServletRequest request){
        try{
            LibrosDTO respuesta = service.InsertarDatos(json);
        if (respuesta == null){
            return ResponseEntity.badRequest().body(Map.of(
                    "status","inserccion fallida",
                    "errorType","validation_error",
                    "message","Los libros no pudieron ser registrados"
            ));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status","succes",
                "data","respuesta"
        ));
       } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status","error",
                    "message","Error no controlado al registrar libro",
                    "detail", e.getMessage()
            ));
        }

    }

    @PutMapping("/editarLibro/{id}")
        public ResponseEntity<?> modificarLibro(@PathVariable Long id, @Valid @RequestBody LibrosDTO json, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String , String > errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(
                 error -> errores.put(error.getField(), error.getDefaultMessage()));
                 return ResponseEntity.badRequest().body(errores);
        }

        try{//ACTUALIZAR EL LIBRO LLAMANDO AL SEVRICE Y GUARDANDO EL RESULTADO EN UN DTO
            LibrosDTO dto = service.actualizarLibro(id, json);
            //si sale bien retornar HTTP 200(OK)
            return ResponseEntity.ok(dto);
        }
        //SI EL registro no existe retornar HTTP 400(NOT FOUND)
        catch (ExceptionDatosDuplicados e){
            //
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Datos Duplicados",
                    "campo",e.getCampoDuplicado()
            ));
        }
    }


    //eliminar
    @DeleteMapping("/eliminarLibro/{id}")
    public ResponseEntity<?> eliminarLibro(@PathVariable Long id) {
        try {
            if (!service.removerLibro(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).header("mensaje de error", "libro no encontrado").body(Map.of(
                        "error", "NOT FOUND ",
                        "message", "EL libro  no fue encontrado",
                        "timestamp", Instant.now().toString()
                ));
            }

            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso Completado",
                    "message", "Libro eliminado con exito"
            ));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status","error",
                    "message","Error al elmiinar libro",
                    "detail", e.getMessage()
            ));
        }

    }
}
