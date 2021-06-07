import "./feed.css";
import { useEffect, useState } from "react";
import WithLoading from "../sidebar/WithGroupLoading"

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
          
          return (
            <div className="post">
              <div className="postWrapper">
                <div className="postTop">
                  <div className="postTopLeft">
                    <span className="postUsername">@{r?.userName}</span>
                  </div>
                </div>
                <div className="postCenter">
                  <span className="postText">{r?.displayName}</span>
                </div>
                <div className="postBottom">       
                </div>   
              </div>
            </div>
          );
          })}
        <hr />
        <h2>Groups:</h2>
      </div>
    </div>
  );
}
