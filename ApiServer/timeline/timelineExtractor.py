import pandas as pd
from bs4 import BeautifulSoup
from pathlib import Path


class timelineExtractor:

# 타임라인 처리 시 타임라인 + 댓글 + ???
# 하나의 댓글에 여러개의 타임라인을 걸어두었을 경우

# html 태그 그대로 처리!!! 그런것만 검출
# js에서 링크만 사아악 바꿔주기
# np_classify.py에서 timeline 추출 기능 제거해야함

  def __init__(self):
    self.timeline_list = []

  def get_comments_from_excel(self,url):
    self.url = Path(url).stem
    self.data = pd.read_excel(url)
    return self
  
  def get_timeline(self):
    for i in self.data['comment']:
      timeline_data = {}
      timeline = []
      soup = BeautifulSoup(i,'html.parser')
      for j in soup.find_all('a'):
        if 'https://www.youtube.com/watch?v='+self.url in j['href']:
          time = self.standardized_time(j.text)
          i = i.replace(str(j), '<a href="javascript:void(0);" onclick="move('+str(time)+');">'+j.text+'</a>')
          timeline.append(time)
      if not timeline:
        continue
      timeline_data['timeline'] = timeline
      timeline_data['comment'] = i
      timeline_data['length'] = len(timeline)
      self.timeline_list.append(timeline_data)
    return self
  
  
  def sorting_timeline_comments(self):
    self.timeline_list.sort(key = lambda x : (-x['length'], x['timeline']))
    return self
  
  def standardized_time(self,time):
    time = time.split(':')
    hour = 0
    minutes = 0
    seconds = 0
    if(len(time)==3):
      hour+=int(time[0])
      minutes+=int(time[1])
      seconds+=int(time[2])
    else:
      minutes+=int(time[0])
      seconds+=int(time[1])
    result = hour*3600+minutes*60+seconds
    return result
  
  def extract_timeline_comments(self, url):
    self.get_comments_from_excel(url).get_timeline().sorting_timeline_comments()
    return self.timeline_list

  
 



  
