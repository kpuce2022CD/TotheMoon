import React from "react";
import "./Video.css";
import Timeline from "../Timeline/Timeline";

const VideoInfo = ({player}) => {

  const clickfunc = (para)=>{
    return ()=>{
      player.seekTo(para)
    }
  }
  const text = `조회수 : 20,242,037회\t\t\t댓글수 : 20,793개\t\t\t업로드날짜 : 2022. 1. 17.`;
  return (
    <div className="content" style={{ marginBottom: "30px" }}>
      <div className="infoContainer">
        <p className="title">YENA (최예나) - SMILEY (Feat. BIBI) MV</p>

        <p>Stone Music Entertainment</p>
        <hr />
        <pre className="text">{text}</pre>
        <div></div>
        <span
          className="timeline"
          style={{ backgroundColor: "rgb(189,93,56)" }}
        >
          인기 타임라인
        </span>
        <Timeline clickfunc={clickfunc(60)}>00:00:00</Timeline>
        <Timeline clickfunc={clickfunc(120)}>00:00:00</Timeline>
        <Timeline clickfunc={clickfunc(180)}>00:00:00</Timeline>
        <Timeline clickfunc={clickfunc(210)}>00:00:00</Timeline>
        <Timeline clickfunc={clickfunc(270)}>00:00:00</Timeline>
      </div>
    </div>
  );
};

export default VideoInfo;
