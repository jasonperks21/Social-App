import "./groupMsg.css";
import TimeAgo from 'react-timeago';

export default function GroupMsg(message) {
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
            {checkReply(msg.replies)}
        </div>
      </div>
    </div>
  );
}

function checkReply(replies){
    let num = replies.length
    if(num>0){
        return <div>{displayReply(replies)}</div>;
    } 

    else{
        return <div></div>;
    }
}

function displayReply(replies){
    if(replies !== undefined){
        if(replies.length>0){
            let obj = [];
            replies.forEach(element => {
                obj.push(<GroupMsg msg={element} />);
                if(element.replies>0){
                    displayReply(element.replies);
                }
            });
            return <div className="PostBottomRight">{obj} </div>;
        }
        
    }
    return <div></div>;
}