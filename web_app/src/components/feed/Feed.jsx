import "./feed.css";
import Share from "../share/Share";
import Post from "../post/Post";
import { useEffect, useState } from "react";
import axios from "axios";

export default function Feed() {
  const [posts,setPosts] = useState([]);
  
useEffect(()=>{
  const fetchPosts = async() => {
    const res = await axios.get("app/posts/1");
    console.log(res.data);
    setPosts(res.data);
  }
  fetchPosts();
})

  return (
    <div className="feed">
      <div className="feedWrapper">
        <Share />
        {posts.data.map((p) => (
          <Post key={p.postId} post={p} />
        ))}
      </div>
    </div>
  );
}
