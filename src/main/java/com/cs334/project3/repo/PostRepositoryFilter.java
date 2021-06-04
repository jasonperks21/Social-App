package com.cs334.project3.repo;

import com.cs334.project3.repo.resultset.PostResultSetMapping;
import org.locationtech.jts.geom.Point;

import java.time.ZonedDateTime;
import java.util.List;

public interface PostRepositoryFilter {
    List<PostResultSetMapping> filter(Long userId, Long filterUserId, Long groupId, ZonedDateTime time, boolean after, Double radius, Point loc);
}
