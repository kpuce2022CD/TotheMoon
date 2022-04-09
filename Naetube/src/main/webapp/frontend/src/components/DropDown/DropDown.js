import React, { useState } from "react";
import TextLoop from "react-text-loop";
import { AiOutlineCaretUp, AiOutlineCaretDown } from "react-icons/ai";

const DropDown = () => {
  const [open, setOpen] = useState(false);
  return (
    <div style={{ width: "100%", height: "100%" }}>
      <div
        style={{
          display: "flex",
          flex: 1,
          height: "100%",
          flexDirection: "row",
        }}
      >
        <div
          style={{
            width: "300px",
            height: "100%",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            visibility: "hidden",
          }}
        >
          <TextLoop>
            <span>1. 스물하나 스물다섯</span>
            <span>2. 백이진</span>
            <span>3. 나희도</span>
            <span>4. 이별</span>
            <span>5. 남주혁</span>
          </TextLoop>
        </div>
        <div
          style={{
            width: "300px",
            height: "360px",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            position: "absolute",

            flexDirection: "column",
          }}
        >
          <div
            style={{
              width: "100%",
              height: "60px",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
          >
            <p style={{ margin: 0 }}>hello</p>
          </div>
          <div
            style={{
              width: "100%",
              height: "60px",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
          >
            <p style={{ margin: 0 }}>hello</p>
          </div>
          <div
            style={{
              width: "100%",
              height: "60px",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
          >
            <p style={{ margin: 0 }}>hello</p>
          </div>
          <div
            style={{
              width: "100%",
              height: "60px",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
          >
            <p style={{ margin: 0 }}>hello</p>
          </div>
          <div
            style={{
              width: "100%",
              height: "60px",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
          >
            <p style={{ margin: 0 }}>hello</p>
          </div>
          <div
            style={{
              width: "100%",
              height: "60px",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
          >
            <p style={{ margin: 0 }}>hello</p>
          </div>
        </div>
        <div
          style={{
            width: "20%",
            height: "100%",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            backgroundColor: "#bd5d38",
          }}
        >
          {open ? (
            <AiOutlineCaretUp size="40" color="#FFFFFF" />
          ) : (
            <AiOutlineCaretDown size="40" color="#FFFFFF" />
          )}
        </div>
      </div>
    </div>
  );
};

export default DropDown;
