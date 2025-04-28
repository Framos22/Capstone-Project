package com.felixramos.album_management_api.service;

import com.felixramos.album_management_api.entity.Artist;
import com.felixramos.album_management_api.entity.Album;
import com.felixramos.album_management_api.model.ArtistDTO;
import com.felixramos.album_management_api.model.AlbumDTO;
import com.felixramos.album_management_api.repository.ArtistRepository;
import com.felixramos.album_management_api.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.HashSet;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository, AlbumRepository albumRepository) {
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    @Transactional(readOnly = true)
    public List<ArtistDTO> getAllArtists() {
        return artistRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArtistDTO getArtistById(Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found with id: " + artistId));
        return convertToDTO(artist);
    }

    @Transactional
    public ArtistDTO createArtist(ArtistDTO artistDTO) {
        Artist artist = new Artist();
        artist.setName(artistDTO.getName());
        if (artistDTO.getAlbums() != null && !artistDTO.getAlbums().isEmpty()) {
            Set<Album> albums = artistDTO.getAlbums().stream()
                    .map(albumDTO -> albumRepository.findById(albumDTO.getAlbumId())
                            .orElseThrow(() -> new RuntimeException("Album not found with id: " + albumDTO.getAlbumId())))
                    .collect(Collectors.toSet());
            artist.setAlbums(albums);
        }
        Artist savedArtist = artistRepository.save(artist);
        return convertToDTO(savedArtist);
    }

    @Transactional
    public ArtistDTO updateArtist(Long artistId, ArtistDTO artistDTO) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found with id: " + artistId));
        artist.setName(artistDTO.getName());
        if (artistDTO.getAlbums() != null) {
            Set<Album> albums = artistDTO.getAlbums().stream()
                    .map(albumDTO -> albumRepository.findById(albumDTO.getAlbumId())
                            .orElseThrow(() -> new RuntimeException("Album not found with id: " + albumDTO.getAlbumId())))
                    .collect(Collectors.toSet());
            artist.setAlbums(albums);
        } else {
            artist.setAlbums(new HashSet<>());
        }
        Artist savedArtist = artistRepository.save(artist);
        return convertToDTO(savedArtist);
    }

    @Transactional
    public void deleteArtist(Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found with id: " + artistId));
        // Remove artist from all associated albums
        for (Album album : artist.getAlbums()) {
            album.getArtists().remove(artist);
            albumRepository.save(album);
        }
        artist.getAlbums().clear(); // Clear artist's albums
        artistRepository.save(artist);
        artistRepository.delete(artist);
    }

    private ArtistDTO convertToDTO(Artist artist) {
        List<AlbumDTO> albumDTOs = artist.getAlbums() != null
                ? artist.getAlbums().stream()
                .map(album -> new AlbumDTO(
                        album.getId(),
                        album.getTitle(),
                        album.getReleaseYear(),
                        album.getArtists().stream().map(Artist::getId).collect(Collectors.toList()),
                        null))
                .collect(Collectors.toList())
                : null;
        return new ArtistDTO(artist.getId(), artist.getName(), albumDTOs);
    }
}