package ru.zivo.beatstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zivo.beatstore.model.Social;
import ru.zivo.beatstore.model.User;
import ru.zivo.beatstore.repository.SocialRepository;
import ru.zivo.beatstore.service.SocialService;
import ru.zivo.beatstore.service.UserService;

@Service
public class SocialServiceImpl implements SocialService {

    private final SocialRepository socialRepository;

    private final UserService userService;

    @Autowired
    public SocialServiceImpl(SocialRepository socialRepository, UserService userService) {
        this.socialRepository = socialRepository;
        this.userService = userService;
    }

    @Override
    public void update(String userId, Social social) {
        User user = userService.findById(userId);
        social.setId(user.getSocial().getId());
        social.setUser(user);
        socialRepository.save(social);
    }
}
