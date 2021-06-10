import React from 'react';

const FriendList = (props) => {
  const { friends } = props;
  //console.log(friends)
  if (!friends || friends.length === 0) return <p>No Friends Yet</p>;
  if (friends===null) return <p>No Friends, sorry</p>;
  if (friends.status===404||friends.status===405||friends.status===400||friends.status===500){return <p>No Friends, sorry</p>;}
  if(friends.status===401){
    return <p>Unauthorized</p>;
  }
  return (
    <ul className="sidebarFriendList">
      {friends.map((friend) => {
          return <Friend friend={friend}/>
      })}
    
    </ul>
);
};
export default FriendList;

class Friend extends React.Component{
  constructor(props){
    super(props);
    this.state = {
        friend: this.props.friend
    }
  }

  render(){
    //console.log(this.state.friend)
    return (
      <li className="sidebarFriend">
      <img src="/assets/person/2.jpeg" alt="" className="sidebarFriendImg"/>
          <span className="sidebarFriendName">{this.state.friend.friendDisplayName}</span>
        </li>
      );
  }
}