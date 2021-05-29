import "./rightbar.css";
//import { Message } from "@material-ui/icons";

export default function Rightbar() {
  return (
    <div className="rightbar">
      <div className="rightbarWrapper">
        <h3 className="messageHeader">Messages</h3>
        <ul className="messageList">
          <li className="messageListItem">
            <div className="messageWrapper">
              <div className="messageTop">
                <img
                  src="/assets/person/4.jpeg"
                  alt=""
                  className="messageProfileImg"
                />
                <span className="messageUsername">Captain Jack Sparrow</span>
                <span className="messageDate">1 min ago</span>
              </div>
              <div className="messageBottom">
                {/* <Message className="messageIcon" /> */}
                <span className="messageText">You got any rum?</span>
              </div>
            </div>
          </li>
          <li className="messageListItem">
            <div className="messageWrapper">
              <div className="messageTop">
                <img
                  src="/assets/person/6.jpeg"
                  alt=""
                  className="messageProfileImg"
                />
                <span className="messageUsername">Donald Trump</span>
                <span className="messageDate">3 min ago</span>
              </div>
              <div className="messageBottom">
                {/* <Message className="messageIcon" /> */}
                <span className="messageText">Wall almost complete!</span>
              </div>
            </div>
          </li>
          <li className="messageListItem">
            <div className="messageWrapper">
              <div className="messageTop">
                <img
                  src="/assets/person/7.jpeg"
                  alt=""
                  className="messageProfileImg"
                />
                <span className="messageUsername">Oprah Winfrey</span>
                <span className="messageDate">15 min ago</span>
              </div>
              <div className="messageBottom">
                {/* <Message className="messageIcon" /> */}
                <span className="messageText">Hellooooooooooo!</span>
              </div>
            </div>
          </li>
        </ul>
        <hr className="rightbarHr" />
        <h3 className="postMap">Post Map</h3>
        <div className="mapWrapper">
          <div className="map">
            <img src="/assets/map.png" alt="" className="mapImg" />
          </div>
        </div>
      </div>
    </div>
  );
}
