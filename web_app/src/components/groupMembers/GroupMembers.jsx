import "./rightbar.css";
//import { Message } from "@material-ui/icons";
import React, { useEffect, useState } from 'react';

import  WithLoading from "../sidebar/WithGroupLoading";

export default function GroupMembers(userId) {
  //const id = userId.userId;
  const gId = new URLSearchParams(window.location.search).get("gid");
  const ListLoading = WithLoading(MemberList);
  const [appState, setAppState] = useState({
    loading: false,
    members: null
  });

  useEffect(() => {
    setAppState({ loading: true });
    const apiUrl = '/app/groupmember/?gid='+gId;
    fetch(apiUrl)
      .then((res) => res.json())
      .then((members) => {
        setAppState({ loading: false, members: members});
       
      });
      // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [setAppState]);
  if(!gId){
    return(<div className="feed">
      <div className="feedWrapper">
      <h2>Choose a group</h2>
      </div>
    </div>);
  }
  return (<div className='Groups'>
          <ListLoading isLoading={appState.loading} members={appState.members} />
          </div>);
}

const MemberList = (props) =>{
  const { members } = props;
  if(members === null) return <p>Please select group</p>
  if (!members|| members.length === 0) return <p>No Members Yet</p>;
  if(members.error !== null) return <p>Please select group</p>
  return (
    <div className="rightbar">
      <div className="rightbarWrapper">
        <h3 className="messageHeader">Group Members</h3>
        <ul className="messageList">
        {members.map((member) => {
          return (
            <li className="messageListItem">
            <div className="messageWrapper">
              <div className="messageTop">
                <img
                  src="/assets/person/4.jpeg"
                  alt=""
                  className="messageProfileImg"
                />
                <span className="sidebarFriendName">{member.userId}</span>
                </div>
            </div>
          </li>
            );
          })}     
      </ul>

      </div>
    </div>
  );

}
