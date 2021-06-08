//import { Check } from '@material-ui/icons';
import React, { useEffect, useState } from 'react';
//import './App.css';
import GroupList from './groupList';
import WithLoading from './WithGroupLoading';

function Groups(userId) {
  const id = userId.userId;
  const token = userId.token;
  //console.log(token);
  const ListLoading = WithLoading(GroupList);
  const [appState, setAppState] = useState({
    loading: false,
    groups: null,
    numOfGroups: 0
  });

  useEffect(() => {
    //console.log(id);
    //console.log(token);
    setAppState({ loading: true });
    const head = {'headers': {'Authorization': 'Bearer ' + token}}
    const apiUrl = '/app/groups/?userId='+id;
    fetch(apiUrl, head)
      .then((res) => res.json())
      .then((groups) => {
        console.log(groups)
        if(groups.status === 400 || groups.status === 401 || groups.status === 405 || groups.status === 500 || groups.status === 404){
          groups = []
        }
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
        <ListLoading isLoading={appState.loading} groups={appState.groups} token={token} />
        {checkMore3()}
        
    </div>
  );
}
export default Groups;
