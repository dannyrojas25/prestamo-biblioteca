package com.ceiba.biblioteca.servicio;

import com.ceiba.biblioteca.modelo.ERole;
import com.ceiba.biblioteca.modelo.user.User;
import com.ceiba.biblioteca.modelo.user.UserDTO;
import com.ceiba.biblioteca.modelo.user.UserRequest;
import com.ceiba.biblioteca.modelo.user.UserResponse;
import com.ceiba.biblioteca.repositorio.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServicio{

    private final UsuarioRepositorio userRepository;

    @Transactional
    public UserResponse updateUser(UserRequest userRequest) {

        User user = User.builder()
                .id(userRequest.getId())
                .email(userRequest.getEmail())
                .num_identificacion(userRequest.getNum_identificacion())
                .username(userRequest.getUsername())
                .role(ERole.USER)
                .build();

        userRepository.updateUser(user.getId(), user.getEmail(), user.getNum_identificacion(), user.getUsername());

        return new UserResponse("El usuario se registró satisfactoriamente");
    }

    public UserDTO getUser(Integer id) {
        User user= userRepository.findById(id).orElse(null);

        if (user!=null)
        {
            UserDTO userDTO = UserDTO.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .num_identificacion(user.getNum_identificacion())
                    .username(user.getUsername())
                    .build();
            return userDTO;
        }
        return null;
    }
}
