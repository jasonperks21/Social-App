import Topbar from "../../components/topbar/Topbar";
import Sidebar from "../../components/sidebar/Sidebar";
import GroupMembers from "../../components/groupMembers/GroupMembers";
import GroupList from "../../components/groupsList/GroupList";
import "../home/home.css";

export default function Groups(ids) {
  return (
    <>
      <Topbar />
      <div className="homeContainer">
        <Sidebar userId = {ids.userId} token = {ids.token}/>
        <GroupList userId={ids.userId} token = {ids.token} coords={ids.coords}/>
        <GroupMembers userId={ids.userId} token = {ids.token}/>
      </div>
    </>
  );
}
