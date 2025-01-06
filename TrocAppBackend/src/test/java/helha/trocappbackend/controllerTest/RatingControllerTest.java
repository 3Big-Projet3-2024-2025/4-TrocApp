package helha.trocappbackend.controllerTest;

import helha.trocappbackend.controllers.RatingController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import helha.trocappbackend.models.Rating;
import helha.trocappbackend.services.RatingService;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test class for the RatingController.
 */
@SpringBootTest
public class RatingControllerTest {

    /**
     * Mocked RatingService for testing.
     */
    @Mock
    private RatingService ratingService;

    /**
     * Injected RatingController for testing.
     */
    @InjectMocks
    private RatingController ratingController;

    /**
     * Initializes mocks before each test.
     */
    public RatingControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the addRating method.
     * Should save and return the new rating.
     */
    @Test
    @DisplayName("Add rating: should save and return the new rating")
    void testAddRating() {
        Rating mockRating = new Rating();
        when(ratingService.addRating(mockRating)).thenReturn(mockRating);

        Rating result = ratingController.addRating(mockRating);

        assertNotNull(result);
        assertEquals(mockRating, result);
        verify(ratingService, times(1)).addRating(mockRating);
    }

    /**
     * Tests the getReceivedRatings method.
     * Should return ratings received by a specific user.
     */
    @Test
    @DisplayName("Get received ratings: should return ratings received by a specific user")
    void testGetReceivedRatings() {
        int userId = 1;
        List<Rating> mockRatings = Arrays.asList(new Rating(), new Rating());
        when(ratingService.getReceivedRatings(userId)).thenReturn(mockRatings);

        List<Rating> result = ratingController.getReceivedRatings(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(ratingService, times(1)).getReceivedRatings(userId);
    }

    /**
     * Tests the getPostedRatings method.
     * Should return ratings posted by a specific user.
     */
    @Test
    @DisplayName("Get posted ratings: should return ratings posted by a specific user")
    void testGetPostedRatings() {
        int userId = 2;
        List<Rating> mockRatings = Arrays.asList(new Rating());
        when(ratingService.getPostedRatings(userId)).thenReturn(mockRatings);

        List<Rating> result = ratingController.getPostedRatings(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(ratingService, times(1)).getPostedRatings(userId);
    }

    /**
     * Tests the deleteRating method.
     * Should delete rating and return HTTP 200 status.
     */
    @Test
    @DisplayName("Delete rating: should delete rating and return HTTP 200 status")
    void testDeleteRating() {
        int ratingId = 3;

        ResponseEntity<Void> response = ratingController.deleteRating(ratingId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(ratingService, times(1)).deleteRating(ratingId);
    }

    /**
     * Tests the getAverageRating method when ratings exist.
     * Should return the correct average for received ratings.
     */
    @Test
    @DisplayName("Get average rating: should return the correct average for received ratings")
    void testGetAverageRating_WhenRatingsExist() {
        int userId = 1;
        Rating rating1 = new Rating();
        rating1.setNumberStars(4);
        Rating rating2 = new Rating();
        rating2.setNumberStars(5);
        when(ratingService.getReceivedRatings(userId)).thenReturn(Arrays.asList(rating1, rating2));

        Double result = ratingController.getAverageRating(userId);

        assertNotNull(result);
        assertEquals(4.5, result);
    }

    /**
     * Tests the getAverageRating method when no ratings exist.
     * Should return 0.0 when no ratings exist.
     */
    @Test
    @DisplayName("Get average rating: should return 0.0 when no ratings exist")
    void testGetAverageRating_WhenNoRatingsExist() {
        int userId = 1;
        when(ratingService.getReceivedRatings(userId)).thenReturn(Arrays.asList());

        Double result = ratingController.getAverageRating(userId);

        assertNotNull(result);
        assertEquals(0.0, result);
    }
}