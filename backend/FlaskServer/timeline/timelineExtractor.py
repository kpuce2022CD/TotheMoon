import pandas as pd
from bs4 import BeautifulSoup
from pathlib import Path
from collections import Counter


class timelineExtractor:

# 타임라인 처리 시 타임라인 + 댓글 + (작성자)
# 하나의 댓글에 여러개의 타임라인을 걸어두었을 경우

# html 태그 그대로 처리!!! 그런것만 검출
# js에서 링크만 사아악 바꿔주기
# np_classify.py에서 timeline 추출 기능 제거해야함
# 10등까지
# 댓글 x 빈도만
# 타임라인 best 10 추출

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
  
  def get_best_timelines(self,n=5):
    timeline = []
    for i in self.data['comment']:  
      soup = BeautifulSoup(i,'html.parser')
      for j in soup.find_all('a'):
        if 'https://www.youtube.com/watch?v='+self.url in j['href']:
          timeline.append(self.standardized_time(j.text))
    best_timelines = Counter(timeline).most_common()
    best_timelines = [{'count':i[1], 'sec':i[0]} for i in best_timelines]
    best_timelines = self.merge_timeline(best_timelines)
    best_timelines = self.sorting_timeline_comments(best_timelines)
    for i in best_timelines:
      i['label'] = self.get_section(i['sec'])

    return best_timelines[:n]
    
  def sorting_timeline_comments(self, timeline_list, para='count'):
    timeline_list.sort(key = lambda x : -x[para])
    return timeline_list
  
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

  def sec_to_time(self, sec):
    hour = str(sec//3600)
    minutes = str(sec%3600//60)
    seconds = str(sec%3600%60)
    result = ''
    if(hour == '0'):
      result = '0'*(len(minutes)%2)+minutes + ':' +'0'*(len(seconds)%2) +seconds
    else:
      result = str(hour) +':'+'0'*(len(minutes)%2)+minutes + ':' +'0'*(len(seconds)%2) +seconds
    return result

  def get_section(self, sec):
    head = ''
    tail = ''
    head = self.sec_to_time(sec)
    tail = self.sec_to_time(sec+10)
    return head + '~' + tail
  
  def merge_timeline(self, timeline_list):
    timeline_list.sort(key = lambda x : x['sec'])
    for i in range(len(timeline_list)):
      if(timeline_list[i] == None):
        continue;
      target = timeline_list[i]['sec']
      for j in range(i, len(timeline_list)):
        if(timeline_list[j] == None):
          continue;
        if(target < timeline_list[j]['sec'] <= target+10):
          if(i!=j):
            timeline_list[i]['count'] += timeline_list[j]['count']
            timeline_list[j] = None
    return list(filter(None, timeline_list))
      

  
  def extract_timeline_comments(self, url):
    self.get_comments_from_excel(url).get_timeline().sorting_timeline_comments()
    return self.timeline_list

  
 



  
