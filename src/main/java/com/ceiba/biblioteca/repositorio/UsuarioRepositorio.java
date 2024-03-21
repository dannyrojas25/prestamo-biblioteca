package com.ceiba.biblioteca.repositorio;

import com.ceiba.biblioteca.modelo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    @Modifying()
    @Query("update User u set u.email=:email, u.num_identificacion=:num_identificacion , u.username=:username where u.id = :id")
    void updateUser(@Param(value = "id") int id,
                    @Param(value = "email") String email,
                    @Param(value = "num_identificacion") String num_identificacion,
                    @Param(value = "username") String username);
}