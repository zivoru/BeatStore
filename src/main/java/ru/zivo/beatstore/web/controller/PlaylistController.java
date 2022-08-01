package ru.zivo.beatstore.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.zivo.beatstore.model.Playlist;
import ru.zivo.beatstore.service.PlaylistService;
import ru.zivo.beatstore.web.dto.PlaylistDto;

import java.io.IOException;
import java.util.List;

@Tag(name = "PlaylistController", description = "API для работы с плейлистами")
@RequestMapping("api/v1/playlists")
@RestController
public class PlaylistController {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Operation(summary = "Получение дто плейлиста по id")
    @GetMapping("{id}")
    public ResponseEntity<PlaylistDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(playlistService.findDtoById(id));
    }

    @Operation(summary = "Получение списка плейлистов по id пользователя")
    @GetMapping("user/{userId}")
    public ResponseEntity<List<Playlist>> findAllByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(playlistService.findAllByUserId(userId));
    }

    @Operation(summary = "Создание плейлиста")
    @PostMapping("{userId}")
    public ResponseEntity<Long> create(@PathVariable String userId, @RequestBody Playlist playlist) {
        Playlist savedPlaylist = playlistService.create(userId, playlist);
        return ResponseEntity.ok(savedPlaylist.getId());
    }

    @Operation(summary = "Изменение плейлиста")
    @PutMapping("{id}")
    public void update(@AuthenticationPrincipal OAuth2User principal,
                       @PathVariable Long id,
                       @RequestBody Playlist playlist) {
        if (principal != null) playlistService.update(principal.getAttribute("sub"), id, playlist);
    }

    @Operation(summary = "Удаление плейлиста")
    @DeleteMapping("{id}")
    public void update(@AuthenticationPrincipal OAuth2User principal, @PathVariable Long id) {
        if (principal != null) playlistService.delete(principal.getAttribute("sub"), id);
    }

    @Operation(summary = "Загрузка фото плейлиста")
    @PostMapping("uploadImage/{id}")
    public void uploadImage(@PathVariable Long id,
                            @RequestParam(name = "image") MultipartFile image) throws IOException {
        playlistService.uploadImage(id, image);
    }

    @Operation(summary = "Добавление бита в плейлист")
    @PostMapping("addBeat/{playlistId}/{beatId}")
    public void addBeat(@AuthenticationPrincipal OAuth2User principal,
                        @PathVariable Long playlistId,
                        @PathVariable Long beatId) {
        if (principal != null) playlistService.addBeat(principal.getAttribute("sub"), playlistId, beatId);
    }

    @Operation(summary = "Удаление бита из плейлиста")
    @PostMapping("removeBeat/{playlistId}/{beatId}")
    public void removeBeat(@AuthenticationPrincipal OAuth2User principal,
                           @PathVariable Long playlistId,
                           @PathVariable Long beatId) {
        if (principal != null) playlistService.removeBeat(principal.getAttribute("sub"), playlistId, beatId);
    }

    @Operation(summary = "Добавление в избранное")
    @PostMapping("addFavorite/{playlistId}/{userId}")
    public void addFavorite(@PathVariable Long playlistId, @PathVariable String userId) {
        playlistService.addFavorite(playlistId, userId);
    }

    @Operation(summary = "Удаление из избранного")
    @PostMapping("removeFavorite/{playlistId}/{userId}")
    public void removeFavorite(@PathVariable Long playlistId, @PathVariable String userId) {
        playlistService.removeFavorite(playlistId, userId);
    }

    @Operation(summary = "Получение рекомендуемых плейлистов")
    @GetMapping("/recommended")
    public ResponseEntity<List<PlaylistDto>> getRecommended(@RequestParam Integer limit) {
        return ResponseEntity.ok(playlistService.getRecommended(limit));
    }
}
