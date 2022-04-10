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
import { bootstrap } from "react-bootstrap";
import Spinner from "../Spinner/Spinner";

const Analyze = () => {
  const params = useParams();
  const url = params.url;

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
    const fetchData = async () => {
      const result = await axios.get(
        `http://localhost:8080/getcomments/${url}`
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

      result.data.map((a, i) => {
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
        } else if (a.index === "9") {
          setPositivePercent(a.positivePercent);
        } else if (a.index === "10") {
          setNegativePercent(a.negativePercent);
        } else if (a.index === "11") {
          setHappyPercent(a.happyPercent);
        } else if (a.index === "12") {
          setSurprisedPercent(a.surprisedPercent);
        } else if (a.index === "13") {
          setAngerPercent(a.angerPercent);
        } else if (a.index === "14") {
          setSadnessPercent(a.sadnessPercent);
        } else if (a.index === "15") {
          setNeutralPercent(a.neutralPercent);
        } else if (a.index === "16") {
          setDisgustPercent(a.disgustPercent);
        } else if (a.index === "17") {
          setFearPercent(a.fearPercent);
        }
      });
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
                <img
                  className="img-fluid img-profile rounded-circle mx-auto mb-2"
                  src="assets/logo.png"
                  alt="..."
                />
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
                <NavItem href="#home">Home</NavItem>
                <NavItem href="#np">댓글 긍정 부정 분석</NavItem>
                <NavItem href="#emotion">댓글 감정 분석</NavItem>
                <NavItem href="#interest">댓글 관심도</NavItem>
              </ul>
            </div>
          </nav>

          <div className="container-fluid p-0">
            <Home url={url} />
            <hr className="m-0" />

            <section className="resume-section" id="np">
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
