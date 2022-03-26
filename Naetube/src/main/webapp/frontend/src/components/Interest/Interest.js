import React from "react";
import "./Interest.css";
import Chart from "../Chart/Chart";

const Interest = () => {
  return (
    <section
      className="resume-section"
      id="interest"
      style={{ display: "block", alignItems: "stretch", padding: "2rem" }}
    >
      <div
        className="resume-section-content interest"
        style={{ height: "100vh" }}
      >
        <Chart />
      </div>
    </section>
  );
};

export default Interest;
