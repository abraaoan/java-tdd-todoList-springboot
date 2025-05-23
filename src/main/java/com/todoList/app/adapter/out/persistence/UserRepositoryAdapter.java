package com.todoList.app.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.todoList.app.application.port.out.UserRepository;
import com.todoList.app.domain.exception.InvalidUserException;
import com.todoList.app.domain.model.User;
import com.todoList.app.infrastructure.persistence.JpaUserRepository;
import com.todoList.app.infrastructure.persistence.entity.UserEntity;

@Component
public class UserRepositoryAdapter implements UserRepository {
    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryAdapter(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User createUser(User user) throws InvalidUserException {
        UserEntity entity = mapToEntity(user);
        UserEntity saved = jpaUserRepository.save(entity);
        return mapToDomain(saved);
    }

    @Override
    public void deleteUser(int userId) throws InvalidUserException {
        jpaUserRepository.deleteById(userId);
    }

    @Override
    public User findUser(int userId) {
        User user = jpaUserRepository.findById(userId)
                .map(this::mapToDomain)
                .orElseThrow(() -> new InvalidUserException("User com ID \" + userId + \" não encontrada"));

        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(this::mapToDomain);
    }

    @Override
    public List<User> listUser() {
        return jpaUserRepository
                .findAll()
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public User updateUser(User user) {
        UserEntity entity = mapToEntity(user);
        UserEntity updatedEntity = jpaUserRepository.save(entity);

        return mapToDomain(updatedEntity);
    }

    // MAPPER

    private UserEntity mapToEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setRole(user.getRole());

        return entity;
    }

    private User mapToDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getEmail(),
                entity.getName(),
                entity.getPassword(),
                entity.getRole());
    }
}
