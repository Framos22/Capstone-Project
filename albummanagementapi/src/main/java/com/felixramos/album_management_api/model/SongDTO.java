package com.felixramos.album_management_api.model;

public class SongDTO {
    private Long songId;
    private String title;
    private Long albumId;
    private Integer duration;

    // Constructors
    public SongDTO() {}

    public SongDTO(Long songId, String title, Long albumId, Integer duration) {
        this.songId = songId;
        this.title = title;
        this.albumId = albumId;
        this.duration = duration;
    }

    // Getters and setters
    public Long getSongId() { return songId; }

    public void setSongId(Long songId) { this.songId = songId; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public Long getAlbumId() { return albumId; }

    public void setAlbumId(Long albumId) { this.albumId = albumId; }

    public Integer getDuration() { return duration; }

    public void setDuration(Integer duration) { this.duration = duration; }
}