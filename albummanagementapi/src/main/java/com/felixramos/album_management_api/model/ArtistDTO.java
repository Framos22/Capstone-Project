package com.felixramos.album_management_api.model;

import java.util.List;

public class ArtistDTO {
    private Long artistId;
    private String name;
    private List<AlbumDTO> albums;

    // Constructors
    public ArtistDTO() {}

    public ArtistDTO(Long artistId, String name, List<AlbumDTO> albums) {
        this.artistId = artistId;
        this.name = name;
        this.albums = albums;
    }

    // Getters and setters
    public Long getArtistId() { return artistId; }

    public void setArtistId(Long artistId) { this.artistId = artistId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<AlbumDTO> getAlbums() { return albums; }

    public void setAlbums(List<AlbumDTO> albums) { this.albums = albums; }
}