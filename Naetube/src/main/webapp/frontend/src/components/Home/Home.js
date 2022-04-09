import React, { useState } from "react";
import Video from "../Video/Video";
import VideoInfo from "../Video/VideoInfo";
import DropDown from "../DropDown/DropDown";

const Home = ({ url }) => {
  const [player, setPlayer] = useState();
  return (
    <section
      className="resume-section"
      style={{ display: "block", alignItems: "stretch", padding: "2rem" }}
      id="home"
    >
      <div className="resume-section-content" style={{ height: "100vh" }}>
        <div className="row" style={{ padding: "1rem" }}>
          <div
            className="col-9"
            style={{
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
          >
            <div className="input-group" style={{ height: "60px" }}>
              <input
                type="search"
                className="form-control rounded"
                placeholder="검색할 댓글을 입력하세요!"
                aria-label="Search"
                aria-describedby="search-addon"
              />
              <button type="button" className="btn btn-outline-primary">
                검색
              </button>
            </div>
          </div>
          <div
            className="col-3"
            style={{
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
          >
            <DropDown />
          </div>
        </div>
        <div className="row h-50">
          <Video videoId={url} setPlayer={setPlayer} />
        </div>

        <div className="row">
          <VideoInfo player={player} url={url} />
        </div>
      </div>
    </section>
  );
};

export default Home;
