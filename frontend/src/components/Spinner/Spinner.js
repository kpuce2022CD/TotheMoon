import React from 'react';
const Spinner = (props) => {
 return(
    <div style={{position:"absolute",left:"0",right:"0"}} class="body-loading">
    <div class="loading-container container-loading">
        <div class="loading"></div>
        <div id="loading-text">loading</div>

    <a style={{marginTop:"30px"}} id="link">분석중입니다...</a>
    </div>
</div>
 );
}

export default Spinner;