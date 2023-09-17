package com.dima.service;

import com.dima.dto.UserCreateDto;
import com.dima.dto.UserReadDto;
import com.dima.dto.filters.UserFilter;
import com.dima.entity.Product;
import com.dima.entity.User;
import com.dima.mapper.UserCreateMapper;
import com.dima.mapper.UserReadMapper;
import com.dima.repository.UserRepository;
import com.dima.specification.UserSpecification;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;
    private final ImageService imageService;

    public Page<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
        var specification = UserSpecification.withFilter(filter);

        return userRepository.findAll(specification, pageable)
                .map(userReadMapper::map);
    }

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::map)
                .toList();
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    public Optional<byte[]> findAvatar(Long id) {
        return userRepository.findById(id)
                .map(User::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);

    }

    @Transactional
    public UserReadDto create(UserCreateDto userDto) {
        return Optional.of(userDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return userCreateMapper.map(dto);
                })
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateDto userDto) {
        return userRepository.findById(id)
                .map(entity -> {
                    uploadImage(userDto.getImage());
                    return userCreateMapper.map(userDto, entity);
                })
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    @Transactional
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
