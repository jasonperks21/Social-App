import "./groupList.css";
import React, { useEffect, useState } from 'react';
import WithLoading from '../sidebar/WithGroupLoading'
import GroupMsg from "../groupMsg/groupMsg";

export default function GroupList(ids) {
  let gId = new URLSearchParams(window.location.search).get("gid");
  // console.log(gId);
  const id = ids.userId;
  const ListLoading = WithLoading(DisplayGroupMsg);
  const [appState, setAppState] = useState({
    loading: false,
    messages: null,
  });

  useEffect(() => {
    setAppState({ loading: true });
    const apiUrl = '/app/posts/'+id+'/'+gId;
    fetch(apiUrl)
      .then((res) => res.json())
      .then((messages) => {
        setAppState({ loading: false, messages: messages.data});
      });
      // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [setAppState]);
  return (
    <div className="feed">
      <div className="feedWrapper">
        <ListLoading isLoading={appState.loading} messages={appState.messages}/>
      </div>
    </div>
  );
}

function deleteGroup(){
  let isExecuted = window.confirm("Are you want to delete group?");
  if(isExecuted){
    alert("Group deleted!");
  }
  else{
    alert("Action aborted");
  }
}

const DisplayGroupMsg = (props) => {
  const { messages} = props;
  //console.log(messages)
  if (!messages) return <h2>Group does not exist</h2>; 
  if( messages.length === 0) return (<div>
    <h2>No messages yet!</h2>
    <button className="deleteGroup" onClick={() => deleteGroup()}>Delete Group</button>
    </div>);
  
  return (
    <div className="GroupMessages">
      <h2>{messages[0].groupName}</h2>
      <button className="deleteGroup" onClick={() => deleteGroup()}>Delete Group</button>
      {messages.map((msg) => {
         return (
          <div className="msgWrapper">
            <GroupMsg msg={msg} />
          </div>
          );
         })}
    </div>
  );
};