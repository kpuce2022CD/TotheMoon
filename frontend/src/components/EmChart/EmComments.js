import React, { useState, useEffect } from "react";
import { Button, Navbar, Nav, Tab, Container } from "react-bootstrap";
import { CSSTransition } from "react-transition-group";

function EmComments(props) {
  let [스위치, 스위치변경] = useState(false);

  let [clickedTab, setClickedTab] = useState(0);

  return (
    <div className="emcomments-div">
      <h3
        style={{
          fontFamily: "NanumGothic",
          marginLeft: "40px",
          marginTop: "15px",
        }}
      >
        Comments
      </h3>

      <div className="navbar" style={{ justifyContent: "flex-end" }}>
        <Nav className="bg-white" variant="tabs" defaultActiveKey="/link-0">
          <Nav.Item>
            <Nav.Link
              id="nav-menu-emotion-happy"
              eventKey="link-0"
              onClick={() => {
                스위치변경(false);
                setClickedTab(0);
              }}
            >
              행복
            </Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link
              id="nav-menu-emotion-surprised"
              eventKey="link-1"
              onClick={() => {
                스위치변경(false);
                setClickedTab(1);
              }}
            >
              놀람
            </Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link
              id="nav-menu-emotion-anger"
              eventKey="link-2"
              onClick={() => {
                스위치변경(false);
                setClickedTab(2);
              }}
            >
              분노
            </Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link
              id="nav-menu-emotion-sadness"
              eventKey="link-3"
              onClick={() => {
                스위치변경(false);
                setClickedTab(3);
              }}
            >
              슬픔
            </Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link
              id="nav-menu-emotion-neutral"
              eventKey="link-4"
              onClick={() => {
                스위치변경(false);
                setClickedTab(4);
              }}
            >
              중립
            </Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link
              id="nav-menu-emotion-fear"
              eventKey="link-5"
              onClick={() => {
                스위치변경(false);
                setClickedTab(5);
              }}
            >
              공포
            </Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link
              id="nav-menu-emotion-disgust"
              eventKey="link-6"
              onClick={() => {
                스위치변경(false);
                setClickedTab(6);
              }}
            >
              혐오
            </Nav.Link>
          </Nav.Item>
        </Nav>

        <table style={{ width: "612px" }} className="tb">
          <colgroup>
            <col style={{ width: "40%" }}></col>
            <col style={{ width: "60%" }}></col>
          </colgroup>
          <thead style={{ margin: "30px" }}>
            <tr>
              <th scope="col" style={{ fontSize: "20px" }}>
                작성자 아이디
              </th>
              <th scope="col" style={{ fontSize: "20px" }}>
                댓글
              </th>
            </tr>
          </thead>
        </table>
        <CSSTransition in={스위치} classNames="effect" timeout={500}>
          <TabContent
            happyComments={props.happyComments}
            surprisedComments={props.surprisedComments}
            angerComments={props.angerComments}
            sadnessComments={props.sadnessComments}
            neutralComments={props.neutralComments}
            fearComments={props.fearComments}
            disgustComments={props.disgustComments}
            clickedTab={clickedTab}
            스위치변경={스위치변경}
          ></TabContent>
        </CSSTransition>
      </div>
    </div>
  );
}

