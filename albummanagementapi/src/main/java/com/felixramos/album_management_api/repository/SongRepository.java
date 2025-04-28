package com.felixramos.album_management_api.repository;

import com.felixramos.album_management_api.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}