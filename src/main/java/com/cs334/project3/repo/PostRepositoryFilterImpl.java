package com.cs334.project3.repo;

import com.cs334.project3.repo.resultset.PostResultSetMapping;
import org.locationtech.jts.geom.Point;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.ZonedDateTime;
import java.util.List;

public class PostRepositoryFilterImpl implements PostRepositoryFilter {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<PostResultSetMapping> filter(Long userId, Long filterUserId, Long groupId, ZonedDateTime time, boolean after, Double radius, Point loc) {
        String ufilt = filterUserId == null ? null : "pu.user_id = " + filterUserId;
        String gfilt = groupId == null ? null : "pg.group_id = " + groupId;
        String tfilt = time == null ? null : "p.timestamp" + (after?" > ":" < ") + "?1";
        String rfilt = radius == null ? null : "ST_Distance_Sphere(?2, p.location) < " + radius;
        String[] filt = {ufilt, gfilt, tfilt, rfilt};
        String sqlfilter = "";
        for(int i = 0; i < 4; i++){
            String s = filt[i];
            if(s != null){
                sqlfilter = sqlfilter + " and " + s;
            }
        }
        String sql = "select group_name as groupName,\n" +
                "timestamp as timePosted, pg.group_id as groupId, p.post_id as postId, p.replied_post_id as replyId, message,\n" +
                "pu.display_name as userDisplayName, pu.user_id as userId,\n" +
                "pgm.member_id as groupMemberId, category_name as category, c.category_id as categoryId\n" +
                "FROM posts p, postgroups pg, group_members gm, group_members pgm, users u, users pu, categories c\n" +
                "where u.user_id = gm.user_id and\n" +
                "gm.group_id = pg.group_id and\n" +
                "p.group_id = pg.group_id and\n" +
                "gm.user_id = "+ userId + " and\n" +
                "pu.user_id = pgm.user_id and\n" +
                "pgm.member_id = p.member_id and\n" +
                "c.category_id = p.category_id" +
                 sqlfilter + " order by timePosted desc";
        Query q = em.createNativeQuery(sql,"postResult");
        return q.getResultList();
    }
}
