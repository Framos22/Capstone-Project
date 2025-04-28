package com.felixramos.album_management_api.repository;

import com.felixramos.album_management_api.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}