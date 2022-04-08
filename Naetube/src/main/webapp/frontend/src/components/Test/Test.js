import React, { useState } from "react";
import TextLoop from "react-text-loop";

const Test = () => {
  const [open, setOpen] = useState(false);
  return (
    <div style={{ width: "100%" }}>
      <div style={{ display: "flex", flex: 1, flexDirection: "row" }}>
        {open ? (
          <div style={{ width: "100%", height: "60px" }}>
            <TextLoop>
              <span>one</span>
              <span>two</span>
              <span>four</span>
              <span>five</span>
              <span>six</span>
            </TextLoop>
          </div>
        ) : (
          <div style={{ width: "100%", height: "60px" }}></div>
        )}
        <div style={{ width: "5%", height: "60px", backgroundColor: "green" }}>
          <button onClick={() => setOpen(!open)}>hello</button>
        </div>
      </div>
    </div>
  );
};

export default Test;
