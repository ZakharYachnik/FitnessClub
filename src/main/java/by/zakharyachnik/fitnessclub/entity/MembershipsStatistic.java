package by.zakharyachnik.fitnessclub.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode
public class MembershipsStatistic {

    private int activeCount;

    private Double totalAmount;

    private Integer mostCommonDuration;

    private Integer rarestDuration;

    private Long mostCommonDurationCount;

    private Long rarestDurationCount;

}
