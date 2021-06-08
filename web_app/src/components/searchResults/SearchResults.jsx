import "./feed.css";
import { useEffect, useState } from "react";
import WithLoading from "../sidebar/WithGroupLoading"
import React from "react";

export default function SearchResults(ids) {
  let q = new URLSearchParams(window.location.search).get("q");
  // console.log(gId);
  const id = ids.userId;
  const ListLoading = WithLoading(DisplayResults);
  const [appState, setAppState] = useState({
    loading: false,
    results: null,
    myFriends: null
  });

  useEffect(() => {
    setAppState({ loading: true });
    const apiUrl = '/app/users/?q='+q;
    const apiUrl2 = '/app/friends/?userId='+id;
    Promise.all([fetch(apiUrl).then(res => res.json()),
    fetch(apiUrl2).then(res => res.json())])
    .then(([urlOneData, urlTwoData]) => {
      let friends =[];
        if(urlTwoData !== null){
          if (urlTwoData.status!==404||urlTwoData.status!==400||urlTwoData.status!==405||urlTwoData.status!==500){
            urlTwoData.forEach(f => {
              friends.push(f.friendId)
            });}
        } 
        setAppState({loading:false, results:urlOneData, myFriends:friends});
      });
      // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [setAppState]);

  if(!q){
    return(<div className="feed">
      <div className="feedWrapper">
      <h2>Search for anything...</h2>
      </div>
    </div>);
  }
  return (
    <div className="feed">
      <div className="feedWrapper">
        <ListLoading isLoading={appState.loading} results={appState.results} userId={id} myFriends={appState.myFriends}/>
      </div>
    </div>
  );
}

const DisplayResults = (props) =>{
  const { results, userId, myFriends} = props;
  if(results === null) return <p>No results found!</p>
  if (!results || results.length === 0) return <p>No results found!</p>;
  if(results.status === 400||results.status === 404||results.status === 405||results.status === 500) return <p>Something went wrong!</p>
  return(
    <div className="feed">
      <div className="feedWrapper">
        <h2>Users:</h2>
        {results.map((r) => {
            return <Person result={r} userId={userId} friendList={myFriends}/>;
          })}
        <hr />
        <h2>Groups:</h2>
      </div>
    </div>
  );
}



class Person extends React.Component{
  constructor(props){
    super(props);
    this.state = {
        person: this.props.result,
        userId: this.props.userId,
        friendList: this.props.friendList,
        buttonPressed: false,
        friendAdded: false,
        removeClicked: false,
        removedFriend: false
    }
    this.clicked = this.clicked.bind(this);
    this.removeClicked = this.removeClicked.bind(this);
  }

  clicked(){
    this.setState({buttonPressed:true});
  }
  removeClicked(){
    this.setState({removeClicked : true});
  }

  checkFriend(){
    if(String(this.state.person.userId) === this.state.userId){
      return <p>You </p>;
    }
    if(this.state.buttonPressed){
      if(!this.state.friendAdded){
        this.addFriend();
        return <span >Processing</span>;
      }
    }
    if(this.state.removeClicked){
      if(!this.state.removedFriend){
        this.deleteFriend();
        return <span >Processing</span>;
      }
    }
    if(this.state.friendAdded){
      return <span id="FriendAdded">Friend Added</span>;
    }
    if(this.state.removedFriend){
      return <span id="removeText">Friend Removed</span>;
    }
    if(this.state.friendList?.indexOf(this.state.person.userId) > -1){
      return <button id="removeButton" onClick={this.removeClicked}>Remove Friend</button>;
    }
    else{
      return <button onClick={this.clicked}>+ Add Friend</button>;
    }
  }

  async addFriend(){
    // Simple POST request with a JSON body using fetch
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({userId: this.state.userId, friendId: String(this.state.person.userId)})
    };
    await fetch('/app/friends', requestOptions)
        .then(response => {
            console.log(requestOptions.body)
            console.log(response);
            if(response.status === 201){
              this.setState({friendAdded: true})
            }
            else{
              this.setState({buttonPressed: false})
            }
            
        });
  }

  async deleteFriend(){
    // Simple POST request with a JSON body using fetch
    const requestOptions = {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({userId: this.state.userId, friendId: String(this.state.person.userId)})
    };
    await fetch('/app/friends', requestOptions)
        .then(response => {
            console.log(requestOptions.body)
            console.log(response);
            if(response.status === 200){
              this.setState({removedFriend: true})
            }
            else{
              this.setState({removeClicked: false})
            }
            
        });
  }



    render(){
      return (
        <div className="post">
          <div className="postWrapper">
            <div className="postTop">
              <div className="postTopLeft">
                <span className="postUsername">@{this.state.person?.userName}</span>
              </div>
              {this.checkFriend()}
            </div>
            <div className="postCenter">
              <span className="postText">{this.state.person?.displayName}</span>
            </div>
            <div className="postBottom">       
            </div>   
          </div>
        </div>
      );
    }
}

