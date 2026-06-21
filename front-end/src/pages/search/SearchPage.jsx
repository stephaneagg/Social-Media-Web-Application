import { useEffect, useState, useCallback } from "react";
import { useSearchParams } from "react-router-dom";
import { search } from "../../services/searchService";

import UserCard from "../../components/profile/UserCard";
import PostList from "../../components/post/PostList";

import "./searchPage.scss";

export default function SearchPage() {

  const [searchParams] = useSearchParams();
  const query = searchParams.get("query");

  const [results, setResults] = useState({ users: [], posts: [] });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const loadResults = useCallback( async () => {
    if (!query) return;
    try {
      setLoading(true);
      setError(null);
      const data = await search(query);
      setResults(data);
    } catch (err) {
      console.log(err);
      setError("Failed to load search results. Please try again.");
    } finally {
      setLoading (false);
    }
  }, [query])

  useEffect(() => {
    loadResults();
  }, [loadResults])



  return(
    <div className="searchPage">

      <h2>Results for "{query}"</h2>

      {loading && <p>Loading...</p>}
      {error && <p className="error">{error}</p>}

      {!loading && !error && (
        <>
          <div className="userSection">
            <h3>Users</h3>
                {results.users.length === 0 ? (
                  <p>No matching users found.</p>
                ) : (
                  results.users.map((user) => <UserCard key={user.id} user={user} />)
                )}
          </div>

          <div className="postSection">
            <h3>Posts</h3>
            {results.posts.length === 0 ? (
              <p>No matching posts found.</p>
            ) : (
              <PostList posts={results.posts} loadPosts={loadResults}/>
            )}
          </div>
        </>
      )}
  </div>);
}