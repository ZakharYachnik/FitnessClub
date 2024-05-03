package by.zakharyachnik.fitnessclub.service.impl;

import by.zakharyachnik.fitnessclub.dto.ReviewDto;
import by.zakharyachnik.fitnessclub.entity.Review;
import by.zakharyachnik.fitnessclub.exceptions.NotFoundException;
import by.zakharyachnik.fitnessclub.mapper.ReviewMapper;
import by.zakharyachnik.fitnessclub.repository.ReviewRepository;
import by.zakharyachnik.fitnessclub.repository.UserRepository;
import by.zakharyachnik.fitnessclub.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    private final ReviewMapper reviewMapper;
    @Override
    public List<ReviewDto> findAll() {
        return reviewRepository.findAll()
                .stream()
                .map(reviewMapper::toReviewDto)
                .toList();
    }

    @Override
    public ReviewDto save(String reviewText, String username) throws NotFoundException {
        Review review = new Review()
                .setReviewText(reviewText)
                .setUser(userRepository.findByUsername(username)
                        .orElseThrow(() -> new NotFoundException("User with username " + username + " not found")));
        return reviewMapper.toReviewDto(reviewRepository.save(review));
    }
}
