import pandas as pd
import pandas_profiling

# 데이터셋 OverView

data = pd.read_csv('./spam.csv',encoding='latin1') # 프로 파일링 할 파일 지정
pr=data.profile_report() # 프로파일링 결과 리포트를 pr에 저장
pr.to_file('./pr_report.html') # pr_report.html 파일로 저장
pr