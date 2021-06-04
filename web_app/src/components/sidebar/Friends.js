//import { Check } from '@material-ui/icons';
import React, { useEffect, useState } from 'react';
//import './App.css';
import FriendList from './friendsList';
import WithLoading from './WithGroupLoading';

function Friends(userId) {
  const id = userId.userId;
  const ListLoading = WithLoading(FriendList);
  const [appState, setAppState] = useState({
    loading: false,
    friends: null
  });

  useEffect(() => {
    setAppState({ loading: true });
    const apiUrl = '/app/friends/?userId='+id;
    fetch(apiUrl)
      .then((res) => res.json())
      .then((friends) => {
        setAppState({ loading: false, friends: friends});
      });
      // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [setAppState]);

  return (
    <div className='Friends'>
        <ListLoading isLoading={appState.loading} friends={appState.friends} />     
    </div>
  );
}
export default Friends;
