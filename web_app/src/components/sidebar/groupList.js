import React from 'react';
import { Group, PermIdentity} from "@material-ui/icons"; //Person

const GroupList = (props) => {
  const { groups } = props;
  //console.log(props)
  if (!groups || groups.length === 0) return <p>No groups, sorry</p>;

  function role(bool){
    console.log(bool)
    if(bool){
      return <span className="listBottomText">Admin</span>;
    }
    else{
      return <span className="listBottomText">Member</span>;
    }
  }

  return (
    // <ul>
    //   <h2 className='list-head'>Available Public Repositories</h2>
    //   {groups.map((group) => {
    //     return (
    //       <li key={group.groupId} className='list'>
    //         <span className='repo-text'>{group.groupName}</span>
    //       </li>
    //     );
    //   })}
    // </ul>
    <ul className="sidebarList">
      {groups.map((group) => {
         return (
          <li className="sidebarListItem">
          <div className="listWrapper">
            <div className="listTop">
              <Group className="sidebarListIcon" />
              <span className="sidebarItemText">{group.groupName}</span>
            </div>
            <div className="listBottom">
              {/* <div className="listBottomActive">
                <div class="circle"></div>
                <span className="listBottomText">50 Online</span>
              </div> */}
              <div className="listBottomMembers">
                <PermIdentity className="listBottomIcon" />
                {role(group.adminOnThisGroup)}
              </div>
            </div>
          </div>
        </li>
          );
         })}
    </ul>
  );
};
export default GroupList;