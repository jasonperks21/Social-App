import React from 'react';
import "./feed.css";
import Share from "../share/Share";
import { useEffect, useState } from "react";
import axios from "axios";
import TimeAgo from 'timeago-react';

export default function GetPosts(userId) {
  const id = userId.userId;
    const token = userId.token;
    const [posts,setPosts] = useState({});
    
  useEffect(()=>{
    const head = {headers: {'Authorization': 'Bearer ' + token}, params: {
      userId: id,
      filterUsedId: id,
      groupId: '',
      after: '',
      time: '',
      radiusKm: '',
      longitude: '',
      latitude: ''
    }};
    const postURL = '/app/posts';
    const fetchPosts = async() => {
      const res = await axios.get(postURL, head);
      setPosts(res.data);
    }
    fetchPosts();
  })
  
    return (
      <div className="feed">
        <div className="feedWrapper">
          <Share />
          {Object.values(posts).map((p) => (
            <Feed key={p.postId} msg={p} />
          ))}
        </div>
      </div>
    );
}

class Feed extends React.Component {
  constructor(props){
    super(props);
    this.state = {
        msg: this.props.msg,
        expand: false,
        setMsg: ''
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
    if(this.state.setMsg === ''){
      return <div className="postBottomRight" ><input className="replyMsg" type='text' value={this.state.setMsg} onChange={this.handleChange} placeholder="Reply Message"></input></div>;
    }
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
          <img
              className="postProfileImg"
              src="/assets/person/1.jpeg"
              alt=""
            />
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
                obj.push(<Feed msg={element} />);
                if(element.replies>0){
                    displayReply(element.replies);
                }
            });
            return <div className="PostBottomRight">{obj} </div>;
        }
        
    }
    return <div></div>;
}