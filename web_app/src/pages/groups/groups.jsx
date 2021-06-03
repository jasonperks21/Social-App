import Topbar from "../../components/topbar/Topbar";
import Sidebar from "../../components/sidebar/Sidebar";
import Rightbar from "../../components/rightbar/Rightbar";
import GroupList from "../../components/groupsList/GroupList";
import "../home/home.css";

export default function Groups(ids) {
  return (
    <>
      <Topbar />
      <div className="homeContainer">
        <Sidebar userId = {ids.userId}/>
        <GroupList userId={ids.userId}/>
        <Rightbar />
      </div>
    </>
  );
}
