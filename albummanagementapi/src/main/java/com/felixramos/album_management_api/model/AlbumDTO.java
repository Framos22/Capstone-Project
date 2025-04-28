package com.felixramos.album_management_api.model;

import java.util.List;

public class AlbumDTO {
    private Long albumId;
    private String title;
    private Integer releaseYear;
    private List<Long> artistIds;
    private List<SongDTO> songs;

    // Constructors
    public AlbumDTO() {}

    public AlbumDTO(Long albumId, String title, Integer releaseYear, List<Long> artistIds, List<SongDTO> songs) {
        this.albumId = albumId;
        this.title = title;
        this.releaseYear = releaseYear;
        this.artistIds = artistIds;
        this.songs = songs;
    }

    // Getters and setters
    public Long getAlbumId() { return albumId; }

    public void setAlbumId(Long albumId) { this.albumId = albumId; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public Integer getReleaseYear() { return releaseYear; }

    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }

    public List<Long> getArtistIds() { return artistIds; }

    public void setArtistIds(List<Long> artistIds) { this.artistIds = artistIds; }

    public List<SongDTO> getSongs() { return songs; }

    public void setSongs(List<SongDTO> songs) { this.songs = songs; }
}