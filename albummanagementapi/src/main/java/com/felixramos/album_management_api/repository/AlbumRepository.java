package com.felixramos.album_management_api.repository;

import com.felixramos.album_management_api.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}