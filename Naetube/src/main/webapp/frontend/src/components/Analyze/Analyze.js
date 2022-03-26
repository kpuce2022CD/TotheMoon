import React from "react";
import NavItem from "../Navbar/NavItem";
import Home from "../Home/Home";
import Interest from "../Interest/Interest";

const Analyze = () => {
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

        <div className="container-fluid p-0">
          <Home />
          <hr className="m-0" />

          <section className="resume-section" id="np">
            <div className="resume-section-content"></div>
          </section>
          <hr className="m-0" />

          <section className="resume-section" id="emotion">
            <div className="resume-section-content"></div>
          </section>
          <hr className="m-0" />

          <Interest />
        </div>
      </div>
    </>
  );
};

export default Analyze;
