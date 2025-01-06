package helha.trocappbackend.serviceTest;

import helha.trocappbackend.services.RatingService;
import org.junit.jupiter.api.*;
import helha.trocappbackend.models.Rating;
import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.RatingRepository;
import helha.trocappbackend.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test class for the RatingService.
 */
@SpringBootTest
public class RatingServiceTest {

    /**
     * Injected RatingService for testing.
     */
    @InjectMocks
    private RatingService ratingService;

    /**
     * Mocked RatingRepository for testing.
     */
    @Mock
    private RatingRepository ratingRepository;

    /**
     * Mocked UserRepository for testing.
     */
    @Mock
    private UserRepository userRepository;

    /**
     * Initializes mocks before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the addRating method.
     * Should successfully add a rating.
     */
    @Test
    @DisplayName("Add rating: should successfully add a rating")
    void testAddRating_Success() {
        User poster = new User();
        poster.setId(1);
        User receiver = new User();
        receiver.setId(2);

        Rating rating = new Rating();
        rating.setPoster(poster);
        rating.setReceiver(receiver);

        when(userRepository.findById(1)).thenReturn(Optional.of(poster));
        when(userRepository.findById(2)).thenReturn(Optional.of(receiver));
        when(ratingRepository.save(rating)).thenReturn(rating);

        Rating result = ratingService.addRating(rating);

        assertNotNull(result);
        assertEquals(poster, result.getPoster());
        assertEquals(receiver, result.getReceiver());
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).findById(2);
        verify(ratingRepository, times(1)).save(rating);
    }

    /**
     * Tests the addRating method.
     * Should throw exception if poster is not found.
     */
    @Test
    @DisplayName("Add rating: should throw exception if poster is not found")
    void testAddRating_PosterNotFound() {
        Rating rating = new Rating();
        User poster = new User();
        poster.setId(1);
        User receiver = new User();
        receiver.setId(2);

        rating.setPoster(poster);
        rating.setReceiver(receiver);

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ratingService.addRating(rating));
        assertEquals("Poster not found", exception.getMessage());

        verify(userRepository, times(1)).findById(1);
        verify(userRepository, never()).findById(2);
        verify(ratingRepository, never()).save(any(Rating.class));
    }

    /**
     * Tests the addRating method.
     * Should throw exception if receiver is not found.
     */
    @Test
    @DisplayName("Add rating: should throw exception if receiver is not found")
    void testAddRating_ReceiverNotFound() {
        Rating rating = new Rating();
        User poster = new User();
        poster.setId(1);
        User receiver = new User();
        receiver.setId(2);

        rating.setPoster(poster);
        rating.setReceiver(receiver);

        when(userRepository.findById(1)).thenReturn(Optional.of(poster));
        when(userRepository.findById(2)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ratingService.addRating(rating));
        assertEquals("Receiver not found", exception.getMessage());

        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).findById(2);
        verify(ratingRepository, never()).save(any(Rating.class));
    }

    /**
     * Tests the getReceivedRatings method.
     * Should return a list of received ratings.
     */
    @Test
    @DisplayName("Get received ratings: should return a list of received ratings")
    void testGetReceivedRatings() {
        int userId = 1;
        List<Rating> ratings = List.of(new Rating(), new Rating());
        when(ratingRepository.findByReceiverId(userId)).thenReturn(ratings);

        List<Rating> result = ratingService.getReceivedRatings(userId);

        assertEquals(2, result.size());
        verify(ratingRepository, times(1)).findByReceiverId(userId);
    }

    /**
     * Tests the getPostedRatings method.
     * Should return a list of posted ratings.
     */
    @Test
    @DisplayName("Get posted ratings: should return a list of posted ratings")
    void testGetPostedRatings() {
        int userId = 1;
        List<Rating> ratings = List.of(new Rating(), new Rating());
        when(ratingRepository.findByPosterId(userId)).thenReturn(ratings);

        List<Rating> result = ratingService.getPostedRatings(userId);

        assertEquals(2, result.size());
        verify(ratingRepository, times(1)).findByPosterId(userId);
    }

    /**
     * Tests the deleteRating method.
     * Should successfully delete a rating by ID.
     */
    @Test
    @DisplayName("Delete rating: should successfully delete a rating by ID")
    void testDeleteRating_Success() {
        int ratingId = 1;
        when(ratingRepository.existsById(ratingId)).thenReturn(true);

        ratingService.deleteRating(ratingId);

        verify(ratingRepository, times(1)).existsById(ratingId);
        verify(ratingRepository, times(1)).deleteById(ratingId);
    }

    /**
     * Tests the deleteRating method.
     * Should throw exception if rating is not found.
     */
    @Test
    @DisplayName("Delete rating: should throw exception if rating is not found")
    void testDeleteRating_RatingNotFound() {
        int ratingId = 1;
        when(ratingRepository.existsById(ratingId)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ratingService.deleteRating(ratingId));
        assertEquals("Rating not found", exception.getMessage());

        verify(ratingRepository, times(1)).existsById(ratingId);
        verify(ratingRepository, never()).deleteById(anyInt());
    }
}