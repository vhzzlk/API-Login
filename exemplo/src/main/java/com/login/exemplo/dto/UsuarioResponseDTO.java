package com.login.exemplo.dto;

import com.login.exemplo.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioResponseDTO {

    @NotNull(message = "O nome não podeser nulo")
    private String name;
    @NotBlank(message =  "você ta faznedo merda")
    private String email;


    public UsuarioResponseDTO(){

    }

    public UsuarioResponseDTO(String name, String email ) {
        this.name = name;
        this.email = email;

    }

    public @NotNull(message = "O nome não podeser nulo") String name() {
        return name;
    }

    public void setName(@NotNull(message = "O nome não podeser nulo") String name) {
        this.name = name;
    }

    public @NotBlank(message = "você ta faznedo merda") String email() {
        return email;
    }

    public void setEmail(@NotBlank(message = "você ta faznedo merda") String email) {
        this.email = email;
    }


}
