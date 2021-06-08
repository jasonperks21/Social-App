import "./groupList.css";
import React, { useEffect, useState } from 'react';
import WithLoading from '../sidebar/WithGroupLoading'
import GroupMsg from "../groupMsg/groupMsg";
//import Share from "../share/Share";

export default function GroupList(ids) {
  let gId = new URLSearchParams(window.location.search).get("gid");
  // console.log(gId);
  const id = ids.userId;
  const token = ids.token;
  const ListLoading = WithLoading(DisplayGroupMsg);
  const [appState, setAppState] = useState({
    loading: false,
    messages: null,
    groupInfo: null
  });

  useEffect(() => {
    setAppState({ loading: true });
    const head = {'headers': {'Authorization': 'Bearer ' + token}}
    const apiUrl = '/app/posts/'+id+'/'+gId;
    const apiUrl2 = '/app/groups/?userId='+id;
    Promise.all([fetch(apiUrl, head).then(res => res.json()),
    fetch(apiUrl2, head).then(res => res.json())])
    .then(([urlOneData, urlTwoData]) => {
        let group =[];
        if(urlTwoData !== null){
          if (urlTwoData.status!==404&&urlTwoData.status!==400&&urlTwoData.status!==405&&urlTwoData.status!==401&&urlTwoData.status!==500){
            urlTwoData.forEach(g => {
              if(String(g.groupId) === gId){
                group = g;
              }
            });}
        } 
        setAppState({loading:false, messages:urlOneData, groupInfo:group});
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
  return (
    <div className="feed">
      <div className="feedWrapper">
        <ListLoading isLoading={appState.loading} messages={appState.messages} groupInfo={appState.groupInfo} userId={id} token={token}/>
      </div>
    </div>
  );
}

async function deleteFunc(groupInfo, userId, token){
  // Simple POST request with a JSON body using fetch
  const requestOptions = {
      method: 'DELETE',
      headers: { 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + token },
      body: JSON.stringify({userId: parseInt(userId), groupId: groupInfo.groupId})
  };
  await fetch('/app/groups', requestOptions)
      .then(response => {
          console.log(requestOptions.body)
          console.log(response);
          window.location.replace('/');
      });
}


function deleteGroup(groupInfo, userId, token){
  let isExecuted = window.confirm("Are you sure you want to delete "+groupInfo.groupName+"?");
  if(isExecuted){
    deleteFunc(groupInfo, userId, token);
  }
  else{
    alert("Action aborted");
  }
}

const DisplayGroupMsg = (props) => {
  const { messages, groupInfo, userId, token} = props;
  console.log(groupInfo)
  console.log(messages)
  if (!messages||groupInfo.error) return <h2>Group does not exist</h2>; 
  if(groupInfo.length === 0){return <h2>Group does not exist</h2>; }
  if( messages.length === 0|| messages.status) return (<div> <h2>{groupInfo.groupName}</h2>
    <p>No messages yet!</p>
    {checkDeleteButton(groupInfo, userId)}
    </div>);
  
  return (
    <div className="GroupMessages">
      <h2>{groupInfo?.groupName}</h2>
      
    {checkDeleteButton(groupInfo, userId, token)}
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

function checkDeleteButton(groupInfo, userId, token){
  if(groupInfo.adminOnThisGroup){
    return <button className="deleteGroup" onClick={() => deleteGroup(groupInfo, userId, token)}>Delete Group</button>;
  }
}