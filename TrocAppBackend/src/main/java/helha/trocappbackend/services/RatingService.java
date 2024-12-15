package helha.trocappbackend.services;

import helha.trocappbackend.models.Rating;
import helha.trocappbackend.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
//@Primary
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public Rating addRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    public List<Rating> getReceivedRatings(Long userId) {
        return ratingRepository.findByReceiverId(userId);
    }

    public List<Rating> getPostedRatings(Long userId) {
        return ratingRepository.findByPosterId(userId);
    }
}
