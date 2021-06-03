import React from 'react';
import { Group, PermIdentity} from "@material-ui/icons"; //Person

const GroupList = (props) => {
  const { groups } = props;
  if (!groups || groups.length === 0) return <p>No groups, sorry</p>;

  function role(bool){
    //console.log(bool)
    if(bool){
      return <span className="listBottomText">Admin</span>;
    }
    else{
      return <span className="listBottomText">Member</span>;
    }
  }

  return (
    <ul className="sidebarList">
      {groups.map((group) => {
         return (
          <li className="sidebarListItem">
          <div className="listWrapper">
            <div className="listTop">
              <Group className="sidebarListIcon" />
              <a href={'/groups?gid='+group.groupId} className="sidebarItemText">{group.groupName}</a>
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