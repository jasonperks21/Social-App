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
    public List filter(Long userId, Long filterUserId, Long groupId, ZonedDateTime time, Boolean after, Double radiusKm, Point loc) {
        String ufilt = filterUserId == null ? "" : "gm.user_id = " + filterUserId;
        String gfilt = groupId == null ? "" : "p.group_id = " + groupId;
        String tfilt = time == null ? "" : "p.timestamp" + (after?" >= ":" <= ") + "?1";
        String rfilt = radiusKm == null ? "" : "ST_Distance_Sphere(?2, p.location)/1000 <= " + radiusKm;
        String[] filt = {ufilt, gfilt, tfilt, rfilt};
        String filterCriteria = "";
        for(int i = 0; i < 4; i++){
            String s = filt[i];
            if(s != ""){
                filterCriteria = filterCriteria + " and " + s;
            }
        }
        String filterSQL = "-- find root posts\n" +
                "(select p.post_id as root_id\n" +
                "from posts p\n" +
                "inner join group_members gm on p.member_id=gm.member_id\n" +
                "inner join postgroups pg on p.group_id=pg.group_id\n" +
                "inner join group_members mem on p.group_id = mem.group_id\n" +
                "inner join categories c on p.category_id=c.category_id\n" +
                "left join posts pr on p.root_post_id = pr.post_id\n" +
                "-- find membership of current user and get root posts matching criteria\n" +
                "where mem.user_id = " + userId + " and\n" +
                "p.root_post_id is null\n" +
                "-- criteria\n" +
                 filterCriteria +
                ") \n" +
                "-- join root post comments\n" +
                "as pf on (pf.root_id = p.post_id or pf.root_id = p.root_post_id)";

        String sql = "select group_name as groupName, p.timestamp as timePosted, pg.group_id as groupId, \n" +
                "p.post_id as postId, p.replied_post_id as replyId, message,\n" +
                "u.display_name as userDisplayName, u.user_id as userId,\n" +
                "gm.member_id as groupMemberId, category_name as category, c.category_id as categoryId\n" +
                " from posts p right join\n" +
                filterSQL +
                "-- retrieve relevant data of root posts and comments\n" +
                "inner join group_members gm on p.member_id=gm.member_id\n" +
                "inner join postgroups pg on p.group_id=pg.group_id\n" +
                "inner join users u on u.user_id=gm.user_id\n" +
                "inner join categories c on p.category_id=c.category_id\n" +
                "order by p.timestamp desc";
        Query q = em.createNativeQuery(sql,"postResult");
        if (tfilt != "") q.setParameter(1, time);
        if (rfilt != "") q.setParameter(2, loc);
        List<PostResultSetMapping> l =  (List<PostResultSetMapping>)q.getResultList();
        return l;
    }
}
