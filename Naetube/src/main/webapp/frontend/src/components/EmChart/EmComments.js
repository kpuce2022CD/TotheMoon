import React, {useState, useEffect} from "react";
import { Button, Navbar, Nav, Tab, Container } from 'react-bootstrap'
import {CSSTransition} from 'react-transition-group'



function EmComments(){



    let [스위치, 스위치변경] = useState(false);

    let [clickedTab, setClickedTab] = useState(0);

    let [happyComments, setHappyComments] = useState([
        {
            id: "가나다라",
            comment: "행복 긍정 안녕하세요 안녕하세요 안녕하세요"
        },
        {
            id: "베짱이 2222",
            comment: "행복행복반갑습니다 긍정 긍정 반갑습니다"
        },
        {
            id: "코카콜라 제로",
            comment: "행복행복코카콜라 긍정 긍정 코카콜라 코카콜라"
        },
        {
            id: "코카콜라 제로",
            comment: "긍정 행복행복행복정 긍정 코카콜라 아이에오오오 아이에오오오 아이에오오오 아이에오오오"
        },
        {
            id: "행복기행복행복행복무무나나나나나",
            comment: "과자 바나행복나나나나나 긍정 긍정 코카콜라 아이에오오오 아이에오오오 아이에오오오 아이에오오오"
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

      let [surprisedComments, setSurprisedComments] = useState([
        {
            id: "마바사아",
            comment: "놀람 부정 안녕하세요 안녕하세요 안녕하세요"
        },
        {
            id: "123124125 2222",
            comment: "놀람놀람정 긍정 긍정 반갑습니다"
        },
        {
            id: "펩베ㅔ페펩시 제로",
            comment: "코카콜놀람놀람놀람정 코카콜라 코카콜라"
        },
        {
            id: "자놀람놀람루루",
            comment: "부정 부정 코카콜라 부정 부정 코카콜라 아이에오오오 아이에오오오 아이에오오오 아이에오오오"
        },
        {
            id: "기놀람놀람무나나나나나",
            comment: "부정 부정나나나나나나 긍정 긍정 코카콜라 아이에오오오 아이에오오오 아이에오오오 아이에오오오"
        },
        {
            id: "마늘요리",
            comment: "놀람놀람놀람 긍정긍정 긍정오오"
        },
        {
            id: "아아아아무무뭄 아아긍정 긍정무긍정 긍정긍정 긍정무무뭄",
            comment: " 가나다라가부정 부정긍정 긍정다라가나다라 긍정 긍정라가나다라가나다라가나다라"
        }
      ]);

      let [angerComments, setAngerComments] = useState([
        {
            id: "분노분노",
            comment: "분노 부정 분노분노요 안녕하세요 안녕하세요"
        },
        {
            id: "1231분노분노 2222",
            comment: "놀분노분노긍정 긍정 반갑습니다"
        },
        {
            id: "펩베ㅔ페펩시 제로",
            comment: "코분노분노놀람정 코카콜라 코카콜라"
        },
        {
            id: "자분노분노루루",
            comment: "부분노분노분노카콜라 부정 부정 코카콜라 아이에오오오 아이에오오오 아이에오오오 아이에오오오"
        },
        {
            id: "기놀람분노분노분노나나나",
            comment: "부정분노분노분노분노정 긍정 코카콜라 아이에오오오 아이에오오오 아이에오오오 아이에오오오"
        },
        {
            id: "마늘요리",
            comment: "놀람놀람놀람 긍정긍정 긍정오오"
        },
        {
            id: "아아아아무무뭄 아아긍정 긍정무긍정 긍정긍정 긍정무무뭄",
            comment: " 가나다라가부정 부정긍정 긍정다라가나다라 긍정 긍정라가나다라가나다라가나다라"
        }
      ]);

      let [sadnessComments, setSadnessComments] = useState([
        {
            id: "슬픔슬픔",
            comment: "분노 부정 분노분노요 안녕하세요 안녕하세요"
        },
        {
            id: "1슬픔슬픔 2222",
            comment: "슬픔슬픔슬픔슬픔노긍정 긍정 반갑습니다"
        },
        {
            id: "펩슬픔슬픔슬픔슬픔시 제로",
            comment: "코분노분노놀람정 코카콜라 코카콜라"
        },
        {
            id: "자분노분노슬픔슬픔루",
            comment: "부분노분노분슬픔슬픔슬픔슬픔슬픔슬픔슬픔슬픔오 아이에오오오 아이에오오오 아이에오오오"
        },
        {
            id: "기놀람분노분노분노나나나",
            comment: "부정분노분노분노분노정 긍정 코카콜라 아이에오오오 아이에오오오 아이에오오오 아이에오오오"
        },
        {
            id: "마늘요리",
            comment: "놀람놀람놀람 긍정긍정 긍정오오"
        },
        {
            id: "아아아아무무뭄 아아긍정 긍정무긍정 긍정긍정 긍정무무뭄",
            comment: " 가나다라가부정 부정긍정 긍정다라가나다라 긍정 긍정라가나다라가나다라가나다라"
        }
      ]);

      let [neutralComments, setNeutralComments] = useState([
        {
            id: "중립중립",
            comment: "분중립중립노요 안녕하세요 안녕하세요"
        },
        {
            id: "1슬픔슬픔 2222",
            comment: "슬중립중립중립픔노긍정 긍정 반갑습니다"
        },
        {
            id: "펩슬픔중립중립중립시 제로",
            comment: "코중립중립중립 코카콜라 코카콜라"
        },
        {
            id: "자분노중립중립",
            comment: "중립 중립중립 ㅍ중립중립중립오 아이에오오오 아이에오오오"
        },
        {
            id: "기놀람분노분노분노나나나",
            comment: "부정분노분노분노분노정 긍정 코카콜라 아이에오오오 아이에오오오 아이에오오오 아이에오오오"
        },
        {
            id: "마늘요리",
            comment: "놀람놀람놀람 긍정긍정 긍정오오"
        },
        {
            id: "아아아아무무뭄 아아긍정 긍정무긍정 긍정긍정 긍정무무뭄",
            comment: " 가나다라가부정 부정긍정 긍정다라가나다라 긍정 긍정라가나다라가나다라가나다라"
        }
      ]);

      let [fearComments, setFearComments] = useState([
        {
            id: "공포공포",
            comment: "공포공포 안녕하세요 안녕하세요"
        },
        {
            id: "1슬픔슬픔 2222",
            comment: "공포공포공포공포공포공포 긍정 반갑습니다"
        },
        {
            id: "펩슬픔공포공포  립공포공포  포제로",
            comment: "코공포공포립 코카콜라 코카콜라"
        },
        {
            id: "자분노공포공포중립",
            comment: "중립 공포공포중립중립오 아이에오오오 아이에오오오"
        },
        {
            id: "기놀람분노 공포공포 공포공포나나",
            comment: "부정공포공포분노분노정 긍정 코카콜라 아이에오오오 아이에오오오 아이에오오오 아이에오오오"
        },
        {
            id: "마늘요리",
            comment: "놀람놀람놀람 긍정긍정 긍정오오"
        },
        {
            id: "아아아아무무뭄 아아긍정 긍정무긍정 긍정긍정 긍정무무뭄",
            comment: " 가나다라가부정 부정긍정 긍정다라가나다라 긍정 긍정라가나다라가나다라가나다라"
        }
      ]);

      let [disgustComments, setDisgustComments] = useState([
        {
            id: "공혐오",
            comment: "혐오혐오 안녕하세요 안녕하세요"
        },
        {
            id: "1슬혐오혐오혐오2222",
            comment: "공혐오혐오혐오 혐오혐오포공포 긍정 반갑습니다"
        },
        {
            id: "펩혐오혐오공포  립공포공포  포제로",
            comment: "코공포혐오립혐오혐오라 코카콜라"
        },
        {
            id: "자분노혐오혐오혐오립",
            comment: "중립 공포공포중립중립오 아이에오오오 아이에오오오"
        },
        {
            id: "기놀람분노 공포공포 공포공포나나",
            comment: "부정공포공포분노분노정 긍정 코카콜라 아이에오오오 아이에오오오 아이에오오오 아이에오오오"
        },
        {
            id: "마늘요리",
            comment: "놀람놀람놀람 긍정긍정 긍정오오"
        },
        {
            id: "아아아아무무뭄 아아긍정 긍정무긍정 긍정긍정 긍정무무뭄",
            comment: " 가나다라가부정 부정긍정 긍정다라가나다라 긍정 긍정라가나다라가나다라가나다라"
        }
      ]);

    return(
        <div className="emcomments-div">

            <h3 style={{fontFamily:"NanumGothic",marginLeft:"15px",marginTop:"15px"}}>Comments</h3>

            <div className="navbar" style={{justifyContent:"flex-end"}}>



                <Nav className='bg-white' variant="tabs" defaultActiveKey="/link-0">
                        <Nav.Item>
                            <Nav.Link id="nav-menu-emotion-happy" eventKey="link-0" onClick={() =>{ 스위치변경(false); setClickedTab(0)}}>행복</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link id="nav-menu-emotion-surprised" eventKey="link-1" onClick={() =>{ 스위치변경(false); setClickedTab(1)}}>놀람</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link id="nav-menu-emotion-anger" eventKey="link-2" onClick={() =>{ 스위치변경(false); setClickedTab(2)}}>분노</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link id="nav-menu-emotion-sadness" eventKey="link-3" onClick={() =>{ 스위치변경(false); setClickedTab(3)}}>슬픔</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link id="nav-menu-emotion-neutral" eventKey="link-4" onClick={() =>{ 스위치변경(false); setClickedTab(4)}}>중립</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link id="nav-menu-emotion-fear" eventKey="link-5" onClick={() =>{ 스위치변경(false); setClickedTab(5)}}>공포</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link id="nav-menu-emotion-disgust" eventKey="link-6" onClick={() =>{ 스위치변경(false); setClickedTab(6)}}>혐오</Nav.Link>
                        </Nav.Item>
                </Nav>

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
            <TabContent happyComments={happyComments} surprisedComments={surprisedComments}
            angerComments={angerComments} sadnessComments={sadnessComments} neutralComments={neutralComments}
            fearComments={fearComments} disgustComments={disgustComments} clickedTab={clickedTab} 스위치변경={스위치변경}></TabContent>
            </CSSTransition>

            </div>
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
                props.happyComments.map(function(happyComments) {
                    return(
                        <div>
                            <table style={{width:"100%"}} className="tb">
                                <colgroup>
                                    <col style={{width:"40%"}}></col>
                                    <col style={{width:"60%"}}></col>
                                 </colgroup>
                                <tbody>
                                    <tr>
                                        <th scope="col">{happyComments.id}</th>
                                        <th style={{width:"60%"}}>{happyComments.comment}</th>
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
                props.surprisedComments.map(function(surprisedComments) {
                    return(
                        <div>
                            <table style={{width:"100%"}} className="tb">
                                <colgroup>
                                    <col style={{width:"40%"}}></col>
                                    <col style={{width:"60%"}}></col>
                                 </colgroup>
                                <tbody>
                                    <tr>
                                        <th scope="col">{surprisedComments.id}</th>
                                        <th style={{width:"60%"}}>{surprisedComments.comment}</th>
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
    else if (props.clickedTab === 2) {
        return(
            <div className="box" style={{overflow:"scroll",overflowX:"hidden",height:"400px"}}>
            {
                props.angerComments.map(function(angerComments) {
                    return(
                        <div>
                            <table style={{width:"100%"}} className="tb">
                                <colgroup>
                                    <col style={{width:"40%"}}></col>
                                    <col style={{width:"60%"}}></col>
                                 </colgroup>
                                <tbody>
                                    <tr>
                                        <th scope="col">{angerComments.id}</th>
                                        <th style={{width:"60%"}}>{angerComments.comment}</th>
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
    else if (props.clickedTab === 3) {
        return(
            <div className="box" style={{overflow:"scroll",overflowX:"hidden",height:"400px"}}>
            {
                props.sadnessComments.map(function(sadnessComments) {
                    return(
                        <div>
                            <table style={{width:"100%"}} className="tb">
                                <colgroup>
                                    <col style={{width:"40%"}}></col>
                                    <col style={{width:"60%"}}></col>
                                 </colgroup>
                                <tbody>
                                    <tr>
                                        <th scope="col">{sadnessComments.id}</th>
                                        <th style={{width:"60%"}}>{sadnessComments.comment}</th>
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
    else if (props.clickedTab === 4) {
        return(
            <div className="box" style={{overflow:"scroll",overflowX:"hidden",height:"400px"}}>
            {
                props.neutralComments.map(function(neutralComments) {
                    return(
                        <div>
                            <table style={{width:"100%"}} className="tb">
                                <colgroup>
                                    <col style={{width:"40%"}}></col>
                                    <col style={{width:"60%"}}></col>
                                 </colgroup>
                                <tbody>
                                    <tr>
                                        <th scope="col">{neutralComments.id}</th>
                                        <th style={{width:"60%"}}>{neutralComments.comment}</th>
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
    else if (props.clickedTab === 5) {
        return(
            <div className="box" style={{overflow:"scroll",overflowX:"hidden",height:"400px"}}>
            {
                props.fearComments.map(function(fearComments) {
                    return(
                        <div>
                            <table style={{width:"100%"}} className="tb">
                                <colgroup>
                                    <col style={{width:"40%"}}></col>
                                    <col style={{width:"60%"}}></col>
                                 </colgroup>
                                <tbody>
                                    <tr>
                                        <th scope="col">{fearComments.id}</th>
                                        <th style={{width:"60%"}}>{fearComments.comment}</th>
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
    else if (props.clickedTab === 6) {
        return(
            <div className="box" style={{overflow:"scroll",overflowX:"hidden",height:"400px"}}>
            {
                props.disgustComments.map(function(disgustComments) {
                    return(
                        <div>
                            <table style={{width:"100%"}} className="tb">
                                <colgroup>
                                    <col style={{width:"40%"}}></col>
                                    <col style={{width:"60%"}}></col>
                                 </colgroup>
                                <tbody>
                                    <tr>
                                        <th scope="col">{disgustComments.id}</th>
                                        <th style={{width:"60%"}}>{disgustComments.comment}</th>
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

export default EmComments;