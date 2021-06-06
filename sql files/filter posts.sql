select group_name as groupName, p.timestamp as timePosted, pg.group_id as groupId, 
p.post_id as postId, p.replied_post_id as replyId, message,
u.display_name as userDisplayName, u.user_id as userId,
gm.member_id as groupMemberId, category_name as category, c.category_id as categoryId
 from posts p right join
-- find root posts
(select p.post_id as root_id
from posts p
inner join group_members gm on p.member_id=gm.member_id
inner join postgroups pg on p.group_id=pg.group_id
inner join group_members mem on p.group_id = mem.group_id
inner join categories c on p.category_id=c.category_id
left join posts pr on p.root_post_id = pr.post_id
-- find membership of current user and get root posts matching criteria
where mem.user_id = 301 and
p.root_post_id is null and
-- criteria
gm.user_id = 302 and
c.category_id is not null and
ST_Distance_Sphere(p.location, Point(25, 25))/1000 <= 1500 and
p.timestamp >= '2021-05-01'
) 
-- join root post comments
as pf on (pf.root_id = p.post_id or pf.root_id = p.root_post_id)
-- retrieve relevant data of root posts and comments
inner join group_members gm on p.member_id=gm.member_id
inner join postgroups pg on p.group_id=pg.group_id
inner join users u on u.user_id=gm.user_id
inner join categories c on p.category_id=c.category_id
order by p.timestamp desc