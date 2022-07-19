import React, { useRef, useState } from "react";
import {useNavigate} from 'react-router-dom'


function Search() {
    const [urlError, setUrlError] = useState(false);
    const [urlName, setUrlName] = useState("");
    const navigate = useNavigate()
    const input = useRef()
    const onClick = ()=>{
        const videoId = new URL(input.current.value).pathname
        const url = `/analyze${videoId}`
        navigate(url)
    }

    const onChangeUrl = (e) => {
        const youtubeUrlRegex = /(http:|https:)?(\/\/)?(www\.)?(youtube.com|youtu.be)\/(watch|embed)?(\?v=|\/)?(\S+)?/g
        if ((!e.target.value || (youtubeUrlRegex.test(e.target.value)))) setUrlError(false);
        else setUrlError(true);
        setUrlName(e.target.value);
        console.log(urlError);
    };

    

    return(
        <div style={{position:"absolute",left:"0",right:"0"}}>
            {/* <!-- Navigation--> */}
        <nav class="navbar navbar-light bg-light static-top">
            <div class="container">
                <a class="navbar-brand" href="#!">Naetube</a>
                {/* <a class="navbar-brand" href="http://localhost:8080/login">Login</a>  */}
                <a class="navbar-brand" href="http://localhost:8080/memberinfo">mypage</a>
                {/* <form action="http://localhost:8080/logout" method="post">
                    <button onClick="" type="submit">logout</button>
                </form> */}
            </div>
        </nav>
        {/* <!-- Masthead--> */}
        <header class="masthead" style={{backgroundColor:"red"}}>
            <div class="container position-relative">
                <div class="row justify-content-center">
                    <div class="col-xl-6">
                        <div class="text-center text-white">
                            {/* <!-- Page heading--> */}
                            <h1 class="mb-5" style={{color:"white",lineHeight:"1.5"}}>손쉽게 유튜브 영상을  <br></br>분석해보세요 !</h1>
                            <h5 style={{color:'white',margin:"20px"}}>해당 영상의 URL을 통해 확인하실 수 있습니다</h5>
                            {/* <!-- Signup form-->
                            <!-- * * * * * * * * * * * * * * *-->
                            <!-- * * SB Forms Contact Form * *-->
                            <!-- * * * * * * * * * * * * * * *-->
                            <!-- This form is pre-integrated with SB Forms.-->
                            <!-- To make this form functional, sign up at-->
                            <!-- https://startbootstrap.com/solution/contact-forms-->
                            <!-- to get an API token!--> */}
                            <form class="form-subscribe" id="contactForm" data-sb-form-api-token="API_TOKEN">
                                {/* <!-- Email address input--> */}
                                <div class="row">
                                    <div class="col">
                                        <input class="form-control form-control-lg" id="emailAddress" type="email" placeholder="유튜브 영상의 URL을 입력해주세요" data-sb-validations="required,email" ref={input} onChange={onChangeUrl}/>
                                        {urlError && <div style={{marginTop:"10px"}} class="invalid-input">정확한 URL 정보를 입력해주세요.</div>}
                                        <div class="invalid-feedback text-white" data-sb-feedback="emailAddress:required">Email Address is required.</div>
                                        <div class="invalid-feedback text-white" data-sb-feedback="emailAddress:email">Email Address Email is not valid.</div>

                                    </div>
                                    <div class="col-auto">
                                        {/* <button class="btn btn-danger btn-lg" id="submitButton" type="submit">분석</button> */}
                                        <a style={{marginTop:"0"}} href="#!" onClick={onClick}  id="a-search" className="button">분석</a>
                                        </div>
                                </div>
                                {/* <!-- Submit success message--> */}
                                {/* <!---->
                                <!-- This is what your users will see when the form-->
                                <!-- has successfully submitted--> */}
                                <div class="d-none" id="submitSuccessMessage">
                                    <div class="text-center mb-3">
                                        <div class="fw-bolder">Form submission successful!</div>
                                        <p>To activate this form, sign up at</p>
                                        <a class="text-white" href="https://startbootstrap.com/solution/contact-forms">https://startbootstrap.com/solution/contact-forms</a>
                                    </div>
                                </div>
                                {/* <!-- Submit error message-->
                                <!---->
                                <!-- This is what your users will see when there is-->
                                <!-- an error submitting the form--> */}
                                <div class="d-none" id="submitErrorMessage"><div class="text-center text-danger mb-3">Error sending message!</div></div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </header>
        {/* <!-- Icons Grid--> */}
        <section class="features-icons bg-light text-center">
            <div class="container">
                <div class="row">
                    <div class="col-lg-4">
                        <div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">
                            <div class="features-icons-icon d-flex"><i class="bi bi-clipboard-minus m-auto text-primary"></i></div>
                            <h3>타임라인과 키워드</h3>
                            <p class="lead mb-0" style={{margin:"30px"}}>해당 영상의 주요 타임라인과 <br></br>베스트 키워드를 조회할 수 있습니다</p>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">
                            <div class="features-icons-icon d-flex"><i class="bi bi-bar-chart-line m-auto text-primary"></i></div>
                            <h3>댓글 긍정 부정 분류</h3>
                            <p class="lead mb-0" style={{margin:"30px"}}>긍정 댓글과 부정 댓글의 비율을 확인할 수 있습니다</p>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="features-icons-item mx-auto mb-0 mb-lg-3">
                            <div class="features-icons-icon d-flex"><i class="bi bi-pie-chart m-auto text-primary"></i></div>
                            <h3>댓글 감정 분석</h3>
                            <p class="lead mb-0" style={{margin:"30px"}}>도넛차트를 통해 댓글의 7가지 감정 비율을 확인할 수 있습니다</p>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        </div>

    );

}



export default Search;