//import { Check } from '@material-ui/icons';
import React, { useEffect, useState } from 'react';
//import './App.css';
import GroupList from './groupList';
import WithLoading from './WithGroupLoading';

function Groups(userId) {
  const id = userId.userId;
  const ListLoading = WithLoading(GroupList);
  const [appState, setAppState] = useState({
    loading: false,
    groups: null,
    numOfGroups: 0
  });

  useEffect(() => {
    setAppState({ loading: true });
    const apiUrl = '/app/groups/?userId='+id;
    fetch(apiUrl)
      .then((res) => res.json())
      .then((groups) => {
        
        setAppState({ loading: false, groups: groups});
      });
      // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [setAppState]);

  function checkMore3(){
    if(appState.numOfGroups>3){
        return <button className="sidebarButton">Show More</button>;
    }
    else return <div></div>
  }

  return (
    <div className='Groups'>
        <ListLoading isLoading={appState.loading} groups={appState.groups} />
        {checkMore3()}
        
    </div>
  );
}
export default Groups;
