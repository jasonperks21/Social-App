//import { Check } from '@material-ui/icons';
import React, { useEffect, useState } from 'react';
//import './App.css';
import FriendList from './friendsList';
import WithLoading from './WithGroupLoading';

function Friends(userId) {
  const id = userId.userId;
  const token = userId.token;
  const ListLoading = WithLoading(FriendList);
  const [appState, setAppState] = useState({
    loading: false,
    friends: null
  });

  useEffect(() => {
    if(token === 'null' || id === 'null'){
      console.log('test')
      localStorage.setItem( 'token', null);
      localStorage.setItem( 'userId', null);
      window.location.replace('/login');
    }
    setAppState({ loading: true });
    const head = {'headers': {'Authorization': 'Bearer ' + token}}
    const apiUrl = '/app/friends/?userId='+id;
    fetch(apiUrl, head)
      .then((res) => res.json())
      .then((friends) => {
        if(friends.status===401){
          console.log('test')
          localStorage.setItem( 'token', null);
          localStorage.setItem( 'userId', null);
          window.location.replace('/login');
        }
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
