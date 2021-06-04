import React from 'react';
//import { Group, PermIdentity} from "@material-ui/icons"; //Person

const FriendList = (props) => {
  const { friends } = props;
  if (!friends || friends.length === 0) return <p>No Friends Yet</p>;
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