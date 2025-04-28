package com.felixramos.album_management_api.service;

import com.felixramos.album_management_api.entity.Song;
import com.felixramos.album_management_api.entity.Album;
import com.felixramos.album_management_api.model.SongDTO;
import com.felixramos.album_management_api.repository.SongRepository;
import com.felixramos.album_management_api.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;

    @Autowired
    public SongService(SongRepository songRepository, AlbumRepository albumRepository) {
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
    }

    public List<SongDTO> getAllSongs() {
        return songRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SongDTO getSongById(Long songId) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found with id: " + songId));
        return convertToDTO(song);
    }

    public SongDTO createSong(SongDTO songDTO) {
        Album album = albumRepository.findById(songDTO.getAlbumId())
                .orElseThrow(() -> new IllegalArgumentException("Album not found with id: " + songDTO.getAlbumId()));
        Song song = convertToEntity(songDTO);
        song.setAlbum(album);
        Song savedSong = songRepository.save(song);
        return convertToDTO(savedSong);
    }

    public SongDTO updateSong(Long songId, SongDTO songDTO) {
        Song existingSong = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found with id: " + songId));
        Album album = albumRepository.findById(songDTO.getAlbumId())
                .orElseThrow(() -> new IllegalArgumentException("Album not found with id: " + songDTO.getAlbumId()));
        existingSong.setTitle(songDTO.getTitle());
        existingSong.setAlbum(album);
        existingSong.setDuration(songDTO.getDuration());
        Song updatedSong = songRepository.save(existingSong);
        return convertToDTO(updatedSong);
    }

    public void deleteSong(Long songId) {
        if (!songRepository.existsById(songId)) {
            throw new RuntimeException("Song not found with id: " + songId);
        }
        songRepository.deleteById(songId);
    }

    private SongDTO convertToDTO(Song song) {
        return new SongDTO(
                song.getId(),
                song.getTitle(),
                song.getAlbum() != null ? song.getAlbum().getId() : null,
                song.getDuration()
        );
    }

    private Song convertToEntity(SongDTO songDTO) {
        Song song = new Song();
        song.setTitle(songDTO.getTitle());
        song.setDuration(songDTO.getDuration());
        return song;
    }
}