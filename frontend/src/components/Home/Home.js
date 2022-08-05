import React, { useState, useRef } from "react";
import Video from "../Video/Video";
import VideoInfo from "../Video/VideoInfo";
import DropDown from "../DropDown/DropDown";
import Modal from "../Modal/Modal";
import axios from "axios";

const Home = ({ url }) => {
  const [player, setPlayer] = useState();
  const [popUp, setPopUp] = useState({ title: "", comments: [] });
  const [visible, setVisible] = useState(false);
  const input = useRef();
  const onClick = () => {
    (async function () {
      const keyword = input.current.value;
      const result = await axios.get(
        `http://localhost:8080/comments?url=${url}&keyword=${keyword}`
      );
      setVisible(true);
      setPopUp({ title: keyword, comments: result.data });
    })();
  };
  return (
    <>
      <section
        className="resume-section"
        style={{ display: "block", alignItems: "stretch", padding: "2rem" }}
        id="home"
      >
        <div className="resume-section-content" style={{ height: "100vh" ,width:"100%" }}>
          <div className="row">
            <div className="col-9">
              <div className="input-group" style={{ height: "60px" }}>
                <input
                  type="search"
                  className="form-control rounded"
                  placeholder="검색할 댓글을 입력하세요!"
                  aria-label="Search"
                  aria-describedby="search-addon"
                  ref={input}
                  style={{borderColor:"black",border:"3px solid rgb(178,34,34)"}}
                />
                <button
                  type="button"
                  className="btn btn-outline-primary"
                  onClick={onClick}
                  style={{width:"100px"}}
                >
                  <i class="bi bi-search"></i>
                </button>
              </div>
            </div>
            <div className="col-3">
              <DropDown url={url} setPopUp={setPopUp} setVisible={setVisible} />
            </div>
          </div>
          <div className="row h-50">
            <Video videoId={url} setPlayer={setPlayer} />
          </div>

          <div className="row">
            <VideoInfo player={player} url={url} />
          </div>
        </div>
      </section>
      <Modal setVisible={setVisible} visible={visible} popUp={popUp} />
    </>
  );
};

export default Home;
