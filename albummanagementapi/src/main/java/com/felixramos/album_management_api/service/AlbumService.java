package com.felixramos.album_management_api.service;

import com.felixramos.album_management_api.entity.Album;
import com.felixramos.album_management_api.entity.Artist;
import com.felixramos.album_management_api.model.AlbumDTO;
import com.felixramos.album_management_api.model.SongDTO;
import com.felixramos.album_management_api.repository.AlbumRepository;
import com.felixramos.album_management_api.repository.ArtistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    private static final Logger logger = LoggerFactory.getLogger(AlbumService.class);

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    @Autowired
    public AlbumService(AlbumRepository albumRepository, ArtistRepository artistRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
    }

    @Transactional(readOnly = true)
    public List<AlbumDTO> getAllAlbums() {
        return albumRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AlbumDTO getAlbumById(Long albumId) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new RuntimeException("Album not found with id: " + albumId));
        return convertToDTO(album);
    }

    @Transactional
    public AlbumDTO createAlbum(AlbumDTO albumDTO) {
        logger.debug("Creating album: {}", albumDTO.getTitle());
        Album album = new Album();
        album.setTitle(albumDTO.getTitle());
        album.setReleaseYear(albumDTO.getReleaseYear());
        // Save the album first to generate its ID
        Album savedAlbum = albumRepository.save(album);
        logger.debug("Saved album with ID: {}", savedAlbum.getId());

        if (albumDTO.getArtistIds() != null && !albumDTO.getArtistIds().isEmpty()) {
            logger.debug("Associating artists: {}", albumDTO.getArtistIds());
            Set<Artist> artists = albumDTO.getArtistIds().stream()
                    .map(artistId -> artistRepository.findById(artistId)
                            .orElseThrow(() -> new IllegalArgumentException("Artist not found with id: " + artistId)))
                    .collect(Collectors.toSet());
            savedAlbum.setArtists(artists);
            // Update the album with artists
            savedAlbum = albumRepository.save(savedAlbum);
            logger.debug("Updated album with artists: {}",
                    savedAlbum.getArtists().stream().map(Artist::getId).collect(Collectors.toList()));
        }

        return convertToDTO(savedAlbum);
    }

    @Transactional
    public AlbumDTO updateAlbum(Long albumId, AlbumDTO albumDTO) {
        logger.debug("Updating album ID: {}", albumId);
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new RuntimeException("Album not found with id: " + albumId));
        album.setTitle(albumDTO.getTitle());
        album.setReleaseYear(albumDTO.getReleaseYear());
        if (albumDTO.getArtistIds() != null) {
            Set<Artist> artists = albumDTO.getArtistIds().stream()
                    .map(artistId -> artistRepository.findById(artistId)
                            .orElseThrow(() -> new IllegalArgumentException("Artist not found with id: " + artistId)))
                    .collect(Collectors.toSet());
            album.setArtists(artists);
        } else {
            album.setArtists(new HashSet<>());
        }
        Album savedAlbum = albumRepository.save(album);
        return convertToDTO(savedAlbum);
    }

    public void deleteAlbum(Long albumId) {
        if (!albumRepository.existsById(albumId)) {
            throw new RuntimeException("Album not found with id: " + albumId);
        }
        albumRepository.deleteById(albumId);
    }

    private AlbumDTO convertToDTO(Album album) {
        List<Long> artistIds = album.getArtists() != null
                ? album.getArtists().stream().map(Artist::getId).collect(Collectors.toList())
                : null;
        List<SongDTO> songDTOs = album.getSongs() != null
                ? album.getSongs().stream()
                .map(song -> new SongDTO(
                        song.getId(),
                        song.getTitle(),
                        song.getAlbum() != null ? song.getAlbum().getId() : null,
                        song.getDuration()))
                .collect(Collectors.toList())
                : null;
        return new AlbumDTO(album.getId(), album.getTitle(), album.getReleaseYear(), artistIds, songDTOs);
    }
}
