package pe.edu.cibertec.DAAII_T1_KAPUSTIN_PEREZ_KEVIN_JOEL.model.dto;

import lombok.Data;

@Data
public class UsuarioDto {
    private Integer idusuario;
    private String nomusuario;
    private String nombres;
    private String apellidos;
    private Boolean activo;
    private String email;
}
