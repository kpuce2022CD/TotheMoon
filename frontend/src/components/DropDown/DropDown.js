import React, { useState, useEffect } from "react";
import TextLoop from "react-text-loop";
import { AiOutlineCaretUp, AiOutlineCaretDown } from "react-icons/ai";
import "./DropDown.css";
import axios from "axios";

const DropDown = ({ url, setPopUp, setVisible }) => {
  const [data, setData] = useState([]);
  const [comments, setComments] = useState([]);
  useEffect(() => {
    (async function () {
      const result = await axios.get(`http://localhost:8080/getkeyword/${url}`);
      setData(result.data.b5);
      setComments(result.data.comments);
    })();
  }, [url]);
  const [open, setOpen] = useState(false);
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
          {data.length !== 0 ? (
            <TextLoop className="textLoop">
              {data.map((cur, index) => (
                <span key={index} className="loopContent">
                  {`${index + 1}. ${cur}`}
                </span>
              ))}
            </TextLoop>
          ) : (
            <div className="textLoop" />
          )}

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
        {open
          ? data.map((cur, index) => (
              <div
                style={{
                  height: "60px",
                  display: "flex",
                  alignItems: "center",
                }}
                key={index}
              >
                <p
                  style={{
                    margin: 0,
                    paddingLeft: "2.5rem",
                    fontSize: "1.1rem",
                  }}
                  onClick={() => {
                    setPopUp({ title: cur, comments: comments[index] });
                    console.log("hi");
                    setVisible(true);
                  }}
                  key={index}
                >
                  {`${index + 1}. ${cur}`}
                </p>
              </div>
            ))
          : null}
      </div>
    </div>
  );
};

export default DropDown;
