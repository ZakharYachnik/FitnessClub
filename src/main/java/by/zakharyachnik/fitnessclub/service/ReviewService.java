package by.zakharyachnik.fitnessclub.service;

import by.zakharyachnik.fitnessclub.dto.ReviewDto;
import by.zakharyachnik.fitnessclub.exceptions.NotFoundException;

import java.util.List;

public interface ReviewService {
    List<ReviewDto> findAll();

    ReviewDto save(String reviewText, String username) throws NotFoundException;
}
