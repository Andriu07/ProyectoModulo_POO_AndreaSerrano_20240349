package ANDREASERRANO_20240349.ANDREASERRANO_20240349.Exception;

import lombok.Getter;

public class ExceptionDatosDuplicados extends RuntimeException {
    @Getter
    private String campoDuplicado;

    public ExceptionDatosDuplicados(String message, String campoDuplicado) {
        super(message);
        this.campoDuplicado = campoDuplicado;
    }
}
