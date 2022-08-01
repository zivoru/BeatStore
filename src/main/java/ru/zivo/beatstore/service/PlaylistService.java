package ru.zivo.beatstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.zivo.beatstore.model.Playlist;
import ru.zivo.beatstore.web.dto.PlaylistDto;

import java.io.IOException;
import java.util.List;

public interface PlaylistService {

    Playlist findById(Long id);

    PlaylistDto findDtoById(Long id);

    List<Playlist> findAllByUserId(String userId);

    Playlist create(String userId, Playlist playlist);

    void update(String userId, Long playlistId, Playlist playlist);

    void delete(String userId, Long playlistId);

    void uploadImage(Long id, MultipartFile image) throws IOException;

    void addBeat(String userId, Long playlistId, Long beatId);

    void removeBeat(String userId, Long playlistId, Long beatId);

    void addFavorite(Long playlistId, String userId);

    void removeFavorite(Long playlistId, String userId);

    List<PlaylistDto> getRecommended(Integer limit);
}
