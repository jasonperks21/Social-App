import Topbar from "../../components/topbar/Topbar";
import SearchResults from "../../components/searchResults/SearchResults"
import "../home/home.css";

export default function Search(ids) {
  return (
    <>
      <Topbar />
      <div className="homeContainer">
        <SearchResults userId = {ids.userId} token={ids.token}/>
      </div>
    </>
  );
}
