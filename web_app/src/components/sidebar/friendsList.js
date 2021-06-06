import React from 'react';
//import { Group, PermIdentity} from "@material-ui/icons"; //Person

const FriendList = (props) => {
  const { friends } = props;
  //console.log(friends)
  if (!friends || friends.length === 0) return <p>No Friends Yet</p>;
  if (friends===null) return <p>No Friends, sorry</p>;
  if (friends.status===404||friends.status===405||friends.status===400||friends.status===500){return <p>No Friends, sorry</p>;}
  return (
    <ul className="sidebarFriendList">
      {friends.map((friend) => {
          return (
            <li className="sidebarFriend">
            <img src="/assets/person/2.jpeg" alt="" className="sidebarFriendImg"/>
                <span className="sidebarFriendName">{friend.friendId}</span>
              </li>
            );
      })}
    
    </ul>
);
};
export default FriendList;