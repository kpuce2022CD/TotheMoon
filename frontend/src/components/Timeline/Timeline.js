import React from "react";
import "./Timeline.css";

const Timeline = ({ clickfunc, children }) => {
  return <span className="timeline" onClick={clickfunc}>{children}</span>;
};

export default Timeline;
