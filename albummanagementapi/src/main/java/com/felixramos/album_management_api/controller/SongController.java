package com.felixramos.album_management_api.controller;

import com.felixramos.album_management_api.model.SongDTO;
import com.felixramos.album_management_api.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public List<SongDTO> getAllSongs() {
        return songService.getAllSongs();
    }

    @GetMapping("/{songId}")
    public ResponseEntity<SongDTO> getSongById(@PathVariable Long songId) {
        SongDTO song = songService.getSongById(songId);
        return ResponseEntity.ok(song);
    }

    @PostMapping
    public ResponseEntity<SongDTO> createSong(@RequestBody SongDTO songDTO) {
        SongDTO createdSong = songService.createSong(songDTO);
        return ResponseEntity.ok(createdSong);
    }

    @PutMapping("/{songId}")
    public ResponseEntity<SongDTO> updateSong(@PathVariable Long songId, @RequestBody SongDTO songDTO) {
        SongDTO updatedSong = songService.updateSong(songId, songDTO);
        return ResponseEntity.ok(updatedSong);
    }

    @DeleteMapping("/{songId}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long songId) {
        songService.deleteSong(songId);
        return ResponseEntity.noContent().build();
    }
}