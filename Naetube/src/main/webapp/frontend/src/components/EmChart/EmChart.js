import React, { useState } from 'react';
import {Chart, Doughnut} from 'react-chartjs-2';
import 'chart.js/auto';


function EmChart(props) {

    const data = {

        labels: [
          '공포',
          '놀람',
          '분노',
          '슬픔',
          '중립',
          '행복',
          '혐오'
        ],
        datasets: [{
          data: [
                props.fearPercent,
                props.surprisedPercent,
                props.angerPercent,
                props.sadnessPercent,
                props.neutralPercent,
                props.happyPercent,
                props.disgustPercent],
          backgroundColor: [
              'rgb(153, 102, 255)',
              'rgb(255, 159, 64)',
              'rgb(255, 205, 86)',
              'rgb(75, 192, 192)',
              'rgb(54, 162, 235)',
              'rgb(255, 99, 132)',
              'rgb(188,143,143)'
          ],
          hoverBackgroundColor: [
              'rgb(153, 102, 255)',
              'rgb(255, 159, 64)',
              'rgb(255, 205, 86)',
              'rgb(75, 192, 192)',
              'rgb(54, 162, 235)',
              'rgb(255, 99, 132)',
              'rgb(188,143,143)'
          ]
        }]
      };


  return (
    <div>
        <Doughnut style={{width:"400px", height:"400px"}} data={data}/>
    </div>
  );
}
export default EmChart;