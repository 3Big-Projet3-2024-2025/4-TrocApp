package helha.trocappbackend.controllers;

import helha.trocappbackend.models.Rating;
import helha.trocappbackend.services.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {


    @Autowired
    private RatingService ratingService;

    // Ajouter une évaluation
    @PostMapping("/add")
    public Rating addRating(@Valid @RequestBody Rating rating) {
        return ratingService.addRating(rating);
    }

    // Récupérer les évaluations reçues par un utilisateur
    @GetMapping("/received/{userId}")
    public List<Rating> getReceivedRatings(@PathVariable int userId) {
        return ratingService.getReceivedRatings(userId);
    }

    // Récupérer les évaluations postées par un utilisateur
    @GetMapping("/posted/{userId}")
    public List<Rating> getPostedRatings(@PathVariable int userId) {
        return ratingService.getPostedRatings(userId);
    }
}
