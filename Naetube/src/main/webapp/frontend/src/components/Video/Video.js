import React from "react";
import Youtube from "react-youtube";
import "./Video.css";

const Video = ({ videoId, setPlayer }) => {

  const onReady = (e)=>{
    setPlayer(e.target)
  }

  return (
    <Youtube
      videoId={videoId}
      containerClassName="videoContainer content"
      className="video"
      onReady={onReady}
    />
  );
};

export default Video;
