import React from "react";
import "./Video.css";

const VideoInfo = () => {
  const text = `조회수 : 20,242,037회\t\t\t\t\t댓글수 : 20,793개`;
  return (
    <div className="content" style={{ marginBottom: "30px" }}>
      <div className="infoContainer">
        <p className="title">YENA (최예나) - SMILEY (Feat. BIBI) MV</p>

        <p>Stone Music Entertainment</p>
        <hr />
        <pre className="text">{text}</pre>
      </div>
    </div>
  );
};

export default VideoInfo;
