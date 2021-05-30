import "../post/post.css";
import TimeAgo from 'react-timeago';

export default function GroupMsg(message) {
    let bool = false;
    const msg = message.msg; 
  return (
    <div className="post">
      <div className="postWrapper">
        <div className="postTop">
          <div className="postTopLeft">
            <span className="postUsername">{msg.userDisplayName}</span>
            <span className="postDate"><TimeAgo date={msg.timePosted} locale="en-US"/></span>
          </div>
        </div>
        <div className="postCenter">
          <span className="postText">{msg.message}</span>
        </div>
        <div className="postBottom">       
            {checkReply(msg.replies.length, bool, msg.replies)}
        </div>
      </div>
    </div>
  );
}

function checkReply(num, bool, replies){
    if(bool){
        return <p>text</p>;
    }
    if(num>0){
        return (<div className="postBottomRight"><img src="/assets/comment.png" alt="" className="commentIcon" />
                <span className="postCommentText">{num} Replies</span></div>);
    }
    else{
        return <div></div>;
    }
}