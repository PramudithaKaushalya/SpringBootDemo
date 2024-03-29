package com.example.demo.resource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Favorite;
import com.example.demo.model.User;
import com.example.demo.model.UserFavorite;
import com.example.demo.repository.FavoriteRepository;
import com.example.demo.repository.UserFavoriteRepository;
import com.example.demo.repository.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/")
public class FavoriteResource {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserFavoriteRepository userFavoriteRepository;

    @GetMapping("/favo")
    public List<Favorite> getAll() {
        return favoriteRepository.findAll();
    }

    @PostMapping(value = "/savefavo")
    public void signup(@RequestBody Favorite favorite) {
        favoriteRepository.save(favorite);

        //Get last favorite Id
        Favorite last = favoriteRepository.findTopByOrderByIdDesc();
        Integer fav_id = last.getId();
        System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnn"+fav_id);

        //Get Id  of favorite_user
        String name = favorite.user_name;
        User user = userRepository.findByName(name);
        Integer user_id = user.getUser_id();
        System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiii"+user_id);

        
        UserFavorite entity = new UserFavorite();
        entity.setUser(user);
        entity.setFavorite(last);
        entity.setAdded_on(LocalDateTime.now());

        userFavoriteRepository.save(entity);
    }

    @GetMapping("/deletefavo/{id}")
    public String DeleteById(@PathVariable("id") Favorite id) {
        UserFavorite userFavorite = userFavoriteRepository.findByFavorite(id);
        Integer userfavid = userFavorite.getId();
        userFavoriteRepository.deleteById(userfavid);
        return "Delete Favorites Successfully!!!";
        
    }

    @GetMapping("/searchfavo/{id}")
    public Optional<Favorite> getOne(@PathVariable("id") Integer id) {
        System.out.println(id);
        return favoriteRepository.findById(id);
    }
}