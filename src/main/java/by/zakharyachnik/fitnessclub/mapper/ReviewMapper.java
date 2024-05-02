package by.zakharyachnik.fitnessclub.mapper;

import by.zakharyachnik.fitnessclub.dto.ReviewDto;
import by.zakharyachnik.fitnessclub.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ReviewMapper {

    @Mapping(target = "username", source = "user.username")
    ReviewDto toReviewDto(Review review);

    @Mapping(target = "user.username", source = "username")
    Review toReview(ReviewDto reviewDto);
}
