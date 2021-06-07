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
    results: null
  });

  useEffect(() => {
    setAppState({ loading: true });
    const apiUrl = '/app/users/?q='+q;
    fetch(apiUrl)
      .then((res) => res.json())
      .then((results) => {
        //console.log(results)
        setAppState({ loading: false, results: results});
       
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
        <ListLoading isLoading={appState.loading} results={appState.results} userId={id}/>
      </div>
    </div>
  );
}

const DisplayResults = (props) =>{
  const { results, userId } = props;
  //console.log(results);
  if(results === null) return <p>No results found!</p>
  if (!results || results.length === 0) return <p>No results found!</p>;
  if(results.status === 400||results.status === 404||results.status === 405||results.status === 500) return <p>Something went wrong!</p>
  return(
    <div className="feed">
      <div className="feedWrapper">
        <h2>Users:</h2>
        {results.map((r) => {
            return <Person result={r} userId={userId}/>;
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
        buttonPressed: false,
        friendAdded: false
    }
    this.clicked = this.clicked.bind(this);
  }

  clicked(){
    this.setState({buttonPressed:true});
  }

  checkFriend(){
    if(String(this.state.person.userId) === this.state.userId){
      return <p>You </p>;
    }
    if(this.state.buttonPressed){
      if(!this.state.friendAdded){
        this.addFriend()
        return <span >Processing</span>
      }
    }
    if(this.state.friendAdded){
      return <span id="FriendAdded">Friend Added</span>
    }
    else{
      return <button onClick={this.clicked}>+ Add Friend</button>
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

