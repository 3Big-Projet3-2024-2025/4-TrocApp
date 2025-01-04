package helha.trocappbackend.services;

import helha.trocappbackend.models.Rating;
import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.RatingRepository;
import helha.trocappbackend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
//@Primary
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;



    public Rating addRating(Rating rating) {
        // Charger le poster et le receiver à partir de leurs IDs
        User poster = userRepository.findById(rating.getPoster().getId())
                .orElseThrow(() -> new RuntimeException("Poster not found"));
        User receiver = userRepository.findById(rating.getReceiver().getId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        rating.setPoster(poster);
        rating.setReceiver(receiver);

        return ratingRepository.save(rating);
    }

    public List<Rating> getReceivedRatings(int userId) {
        return ratingRepository.findByReceiverId(userId);
    }

    public List<Rating> getPostedRatings(int userId) {
        return ratingRepository.findByPosterId(userId);
    }

    @Transactional
    public void deleteRating(int ratingId) {
        if (!ratingRepository.existsById(ratingId)) {
            throw new RuntimeException("Rating not found");
        }
        ratingRepository.deleteById(ratingId);
    }
}
