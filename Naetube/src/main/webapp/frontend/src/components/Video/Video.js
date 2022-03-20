import React from "react";
import Youtube from "react-youtube";
import "./Video.css";

const Video = ({ videoId }) => {
  return (
    <Youtube
      videoId={videoId}
      containerClassName="videoContainer content"
      className="video"
    />
  );
};

export default Video;
