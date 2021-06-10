import "./post.css";
import { MoreVert } from "@material-ui/icons";
import TimeAgo from 'timeago-react';

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
            <span className="postDate"><TimeAgo datetime={post?.timePosted}/></span>
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
            <img src="/assets/comment.png" alt="" className="commentIcon" />
            <span className="postCommentText">{post?.replies.length} comments</span>
          </div>
        </div>
      </div>
    </div>
  );
}
