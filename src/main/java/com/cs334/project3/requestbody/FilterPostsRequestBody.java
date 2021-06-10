package com.cs334.project3.requestbody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class FilterPostsRequestBody {

    @NotNull
    private Long userId;

    private Long filterUsedId, groupId;
    private Boolean after;
    private ZonedDateTime time;
    private Double radiusKm, longitude, latitude;
}
