import "./topbar.css";
import { Search, Person, Chat, Notifications } from "@material-ui/icons";

export default function Topbar() {
  return (
    <div className="topbarContainer">
      <div className="topbarLeft">
        <a href='/' className="logo">Not Facebook</a>
      </div>
      <div className="topbarCenter">
        <div className="searchbar">
          <Search className="searchIcon" />
          <input
            placeholder="find groups, friends or posts..."
            className="searchInput"
          />
        </div>
      </div>
      <div className="topbarRight">
        <div className="topbarLinks">
          <a href="/" className="topbarLink">Home Page</a>
          <span className="topbarLink">Timeline</span>
          <span className="topbarLink">Post Map</span>
        </div>
        <div className="topbarIcons">
          <div className="topbarIconItem">
            <Person />
            <span className="topbarIconBadge">1</span>
          </div>
          <div className="topbarIconItem">
            <Chat />
            <span className="topbarIconBadge">2</span>
          </div>
          <div className="topbarIconItem">
            <Notifications />
            <span className="topbarIconBadge">5</span>
          </div>
        </div>
        <img src="/assets/person/1.jpeg" alt="" className="topbarImg" />
      </div>
    </div>
  );
}
