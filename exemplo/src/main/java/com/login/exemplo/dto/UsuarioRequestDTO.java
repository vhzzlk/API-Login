package com.login.exemplo.dto;

import com.login.exemplo.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioRequestDTO {

    @NotNull(message = "O nome não podeser nulo")
    private String name;
    @NotBlank(message =  "você ta faznedo merda")
    private String email;
    @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres")
    private String password;

    public UsuarioRequestDTO(){

    }

    public UsuarioRequestDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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

    public @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres") String password() {
        return password;
    }

    public void setPassword(@Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres") String password) {
        this.password = password;
    }
}
