import React from "react";

function NpCharts (props) {

    let positivePercentValue = props.positivePercent + "%";
    let negativePercentValue = props.negativePercent + "%";


    return(

        <div>
            <div class="np-div">
                <section class="graph-section">
                    <div class="graph stack1">
                        <span style={{height:negativePercentValue}}>{negativePercentValue}</span>
                    </div>
                    <div class="graph stack2">

                        <span style={{height:positivePercentValue}}>{positivePercentValue}</span>
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