package com.example.pib2.controller;

import com.example.pib2.model.user.dto.UserDto;
import com.example.pib2.model.user.User;
import com.example.pib2.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "API para gestión de usuarios del sistema")
@SecurityRequirement(name = "basicAuth")
public class UserController {

    @Autowired
    private UserService userService;


    private UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    private User toEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        return user;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna todos los usuarios registrados. Requiere rol ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
    })
    public List<UserDto> getAll() {
        return userService.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Retorna un usuario específico por su ID. Requiere rol ADMIN.")
    public ResponseEntity<UserDto> getById(
            @Parameter(description = "ID del usuario a buscar", required = true)
            @PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(toDto(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear usuario", description = "Crea un nuevo usuario en el sistema. Requiere rol ADMIN.")
    public UserDto create(
            @Parameter(description = "Datos del usuario a crear", required = true)
            @RequestBody UserDto userDto) {
        User user = toEntity(userDto);
        return toDto(userService.save(user));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza un usuario existente. Requiere rol ADMIN.")
    public ResponseEntity<UserDto> update(
            @PathVariable Long id,
            @RequestBody UserDto userDto) {
        return userService.findById(id)
                .map(existing -> {
                    userDto.setId(id);
                    User updated = toEntity(userDto);
                    return ResponseEntity.ok(toDto(userService.save(updated)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su ID. Requiere rol ADMIN.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
