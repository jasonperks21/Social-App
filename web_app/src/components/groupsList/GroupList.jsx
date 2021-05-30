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
        setAppState({ loading: false, messages: messages.data });
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

const DisplayGroupMsg = (props) => {
  const { messages } = props;
  if (!messages || messages.length === 0) return <h2>Please select a group</h2>;
  return (
    <div className="GroupMessages">
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