import React from "react";
import "./Timeline.css";
import ReactTooltip from "react-tooltip";

const Timeline = ({ clickfunc, children, count }) => {
  return (
    <>
      <span className="timeline" onClick={clickfunc} data-tip={count}>
        {children}
      </span>
      <ReactTooltip />
    </>
  );
};

export default Timeline;
