import "./topbar.css";
import { Search, Person, Chat, Notifications } from "@material-ui/icons";
import { Link } from "react-router-dom";
import { useState } from "react";

export default function Topbar() {
  const [appState, setAppState] = useState({
    text: '',
    goto: false
  });
  
  const handleSubmit= (e) => {
    e.preventDefault();
    setAppState({text: appState.text, goto:true})
  }
  const handleIn = (e) => {
    setAppState({text: e, goto:false});
  }
  if(appState.goto){
    let link = '/search/?q='+appState.text
    //setAppState({text:appState.text, goto: false})
    window.location.replace(link);
  }
  return (

    <div className="topbarContainer">
      <div className="topbarLeft">

        <Link to="/" style={{textDecoration:"none"}}>
          <span className="logo">Not Facebook</span>
        </Link>
      </div>
      <div className="topbarCenter">
        <div className="searchbar">
          <Search className="searchIcon" />
          <form onSubmit={ e => {handleSubmit(e)} }>
            <input
              placeholder="find groups, friends or posts..."
              className="searchInput"
              onChange={e => handleIn(e.target.value)}
            />
          </form>
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
