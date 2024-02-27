package io.management.resources.repository;

import io.management.resources.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, String> {
    Optional<Image> findImageByUrl(String url);
}
