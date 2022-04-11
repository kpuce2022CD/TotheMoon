import pandas as pd
import numpy as np
import re
import datetime
import dateutil.parser as dp


# C:\Users\X\IdeaProjects\TotheMoon\ApiServer

def get_interestData(filepath):
    parent_filepath = 'C:\\Users\\X\\IdeaProjects\\TotheMoon\\ApiServer\\'
    # filepath = parent_filepath + '3ajwX48yPig.xlsx'

    df = pd.read_excel(filepath)
    size = df.shape[0]  # 댓글의 전체개수
    dates_divide = []  # 영상의 모든 댓글의 date 정보
    dates_count = []  # 일별댓글 개수카운팅을 위한 date 정보
    parse_time = []
    dates_data = []
    result_data = []

    for i in range(0, size):
        dates_divide.append((df['date'][i]))  # 각 댓글들의 년,월,일,시,분,초까지 배열에 저장
        dates_count.append((df['date'][i][0:10]))  # 각 댓글들의 년,월,일까지 배열에 저장
        parse_time.append(dp.parse(dates_divide[i]))
    # print(dates[size-1]) #마지막 index=0, 첫댓글 index=size-1
    # print(df['date'][1])

    startDate = parse_time[size - 1]
    endDate = parse_time[0]
    #print(startDate)
    #print(endDate)
    #print(endDate - startDate)
    div_std = []
    diff = (endDate - startDate)
    day_diff = diff.days * 86400
    second_diff = diff.seconds
    totalDiff = day_diff + second_diff  # 첫댓글, 마지막댓글 차이 초계산
    #print(totalDiff)
    standard = divmod(totalDiff, 11)
    std = divmod(standard[0], 60)
    for k in range(11):
        startDate += datetime.timedelta(minutes=std[0])
        div_std.append(startDate)  # 시간차이를 12등분을 위한 구분시간 11개
    #print(div_std)

    # 댓글의 날짜 수 대로 카운트 배열 생성
    count = {}
    for j in dates_count:
        try:
            count[j] += 1
        except:
            count[j] = 1

    # 딕셔너리에서 value 값만 추출
    countValue = count.values()

    # 딕셔너리 리스트화
    countList = list(countValue)

    # 날짜 데이터가 현재 내림차순 이므로 날짜에 대한 댓글 개수도 오름차순으로 정렬
    countList.reverse()

    # 날짜 데이터 정제 (중복제거)
    for v in dates_count:
        if v not in dates_data:
            dates_data.append(v)

    # 날짜 데이터 오름차순으로 정렬
    dates_data.sort()

    for i in range(0, len(dates_data)):
        data = {"commentDate": dates_data[i], "commentCount": countList[i]}
        result_data.append(data)

    #print(result_data)
    return (result_data)
    # 기준 시간에서 각 댓글의 시간차 음,양수로 기준시간 이전댓글, 이후댓글 파악
    # 기준 시간마다 댓글개수를 누적해서 카운팅하고 마지막에 한칸씩 누적카운트를 빼주면서 각 기준시간대까지의 댓글 개수확인