function TabContent(props) {
  useEffect(() => {
    props.스위치변경(true);
  });

  if (props.clickedTab === 0) {
    return (
      <div
        className="box"
        style={{ overflow: "scroll", overflowX: "hidden", height: "400px" }}
      >
        {props.happyComments.map(function (happyComments) {
          return (
            <div>
              <table style={{ width: "600px" }} className="tb">
                <colgroup>
                  <col style={{ width: "40%" }}></col>
                  <col style={{ width: "60%" }}></col>
                </colgroup>
                <tbody>
                  <tr>
                    <th style={{ width: "40%" }}>{happyComments.id}</th>
                    <th style={{ width: "60%" }}>
                      <div
                        dangerouslySetInnerHTML={{
                          __html: happyComments.comment,
                        }}
                      ></div>
                    </th>
                  </tr>
                </tbody>
              </table>
              <hr></hr>
            </div>
          );
        })}
      </div>
    );
  } else if (props.clickedTab === 1) {
    return (
      <div
        className="box"
        style={{ overflow: "scroll", overflowX: "hidden", height: "400px" }}
      >
        {props.surprisedComments.map(function (surprisedComments) {
          return (
            <div>
              <table style={{ width: "600px" }} className="tb">
                <colgroup>
                  <col style={{ width: "40%" }}></col>
                  <col style={{ width: "60%" }}></col>
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="col">{surprisedComments.id}</th>
                    <th style={{ width: "60%" }}>
                      <div
                        dangerouslySetInnerHTML={{
                          __html: surprisedComments.comment,
                        }}
                      ></div>
                    </th>
                  </tr>
                </tbody>
              </table>
              <hr></hr>
            </div>
          );
        })}
      </div>
    );
  } else if (props.clickedTab === 2) {
    return (
      <div
        className="box"
        style={{ overflow: "scroll", overflowX: "hidden", height: "400px" }}
      >
        {props.angerComments.map(function (angerComments) {
          return (
            <div>
              <table style={{ width: "600px" }} className="tb">
                <colgroup>
                  <col style={{ width: "40%" }}></col>
                  <col style={{ width: "60%" }}></col>
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="col">{angerComments.id}</th>
                    <th style={{ width: "60%" }}>
                      <div
                        dangerouslySetInnerHTML={{
                          __html: angerComments.comment,
                        }}
                      ></div>
                    </th>
                  </tr>
                </tbody>
              </table>
              <hr></hr>
            </div>
          );
        })}
      </div>
    );
  } else if (props.clickedTab === 3) {
    return (
      <div
        className="box"
        style={{ overflow: "scroll", overflowX: "hidden", height: "400px" }}
      >
        {props.sadnessComments.map(function (sadnessComments) {
          return (
            <div>
              <table style={{ width: "600px" }} className="tb">
                <colgroup>
                  <col style={{ width: "40%" }}></col>
                  <col style={{ width: "60%" }}></col>
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="col">{sadnessComments.id}</th>
                    <th style={{ width: "60%" }}>
                      <div
                        dangerouslySetInnerHTML={{
                          __html: sadnessComments.comment,
                        }}
                      ></div>
                    </th>
                  </tr>
                </tbody>
              </table>
              <hr></hr>
            </div>
          );
        })}
      </div>
    );
  } else if (props.clickedTab === 4) {
    return (
      <div
        className="box"
        style={{ overflow: "scroll", overflowX: "hidden", height: "400px" }}
      >
        {props.neutralComments.map(function (neutralComments) {
          return (
            <div>
              <table style={{ width: "600px" }} className="tb">
                <colgroup>
                  <col style={{ width: "40%" }}></col>
                  <col style={{ width: "60%" }}></col>
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="col">{neutralComments.id}</th>
                    <th style={{ width: "60%" }}>
                      <div
                        dangerouslySetInnerHTML={{
                          __html: neutralComments.comment,
                        }}
                      ></div>
                    </th>
                  </tr>
                </tbody>
              </table>
              <hr></hr>
            </div>
          );
        })}
      </div>
    );
  } else if (props.clickedTab === 5) {
    return (
      <div
        className="box"
        style={{ overflow: "scroll", overflowX: "hidden", height: "400px" }}
      >
        {props.fearComments.map(function (fearComments) {
          return (
            <div>
              <table style={{ width: "600px" }} className="tb">
                <colgroup>
                  <col style={{ width: "40%" }}></col>
                  <col style={{ width: "60%" }}></col>
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="col">{fearComments.id}</th>
                    <th style={{ width: "60%" }}>
                      <div
                        dangerouslySetInnerHTML={{
                          __html: fearComments.comment,
                        }}
                      ></div>
                    </th>
                  </tr>
                </tbody>
              </table>
              <hr></hr>
            </div>
          );
        })}
      </div>
    );
  } else if (props.clickedTab === 6) {
    return (
      <div
        className="box"
        style={{ overflow: "scroll", overflowX: "hidden", height: "400px" }}
      >
        {props.disgustComments.map(function (disgustComments) {
          return (
            <div>
              <table style={{ width: "600px" }} className="tb">
                <colgroup>
                  <col style={{ width: "40%" }}></col>
                  <col style={{ width: "60%" }}></col>
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="col">{disgustComments.id}</th>
                    <th style={{ width: "60%" }}>
                      <div
                        dangerouslySetInnerHTML={{
                          __html: disgustComments.comment,
                        }}
                      ></div>
                    </th>
                  </tr>
                </tbody>
              </table>
              <hr></hr>
            </div>
          );
        })}
      </div>
    );
  }
}

export default EmComments;
