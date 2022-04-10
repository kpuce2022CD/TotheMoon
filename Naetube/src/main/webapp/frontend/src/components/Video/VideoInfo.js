import React, { useEffect, useState } from "react";
import "./Video.css";
import Timeline from "../Timeline/Timeline";
import axios from "axios";

const VideoInfo = ({ player, url }) => {
  const [timeline, setTimeline] = useState([]);
  const [videoInfo, setVideoInfo] = useState({});
  useEffect(() => {
    (async function () {
      const result = await axios.get(`http://localhost:8080/timeline/${url}`);
      setTimeline(result.data);
    })();
    (async function () {
      const result = await axios.get(`http://localhost:8080/videoInfo/${url}`);
      setVideoInfo(result.data[0]);
    })();
  }, [url]);

  const clickfunc = (para) => {
    return () => {
      player.seekTo(para);
    };
  };
  const text = `조회수 : ${videoInfo.view},037회\t\t\t좋아요 수 : ${videoInfo.like},793개\t\t\t업로드날짜 : ${videoInfo.date}`;
  return (
    <div className="content" style={{ marginBottom: "30px" }}>
      <div className="infoContainer">
        <p className="title">{videoInfo.title}</p>

        {/* <p>Stone Music Entertainment</p> */}
        <hr />
        <pre className="text">{text}</pre>
        <div></div>
        <span
          className="timeline"
          style={{ backgroundColor: "rgb(189,93,56)" }}
        >
          인기 타임라인
        </span>
        {timeline.map((cur, index) => (
          <Timeline clickfunc={clickfunc(cur.sec)} key={index}>
            {cur.time}
          </Timeline>
        ))}
      </div>
    </div>
  );
};

export default VideoInfo;
