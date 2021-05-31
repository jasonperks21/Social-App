import "./sidebar.css";
import { Group, PermIdentity, Person } from "@material-ui/icons";

export default function Sidebar() {
  return (
    <div className="sidebar">
      <div className="sidebarWrapper">
        <h3 className="sidebarHeading">Active Groups</h3>
        <ul className="sidebarList">
          <li className="sidebarListItem">
            <div className="listWrapper">
              <div className="listTop">
                <Group className="sidebarListIcon" />
                <span className="sidebarItemText">Computer Systems 414</span>
              </div>
              <div className="listBottom">
                <div className="listBottomActive">
                  <div class="circle"></div>
                  <span className="listBottomText">50 Online</span>
                </div>
                <div className="listBottomMembers">
                  <PermIdentity className="listBottomIcon" />
                  <span className="listBottomText">Member</span>
                </div>
              </div>
            </div>
          </li>
          <li className="sidebarListItem">
            <div className="listWrapper">
              <div className="listTop">
                <Group className="sidebarListIcon" />
                <span className="sidebarItemText">Computer Sci E414</span>
              </div>
              <div className="listBottom">
                <div className="listBottomActive">
                  <div class="circle"></div>
                  <span className="listBottomText">10 Online</span>
                </div>
                <div className="listBottomMembers">
                  <Person className="listBottomIcon" />
                  <span className="listBottomText">Admin</span>
                </div>
              </div>
            </div>
          </li>
          <li className="sidebarListItem">
            <div className="listWrapper">
              <div className="listTop">
                <Group className="sidebarListIcon" />
                <span className="sidebarItemText">Systems and Signals 414</span>
              </div>
              <div className="listBottom">
                <div className="listBottomActive">
                  <div class="circle"></div>
                  <span className="listBottomText">50 Online</span>
                </div>
                <div className="listBottomMembers">
                  <PermIdentity className="listBottomIcon" />
                  <span className="listBottomText">Member</span>
                </div>
              </div>
            </div>
          </li>
          <li className="sidebarListItem">
            <div className="listWrapper">
              <div className="listTop">
                <Group className="sidebarListIcon" />
                <span className="sidebarItemText">CompSci 334</span>
              </div>
              <div className="listBottom">
                <div className="listBottomActive">
                  <div class="circle"></div>
                  <span className="listBottomText">5 Online</span>
                </div>
                <div className="listBottomMembers">
                  <Person className="listBottomIcon" />
                  <span className="listBottomText">Admin</span>
                </div>
              </div>
            </div>
          </li>
        </ul>
        <button className="sidebarButton">Show More</button>
        <hr className="sidebarHr" />
        <h3 className="sidebarHeading">Friends</h3>
        <ul className="sidebarFriendList">
          <li className="sidebarFriend">
            <img
              src="/assets/person/2.jpeg"
              alt=""
              className="sidebarFriendImg"
            />
            <span className="sidebarFriendName">Spiderman</span>
          </li>
          <li className="sidebarFriend">
            <img
              src="/assets/person/2.jpeg"
              alt=""
              className="sidebarFriendImg"
            />
            <span className="sidebarFriendName">Spiderman</span>
          </li>
          <li className="sidebarFriend">
            <img
              src="/assets/person/2.jpeg"
              alt=""
              className="sidebarFriendImg"
            />
            <span className="sidebarFriendName">Spiderman</span>
          </li>
          <li className="sidebarFriend">
            <img
              src="/assets/person/2.jpeg"
              alt=""
              className="sidebarFriendImg"
            />
            <span className="sidebarFriendName">Spiderman</span>
          </li>
          <li className="sidebarFriend">
            <img
              src="/assets/person/2.jpeg"
              alt=""
              className="sidebarFriendImg"
            />
            <span className="sidebarFriendName">Spiderman</span>
          </li>
          <li className="sidebarFriend">
            <img
              src="/assets/person/2.jpeg"
              alt=""
              className="sidebarFriendImg"
            />
            <span className="sidebarFriendName">Spiderman</span>
          </li>
          <li className="sidebarFriend">
            <img
              src="/assets/person/2.jpeg"
              alt=""
              className="sidebarFriendImg"
            />
            <span className="sidebarFriendName">Spiderman</span>
          </li>
          <li className="sidebarFriend">
            <img
              src="/assets/person/2.jpeg"
              alt=""
              className="sidebarFriendImg"
            />
            <span className="sidebarFriendName">Spiderman</span>
          </li>
          <li className="sidebarFriend">
            <img
              src="/assets/person/2.jpeg"
              alt=""
              className="sidebarFriendImg"
            />
            <span className="sidebarFriendName">Spiderman</span>
          </li>
          <li className="sidebarFriend">
            <img
              src="/assets/person/2.jpeg"
              alt=""
              className="sidebarFriendImg"
            />
            <span className="sidebarFriendName">Spiderman</span>
          </li>
          <li className="sidebarFriend">
            <img
              src="/assets/person/2.jpeg"
              alt=""
              className="sidebarFriendImg"
            />
            <span className="sidebarFriendName">Spiderman</span>
          </li>
          <li className="sidebarFriend">
            <img
              src="/assets/person/2.jpeg"
              alt=""
              className="sidebarFriendImg"
            />
            <span className="sidebarFriendName">Spiderman</span>
          </li>
          <li className="sidebarFriend">
            <img
              src="/assets/person/2.jpeg"
              alt=""
              className="sidebarFriendImg"
            />
            <span className="sidebarFriendName">Spiderman</span>
          </li>
          <li className="sidebarFriend">
            <img
              src="/assets/person/2.jpeg"
              alt=""
              className="sidebarFriendImg"
            />
            <span className="sidebarFriendName">Spiderman</span>
          </li>
          <li className="sidebarFriend">
            <img
              src="/assets/person/2.jpeg"
              alt=""
              className="sidebarFriendImg"
            />
            <span className="sidebarFriendName">Spiderman</span>
          </li>
          <li className="sidebarFriend">
            <img
              src="/assets/person/2.jpeg"
              alt=""
              className="sidebarFriendImg"
            />
            <span className="sidebarFriendName">Spiderman</span>
          </li>
        </ul>
      </div>
    </div>
  );
}
