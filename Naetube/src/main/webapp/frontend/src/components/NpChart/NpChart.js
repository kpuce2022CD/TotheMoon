import React from "react";

function NpCharts () {

    let num1 = "25%";
    let num2 = "75%";

    return(

        <div>
            <div class="np-div">
                <section class="graph-section">
                    <div class="graph stack1">
                        <span style={{height:num1}}>{num1}</span>
                    </div>
                    <div class="graph stack2">

                        <span style={{height:num2}}>{num2}</span>
                    </div>
                </section>
                <div>
                    <span style={{display:"flex"}}>
                        <section class="description-positive"></section>
                        <p class="description-font">긍정 댓글 비율</p>                </span>
                    <span style={{display:"flex"}}>
                        <section class="description-negative"></section>
                        <p class="description-font">부정 댓글 비율</p>
                    </span>
                </div>
            </div>
        </div>

    );
}
export default NpCharts;