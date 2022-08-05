import React from "react";
import NavItem from "../Navbar/NavItem";
import Home from "../Home/Home";
import NpCharts from "../NpChart/NpChart";
import NpComments from "../NpChart/NpComments";
import EmChart from "../EmChart/EmChart";
import EmComments from "../EmChart/EmComments";
import Interest from "../Interest/Interest";
import { useParams } from "react-router-dom";
import axios from "axios";
import { useState, useEffect } from "react";
import {bootstrap} from "react-bootstrap";
import Spinner from "../Spinner/Spinner";
import { ScrollSpy } from "bootstrap";

const Analyze = () => {
  const params = useParams();
  const url = params.url;
  let [clickedMenuTab, setClickedMenuTab] = useState(0);

  const [loading, setLoading] = useState(true);

  let [positiveComments, setPositiveComments] = useState([]);
  let [negativeComments, setNegativeComments] = useState([]);
  let [happyComments, setHappyComments] = useState([]);
  let [sadnessComments, setSadnessComments] = useState([]);
  let [fearComments, setFearComments] = useState([]);
  let [disgustComments, setDisgustComments] = useState([]);
  let [surprisedComments, setSurprisedComments] = useState([]);
  let [neutralComments, setNeutralComments] = useState([]);
  let [angerComments, setAngerComments] = useState([]);

  let [positivePercent, setPositivePercent] = useState(0);
  let [negativePercent, setNegativePercent] = useState(0);

  let [happyPercent, setHappyPercent] = useState(0);
  let [surprisedPercent, setSurprisedPercent] = useState(0);
  let [angerPercent, setAngerPercent] = useState(0);
  let [neutralPercent, setNeutralPercent] = useState(0);
  let [disgustPercent, setDisgustPercent] = useState(0);
  let [sadnessPercent, setSadnessPercent] = useState(0);
  let [fearPercent, setFearPercent] = useState(0);

  let [data, setData] = useState([]);

  useEffect(() => {
    console.log(clickedMenuTab);

  },[clickedMenuTab])

  useEffect(() => {


    const fetchData = async () => {
      const result = await axios.get(
        `http://localhost:8080/comments/${url}`
      );
      setData(result.data);

      // 데이터 초기화
      setPositiveComments([]);
      setNegativeComments([]);
      setSurprisedComments([]);
      setFearComments([]);
      setSadnessComments([]);
      setNeutralComments([]);
      setHappyComments([]);
      setDisgustComments([]);
      setAngerComments([]);

      result.data.comments.map((a, i) => {
        if (a.index === "1") {
          setPositiveComments((comment) => [...comment, a]);
        } else if (a.index === "0") {
          setNegativeComments((comment) => [...comment, a]);
        } else if (a.index === "2") {
          setFearComments((comment) => [...comment, a]);
        } else if (a.index === "3") {
          setSurprisedComments((comment) => [...comment, a]);
        } else if (a.index === "4") {
          setAngerComments((comment) => [...comment, a]);
        } else if (a.index === "5") {
          setSadnessComments((comment) => [...comment, a]);
        } else if (a.index === "6") {
          setNeutralComments((comment) => [...comment, a]);
        } else if (a.index === "7") {
          setHappyComments((comment) => [...comment, a]);
        } else if (a.index === "8") {
          setDisgustComments((comment) => [...comment, a]);
        } 
      });

      setPositivePercent(result.data.percent.positive);
      setNegativePercent(result.data.percent.negative);
      setHappyPercent(result.data.percent.happy);
      setSurprisedPercent(result.data.percent.surprise);
      setAngerPercent(result.data.percent.anger);
      setSadnessPercent(result.data.percent.sadness);
      setNeutralPercent(result.data.percent.neutral);
      setDisgustPercent(result.data.percent.disgust);
      setFearPercent(result.data.percent.fear);

      setLoading(false);
    };

    fetchData();
  }, []);

  return (
    <div>
      {loading ? (
        <Spinner />
      ) : (
        <div id="page-top">
          <nav
            className="navbar navbar-expand-lg navbar-dark bg-primary fixed-top"
            id="sideNav"
          >
            <a className="navbar-brand js-scroll-trigger" href="#page-top">
              <span className="d-block d-lg-none">ToTheMoon</span>
              <span className="d-none d-lg-block">
                {/* <img
                  className="img-fluid img-profile rounded-circle mx-auto mb-2"
                  src="assets/logo.png"
                  alt="..."
                /> */}
              </span>
            </a>
            <button
              className="navbar-toggler"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#navbarResponsive"
              aria-controls="navbarResponsive"
              aria-expanded="false"
              aria-label="Toggle navigation"
            >
              <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarResponsive">
              <ul className="navbar-nav">
                {
                  clickedMenuTab===1 ? 
                  <li style={{backgroundColor:"rgb(250,128,114)",margin:"10px"}} onClick={() => {setClickedMenuTab(1)}} class="nav-item"><a style={{color:"white"}} class="nav-link js-scroll-trigger" href="#home">Home</a></li>
                  :
                  <li style={{margin:"10px"}} class="nav-item" onClick={() => {setClickedMenuTab(1)}}><a style={{color:"white"}} class="nav-link js-scroll-trigger" href="#home">Home</a></li>
                }
                {
                  clickedMenuTab===2 ? 
                  <li style={{backgroundColor:"rgb(250,128,114)",margin:"10px"}} class="nav-item" onClick={() => {setClickedMenuTab(2)}}><a style={{color:"white"}} class="nav-link js-scroll-trigger" href="#np">댓글 긍정 부정 분석</a></li>
                  :
                  <li style={{margin:"10px"}} class="nav-item" onClick={() => {setClickedMenuTab(2)}}><a style={{color:"white"}} class="nav-link js-scroll-trigger" href="#np">댓글 긍정 부정 분석</a></li>
                }
                {
                  clickedMenuTab===3 ? 
                  <li style={{backgroundColor:"rgb(250,128,114)",margin:"10px"}} class="nav-item" onClick={() => {setClickedMenuTab(3)}}><a style={{color:"white"}} class="nav-link js-scroll-trigger" href="#emotion">댓글 감정 분석</a></li>
                  :
                  <li style={{margin:"10px"}} class="nav-item" onClick={() => {setClickedMenuTab(3)}}><a style={{color:"white"}} class="nav-link js-scroll-trigger" href="#emotion">댓글 감정 분석</a></li>
                }
                {
                  clickedMenuTab===4 ? 
                  <li style={{backgroundColor:"rgb(250,128,114)",margin:"10px"}} class="nav-item" onClick={() => {setClickedMenuTab(4)}}><a style={{color:"white"}} class="nav-link js-scroll-trigger" href="#interest">댓글 관심도</a></li>
                  :
                  <li style={{margin:"10px"}} class="nav-item" onClick={() => {setClickedMenuTab(4)}}><a style={{color:"white"}} class="nav-link js-scroll-trigger" href="#interest">댓글 관심도</a></li>
                }
                 {
                  clickedMenuTab===5 ? 
                  <li style={{backgroundColor:"rgb(250,128,114)",margin:"10px"}} class="nav-item" onClick={() => {setClickedMenuTab(5)}}><a style={{color:"white"}} class="nav-link js-scroll-trigger" href="http://localhost:8080/persist" onclick="alert('저장 완료')" target="_blank">분석 결과 저장</a></li>
                  :
                  <li style={{margin:"10px"}} class="nav-item" onClick={() => {setClickedMenuTab(4)}}><a style={{color:"white"}} class="nav-link js-scroll-trigger" href="http://localhost:8080/persist" onclick="alert('저장 완료')" target="_blank">분석 결과 저장</a></li>
                }
                {
                  clickedMenuTab===5 ? 
                  <li style={{backgroundColor:"rgb(250,128,114)",margin:"10px"}} class="nav-item" onClick={() => {setClickedMenuTab(5)}}><a style={{color:"white"}} class="nav-link js-scroll-trigger" href="http://localhost:8080/my-page" target="_blank">MY PAGE</a></li>
                  :
                  <li style={{margin:"10px"}} class="nav-item" onClick={() => {setClickedMenuTab(4)}}><a style={{color:"white"}} class="nav-link js-scroll-trigger" href="http://localhost:8080/my-page" target="_blank">MY PAGE</a></li>
                }
              </ul>
            </div>
          </nav>

          <div className="container-fluid p-0">
            <Home url={url} />
            <hr className="m-0" />

            <section className="resume-section" id="np" style={{ justifyContent: "center" }}>
              <NpCharts
                positivePercent={positivePercent}
                negativePercent={negativePercent}
              ></NpCharts>
              <NpComments
                positiveComments={positiveComments}
                setPositiveComments={setPositiveComments}
                negativeComments={negativeComments}
                setNegativeComments={setNegativeComments}
              ></NpComments>
            </section>
            <hr className="m-0" />

            <section
              className="resume-section"
              id="emotion"
              style={{ justifyContent: "center" }}
            >
              <EmChart
                happyPercent={happyPercent}
                setHappyPercent={setHappyPercent}
                surprisedPercent={surprisedPercent}
                setSurprisedPercent={setSurprisedPercent}
                angerPercent={angerPercent}
                setAngerPercent={setAngerPercent}
                sadnessPercent={sadnessPercent}
                setSadnessPercent={setSadnessPercent}
                disgustPercent={disgustPercent}
                setDisgustPercent={setDisgustPercent}
                neutralPercent={neutralPercent}
                setNeutralPercent={setNeutralPercent}
                fearPercent={fearPercent}
                setFearPercent={setFearPercent}
              ></EmChart>

              <EmComments
                happyComments={happyComments}
                sadnessComments={sadnessComments}
                disgustComments={disgustComments}
                angerComments={angerComments}
                neutralComments={neutralComments}
                surprisedComments={surprisedComments}
                fearComments={fearComments}
                setHappyComments={setHappyComments}
                setSadnessComments={setSadnessComments}
                setDisgustComments={setDisgustComments}
                setAngerComments={setAngerComments}
                setNeutralComments={setNeutralComments}
                setSurprisedComments={setSurprisedComments}
                setFearComments={setFearComments}
              ></EmComments>
            </section>
            <hr className="m-0" />

            <Interest url={url} />
          </div>
        </div>
      )}
    </div>
  );
};

export default Analyze;
