package com.springCrud.CrudSpringBackEnd.Model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class JwtRequest {

    private String username;
    private String password;

}
