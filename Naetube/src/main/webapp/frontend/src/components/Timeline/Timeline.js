import React from "react";
import "./Timeline.css";

const Timeline = ({ children, href }) => {
  return (
    <div
      className="col"
      style={{
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
      }}
    >
      {/* <a href={href}>{children}</a> */}
      <span className="timeline">00:00:00</span>
    </div>
  );
};

export default Timeline;
