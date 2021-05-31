import "./post.css";
import { MoreVert } from "@material-ui/icons";

export default function Post({post}) {
  return (
    <div className="post">
      <div className="postWrapper">
        <div className="postTop">
          <div className="postTopLeft">
            <img
              className="postProfileImg"
              src="/assets/person/1.jpeg"
              alt=""
            />
            <span className="postUsername">{post?.userDisplayName}</span>
            <span className="postDate">5 min ago</span>
          </div>
          <div className="postTopRight">
            <MoreVert />
          </div>
        </div>
        <div className="postCenter">
          <span className="postText">{post?.message}</span>
          {/* <img className="postImg" src="/assets/post/1.jpeg" alt="" /> */}
        </div>
        <div className="postBottom">
          <div className="postBottomLeft">
            <img src="/assets/like.png" alt="" className="likeIcon" />
            <span className="postLikeCounter">32 people like it</span>
          </div>
          <div className="postBottomRight">
            <img src="/assets/comment.png" alt="" className="commentIcon" />
            <span className="postCommentText">9 comments</span>
          </div>
          <div className="replies">
           
          </div>
        </div>
      </div>
    </div>
  );
}
