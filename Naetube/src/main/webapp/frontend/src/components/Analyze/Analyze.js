import React from "react";
import NavItem from "../Navbar/NavItem";
import Home from "../Home/Home";
import NpCharts from "../NpChart/NpChart";
import NpComments from "../NpChart/NpComments";
import EmChart from "../EmChart/EmChart";
import EmComments from "../EmChart/EmComments";
import axios from "axios"
import { useState, useEffect } from "react";



const Analyze = () => {

  let [positiveComments, setPositiveComments] = useState([]);
  let [negativeComments, setNegativeComments] = useState([]);
  let [happyComments, setHappyComments] = useState([]);
  let [sadnessComments, setSadnessComments] = useState([]);
  let [fearComments, setFearComments] = useState([]);
  let [disgustComments, setDisgustComments] = useState([]);
  let [surprisedComments, setSurprisedComments] = useState([]);
  let [neutralComments, setNeutralComments] = useState([]);
  let [angerComments, setAngerComments] = useState([]);



  return (
    <>
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

        <GetCommentsData positiveComments={positiveComments} negativeComments={negativeComments}
          happyComments={happyComments} sadnessComments={sadnessComments} disgustComments={disgustComments}
          angerComments={angerComments} neutralComments={neutralComments} surprisedComments={surprisedComments}
          fearComments={fearComments} setPositiveComments={setPositiveComments}
          setNegativeComments={setNegativeComments} setHappyComments={setHappyComments}
          setSadnessComments={setSadnessComments} setDisgustComments={setDisgustComments}
          setAngerComments={setAngerComments} setNeutralComments={setNeutralComments}
          setSurprisedComments={setSurprisedComments} setFearComments={setFearComments}/>

        <div className="container-fluid p-0">
          <Home />
          <hr className="m-0" />



          <section className="resume-section" id="np">
            <NpCharts></NpCharts>
            <NpComments positiveComments={positiveComments} setPositiveComments={setPositiveComments}
            negativeComments={negativeComments} setNegativeComments={setNegativeComments}></NpComments>


          </section>
          <hr className="m-0" />

          <section className="resume-section" id="emotion">
            <EmChart></EmChart>
            <EmComments happyComments={happyComments} sadnessComments={sadnessComments} disgustComments={disgustComments}
          angerComments={angerComments} neutralComments={neutralComments} surprisedComments={surprisedComments}
          fearComments={fearComments} setHappyComments={setHappyComments}
          setSadnessComments={setSadnessComments} setDisgustComments={setDisgustComments}
          setAngerComments={setAngerComments} setNeutralComments={setNeutralComments}
          setSurprisedComments={setSurprisedComments} setFearComments={setFearComments}></EmComments>
          </section>
          <hr className="m-0" />

          <section className="resume-section" id="interest">
            <div className="resume-section-content"></div>
          </section>
        </div>
      </div>
    </>
  );
};

function GetCommentsData(props) {

  let [data,setData] = useState([]);
  let [loading, setLoading] = useState(false);


      useEffect(() => {

          const fetchData = async () => {
              const result = await axios.get(
                  "http://localhost:8080/getComments/kKcTnnMjUT0"
              );
              setData(result.data);


              result.data.map((a,i)=>{
                  if(a.index==="1") {
                    props.setPositiveComments((comment)=>[...comment,a]);
                  }
                  else if(a.index==="0") {
                    props.setNegativeComments((comment)=>[...comment,a]);
                  }
                  else if(a.index==="2") {
                    props.setFearComments((comment)=>[...comment,a]);
                  }
                  else if(a.index==="3") {
                    props.setSurprisedComments((comment)=>[...comment,a]);
                  }
                  else if(a.index==="4") {
                    props.setAngerComments((comment)=>[...comment,a]);
                  }
                  else if(a.index==="5") {
                    props.setSadnessComments((comment)=>[...comment,a]);
                  }
                  else if(a.index==="6") {
                    props.setNeutralComments((comment)=>[...comment,a]);
                  }
                  else if(a.index==="7") {
                    props.setHappyComments((comment)=>[...comment,a]);
                  }
                  else if(a.index==="8") {
                    props.setDisgustComments((comment)=>[...comment,a]);
                  }


              })


              setLoading(true);
          };

      fetchData();
      }, []);

        return(
          null

      );

}

export default Analyze;
