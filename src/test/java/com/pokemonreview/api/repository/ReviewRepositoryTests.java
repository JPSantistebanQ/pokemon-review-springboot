package com.pokemonreview.api.repository;

import com.pokemonreview.api.containers.BaeldungPostgresqlContainer;
import com.pokemonreview.api.containers.MyPostgreSqlTest;
import com.pokemonreview.api.models.Review;
import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.Optional;

@MyPostgreSqlTest
class ReviewRepositoryTests {

    @ClassRule
    public static PostgreSQLContainer<BaeldungPostgresqlContainer> postgreSQLContainer = BaeldungPostgresqlContainer.getInstance();

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void ReviewRepository_SaveAll_ReturnsSavedReview() {
        Review review = Review.builder().title("title").content("content").stars(5).build();

        Review savedReview = reviewRepository.save(review);

        Assertions.assertThat(savedReview).isNotNull();
        Assertions.assertThat(savedReview.getId()).isGreaterThan(0);
    }

    @Test
    void ReviewRepostory_GetAll_ReturnsMoreThenOneReview() {
        Review review = Review.builder().title("title").content("content").stars(5).build();
        Review review2 = Review.builder().title("title").content("content").stars(5).build();

        reviewRepository.save(review);
        reviewRepository.save(review2);

        List<Review> reviewList = reviewRepository.findAll();

        Assertions.assertThat(reviewList).isNotNull();
        Assertions.assertThat(reviewList.size()).isEqualTo(2);
    }

    @Test
    void ReviewRepository_FindById_ReturnsSavedReview() {
        Review review = Review.builder().title("title").content("content").stars(5).build();

        reviewRepository.save(review);

        Review reviewReturn = reviewRepository.findById(review.getId()).get();

        Assertions.assertThat(reviewReturn).isNotNull();
    }

    @Test
    void ReviewRepository_UpdateReview_ReturnReview() {
        Review review = Review.builder().title("title").content("content").stars(5).build();

        reviewRepository.save(review);

        Review reviewSave = reviewRepository.findById(review.getId()).get();
        reviewSave.setTitle("title");
        reviewSave.setContent("content");
        Review udpatedPokemon = reviewRepository.save(reviewSave);

        Assertions.assertThat(udpatedPokemon.getTitle()).isNotNull();
        Assertions.assertThat(udpatedPokemon.getContent()).isNotNull();
    }

    @Test
    void ReviewRepository_ReviewDelete_ReturnReviewIsEmpty() {
        Review review = Review.builder().title("title").content("content").stars(5).build();

        reviewRepository.save(review);

        reviewRepository.deleteById(review.getId());
        Optional<Review> reviewReturn = reviewRepository.findById(review.getId());

        Assertions.assertThat(reviewReturn).isEmpty();
    }

}
