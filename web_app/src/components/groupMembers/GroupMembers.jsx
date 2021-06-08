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
    members: null,
    admin: false
  });

  useEffect(() => {
    setAppState({ loading: true });
    const apiUrl = '/app/groupmember/?groupId='+gId;
    fetch(apiUrl)
      .then((res) => res.json())
      .then((members) => {
        let admin = false;
        members?.forEach(element => {
          if(element?.userId === parseInt(userId.userId)){ 
            if(element.admin){
              admin = true;
            }
          }
        });
        setAppState({ loading: false, members: members, admin:admin});
       
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
          <ListLoading isLoading={appState.loading} members={appState.members} admin={appState.admin}/>
          </div>);
}

const MemberList = (props) =>{
  const { members, userId, admin } = props;
  if(members === null) return <p>Please select group</p>
  if (!members|| members.length === 0) return <p>No Members Yet</p>;
  if(members.status === 400||members.status === 404||members.status === 405||members.status === 500) return <p>Please select group</p>
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
                <span className="messageUsername">{member.userId}</span>
                </div>
                <div className="messageBottom">
                {checkAdmin(member.admin)}
              </div>
            </div>
          </li>
            );
          })}     
      </ul>
      <hr className="rightbarHr" />
      </div>
    </div>
  );

}

function checkAdmin(bool){
  if(bool){
    return <span className="messageText">Admin</span>;
  }
  return <span className="messageText">Member</span>;
}
