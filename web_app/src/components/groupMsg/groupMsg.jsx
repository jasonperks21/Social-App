import "./groupMsg.css";
import React from 'react';
import TimeAgo from 'react-timeago';

class GroupMsg extends React.Component {
  constructor(props){
    super(props);
    this.state = {
        msg: this.props.msg,
        expand: false,
        setMsg: null

    }
    this.clicked = this.clicked.bind(this);
    this.post = this.post.bind(this);
    this.handleChange = this.handleChange.bind(this);
}
handleChange(event) {
  this.setState({setMsg: event.target.value});
}
  clicked(){
    this.setState({expand: true});
  }
  post(){
    alert(this.state.setMsg);
    this.setState({setMsg: ''});
  }
  reply(){
    return (<div className="postBottomRight" ><input className="replyMsg" type='text' value={this.state.setMsg} onChange={this.handleChange} placeholder="Reply Message"></input>
    <button className="sendIcon" onClick={()=> this.post()}>Post</button>  
    </div>);
  }

  expand(){
    if(this.state.expand){
      return <div> {checkReply(this.state.msg.replies, this.state.expand)}{this.reply()}</div>;
    }
    let num = this.state.msg.replies.length;
    if(num > 0){
      let text = "1 Reply";
      if(num>1){
        text = String(num)+" Replies"
      }
      return (<div className = "postBottomRight">
        <img src="/assets/comment.png" alt="" className="commentIcon" />
        <button className="postCommentText" onClick={() => this.clicked()}> {text} </button>
      </div>);
    }
   return this.reply();
  }

  render(){
    const msg = this.state.msg; 
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
            {this.expand()}
        </div>
        
      </div>
    </div>
  );
  }
}

export default GroupMsg;

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