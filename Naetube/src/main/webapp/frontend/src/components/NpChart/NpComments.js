import React, {useState, useEffect} from "react";
import { Button, Navbar, Nav, Tab, Container } from 'react-bootstrap'
import {CSSTransition} from 'react-transition-group'



function NpComments() {


    let [스위치, 스위치변경] = useState(false);

    let [clickedTab, setClickedTab] = useState(0);

    let [positiveComments, setPositiveComments] = useState([
        {
            id: "가나다라",
            comment: "긍정 긍정 안녕하세요 안녕하세요 안녕하세요"
        },
        {
            id: "베짱이 2222",
            comment: "반갑습니다 긍정 긍정 반갑습니다"
        },
        {
            id: "코카콜라 제로",
            comment: "코카콜라 긍정 긍정 코카콜라 코카콜라"
        },
        {
            id: "코카콜라 제로",
            comment: "긍정 긍정 코카콜라 긍정 긍정 코카콜라 아이에오오오 아이에오오오 아이에오오오 아이에오오오"
        },
        {
            id: "기기기기기무무무무무나나나나나",
            comment: "과자 바나나나나나나나나나 긍정 긍정 코카콜라 아이에오오오 아이에오오오 아이에오오오 아이에오오오"
        },
        {
            id: "마늘요리",
            comment: "긍정 긍정긍정 긍정긍정 긍정오오"
        },
        {
            id: "아아아아무무뭄 아아긍정 긍정무긍정 긍정긍정 긍정무무뭄",
            comment: " 가나다라가나다라가나긍정 긍정다라가나다라 긍정 긍정라가나다라가나다라가나다라"
        }
      ]);

      let [negativeComments, setNegativeComments] = useState([
        {
            id: "마바사아",
            comment: "부정 부정 안녕하세요 안녕하세요 안녕하세요"
        },
        {
            id: "123124125 2222",
            comment: "부정 부정 긍정 긍정 반갑습니다"
        },
        {
            id: "펩베ㅔ페펩시 제로",
            comment: "코카콜라 부정 부정 코카콜라 코카콜라"
        },
        {
            id: "자잦라ㅜ루루루루",
            comment: "부정 부정 코카콜라 부정 부정 코카콜라 아이에오오오 아이에오오오 아이에오오오 아이에오오오"
        },
        {
            id: "기기기기기무무무무무나나나나나",
            comment: "부정 부정나나나나나나 긍정 긍정 코카콜라 아이에오오오 아이에오오오 아이에오오오 아이에오오오"
        },
        {
            id: "마늘요리",
            comment: "긍정 부정 부정 긍정긍정 긍정오오"
        },
        {
            id: "아아아아무무뭄 아아긍정 긍정무긍정 긍정긍정 긍정무무뭄",
            comment: " 가나다라가부정 부정긍정 긍정다라가나다라 긍정 긍정라가나다라가나다라가나다라"
        }
      ]);


    return(
        <div className="comments-div">
            <div className="navbar">

                <h3 style={{fontFamily:"NanumGothic",marginLeft:"15px"}}>Comments</h3>

                <Nav className='mt-5 bg-white' variant="tabs" defaultActiveKey="/link-0">
                        <Nav.Item>
                            <Nav.Link id="nav-menu-positive" eventKey="link-0" onClick={() =>{ 스위치변경(false); setClickedTab(0)}}>긍정</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link id="nav-menu-negative" eventKey="link-1" onClick={() =>{ 스위치변경(false); setClickedTab(1)}}>부정</Nav.Link>
                        </Nav.Item>
                </Nav>
            </div>

            <table style={{width:"100%"}} className="tb">
                <colgroup>
                    <col style={{width:"40%"}}></col>
                    <col style={{width:"60%"}}></col>
                </colgroup>
                <thead>
                    <tr>
                        <th scope="col" style={{fontSize:"20px"}}>작성자 아이디</th>
                        <th scope="col" style={{fontSize:"20px"}}>댓글</th>
                    </tr>
                </thead>
            </table>
            <CSSTransition in={스위치} classNames="effect" timeout={500}>
            <TabContent positiveComments={positiveComments} negativeComments={negativeComments} clickedTab={clickedTab} 스위치변경={스위치변경}></TabContent>
            </CSSTransition>
        </div>
    );
}

function TabContent(props) {

    useEffect( () => {
        props.스위치변경(true);
    });

    if (props.clickedTab === 0) {
        return(
            <div className="box" style={{overflow:"scroll",overflowX:"hidden",height:"400px"}}>
            {
                props.positiveComments.map(function(positiveComments) {
                    return(
                        <div>
                            <table style={{width:"100%"}} className="tb">
                                <colgroup>
                                    <col style={{width:"40%"}}></col>
                                    <col style={{width:"60%"}}></col>
                                 </colgroup>
                                <tbody>
                                    <tr>
                                        <th scope="col">{positiveComments.id}</th>
                                        <th style={{width:"60%"}}>{positiveComments.comment}</th>
                                    </tr>
                                </tbody>
                            </table>
                            <hr></hr>
                        </div>
                    );
                })
            }
            </div>
        );
    }
    else if (props.clickedTab === 1) {
        return(
            <div className="box" style={{overflow:"scroll",overflowX:"hidden",height:"400px"}}>
            {
                props.negativeComments.map(function(negativeComments) {
                    return(
                        <div>
                            <table style={{width:"100%"}} className="tb">
                                <colgroup>
                                    <col style={{width:"40%"}}></col>
                                    <col style={{width:"60%"}}></col>
                                 </colgroup>
                                <tbody>
                                    <tr>
                                        <th scope="col">{negativeComments.id}</th>
                                        <th style={{width:"60%"}}>{negativeComments.comment}</th>
                                    </tr>
                                </tbody>
                            </table>
                            <hr></hr>
                        </div>
                    );
                })
            }
            </div>
        );
    }


}

export default NpComments;