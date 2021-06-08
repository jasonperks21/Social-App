package com.cs334.project3.requestbody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class FilterPostsRequestBody {
    private Long userId, filterUserId, groupId;
    private Boolean after;
    private ZonedDateTime time;
    private Double radiusKm, longitude, latitude;
}
