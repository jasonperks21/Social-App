import Topbar from "../../components/topbar/Topbar";
import Sidebar from "../../components/sidebar/Sidebar";
import Rightbar from "../../components/rightbar/Rightbar";
import "./home.css";
import GetPosts from "../../components/feed/Feed";

export default function Home(userId) {
  return (
    <>
      <Topbar />
      <div className="homeContainer">
        <Sidebar userId = {userId.userId} token = {userId.token}/>
        <GetPosts userId = {userId.userId} token = {userId.token}/>
        <Rightbar />
      </div>
    </>
  );
}
