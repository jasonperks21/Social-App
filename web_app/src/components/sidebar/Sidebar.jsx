import "./sidebar.css";
//import { Group, PermIdentity, Person } from "@material-ui/icons";
import Groups from "./Groups";
import AddGroup from "./AddGroup";
import Friends from "./Friends";

export default function Sidebar(userId) {
  return (
    <div className="sidebar">
      <div className="sidebarWrapper">
        <h3 className="sidebarHeading">Active Groups</h3>
          <Groups userId={userId.userId} token = {userId.token}/>
          <AddGroup userId={userId.userId} token = {userId.token}/>
        <hr className="sidebarHr" />
        <h3 className="sidebarHeading">Friends</h3>
        <Friends userId={userId.userId} token = {userId.token}/>
      </div>
    </div>
  );
}
