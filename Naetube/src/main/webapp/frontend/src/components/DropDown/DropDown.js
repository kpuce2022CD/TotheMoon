import React, { useState } from "react";
import TextLoop from "react-text-loop";
import { AiOutlineCaretUp, AiOutlineCaretDown } from "react-icons/ai";
import "./DropDown.css";

const DropDown = () => {
  const [open, setOpen] = useState(true);
  const onClick = () => {
    setOpen(!open);
  };
  return (
    <div
      style={{
        width: "16rem",
        height: "100%",
        display: "flex",
        marginLeft: "1rem",
      }}
    >
      <div
        style={{
          width: "16rem",
          display: "flex",
          flexDirection: "column",
          position: "absolute",
          border: "solid",
        }}
      >
        <div
          style={{
            height: "60px",
            width: "100%",
            display: "flex",
            alignItems: "center",
            flexDirection: "row",
          }}
        >
          <TextLoop className="textLoop">
            <span className="loopContent">1. 네카라쿠베</span>
            <span className="loopContent">2. helloworld</span>
          </TextLoop>
          <div
            style={{
              width: "20%",
              height: "60px",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
            onClick={onClick}
          >
            {open ? (
              <AiOutlineCaretUp size="40" />
            ) : (
              <AiOutlineCaretDown size="40" />
            )}
          </div>
        </div>
        {open ? (
          <>
            <div
              style={{
                height: "60px",

                display: "flex",
                alignItems: "center",
              }}
            >
              <p
                style={{ margin: 0, paddingLeft: "2.5rem", fontSize: "1.1rem" }}
              >
                2. 안녕하세요
              </p>
            </div>
            <div
              style={{
                height: "60px",

                display: "flex",
                alignItems: "center",
              }}
            >
              <p
                style={{ margin: 0, paddingLeft: "2.5rem", fontSize: "1.1rem" }}
              >
                3. 안녕하세요
              </p>
            </div>
            <div
              style={{
                height: "60px",

                display: "flex",
                alignItems: "center",
              }}
            >
              <p
                style={{ margin: 0, paddingLeft: "2.5rem", fontSize: "1.1rem" }}
              >
                4. 안녕하세요
              </p>
            </div>
            <div
              style={{
                height: "60px",

                display: "flex",
                alignItems: "center",
              }}
            >
              <p
                style={{ margin: 0, paddingLeft: "2.5rem", fontSize: "1.1rem" }}
              >
                5. 안녕하세요
              </p>
            </div>
            <div
              style={{
                height: "60px",
                display: "flex",
                alignItems: "center",
              }}
            >
              <p
                style={{ margin: 0, paddingLeft: "2.5rem", fontSize: "1.1rem" }}
              >
                1. 안녕하세요
              </p>
            </div>
          </>
        ) : null}
      </div>
    </div>
  );
};

export default DropDown;
