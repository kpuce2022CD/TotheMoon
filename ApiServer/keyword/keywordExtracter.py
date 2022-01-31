import numpy as np
import pandas as pd
from krwordrank.word import KRWordRank
from soynlp.noun import LRNounExtractor_v2
from konlpy.tag import Okt
from collections import Counter
import copy

# 영어 숫자를 제외한 한국어 댓글를 통해 키워드를 추출
# soynlp, krwordsrank는 비지도 학습이므로 댓글 수가 적으면 정확한 키워드 추출이 안될 수 있다(댓글 50개이하..?)
# 댓글이 적을 경우 => 키워드 유사 10개를 못 뽑아 낼경우 error => 예외처리 해주어야함


class keywordExtracter: # 키워드 추출 클래스

  def __init__(self, common_words=[]):
    self.common_words = common_words
    
  def set_common_words(self, common_words):
    self.common_words = common_words
  
  def get_comments_from_excel(self,link):
    data = pd.read_excel(link)  # 엑셀 파일 읽기
    self.data = copy.deepcopy(data)   # 깊은 복사
    data['comment'] = data['comment'].str.replace('[^ㄱ-ㅎㅏ-ㅣ가-힣 ]','', regex=True)   # 한국어를 제외한 문자 제거(숫자, 영어도 모두 제거)
    data['comment'] = data['comment'].str.replace('^ +','', regex=True)
    data['comment'].replace('',np.nan, inplace=True)
    data = data.dropna(how='any')
    self.comments = data['comment'].values.tolist() # df to list
    return self
  
  def keywords_from_krwordsrank(self):
    # krwordsrank를 통한 키워드 추출
    wordrank_extractor = KRWordRank()
    # 영어 포함 시 normalize 해주어야함
    keywords, rank, graph = wordrank_extractor.extract(self.comments)
    return keywords
  
  def keywords_from_soynlp(self):
    # soynlp를 통한 키워드 추출
    # verbose : 함수 실행시 상세 정보를 출력할 것인지
    noun_extractor = LRNounExtractor_v2(verbose=False, extract_compound=True)
    keywords = noun_extractor.train_extract(self.comments)
    keywords = sorted(keywords.items(), reverse=True, key= lambda x : x[1])   # 빈도수 순으로 정렬(빈도수 높은 순으로)
    keywords = dict(((key, value) for key, value in keywords))
    # 딕셔너리로 변환
    # 반환값 : 빈도수 순으로 정렬된 키워드 딕셔너리(키워드 : 빈도수)
    return keywords
  
  def keywords_from_konlpy(self):
    # konlpy를 통한 키워드 추출
    data = copy.deepcopy(self.comments)   # 깊은 복사
    okt = Okt()
    for i in range(len(data)):   # 토큰화 n번
      data[i] = okt.nouns(data[i])  #명사 추출
    data = sum(data,[])  # 리스트 합치기(2차원 -> 1차원)
    keywords = dict(Counter(data))  #토큰 빈도수 계산
    keywords = sorted(keywords.items(), reverse=True, key= lambda x : x[1]) # 빈도수 순으로 정렬(빈도수 높은 순으로)
    keywords = dict(((key, value) for key, value in keywords))
    # 딕셔너리로 변환
    # 반환값 : 빈도수 순으로 정렬된 키워드 딕셔너리(키워드 : 빈도수)
    return keywords
  
  def get_best5_keywords(self, keywords, one=False):
    # best 5 키워드 뽑기
    # common_words나 한글자 단어는 키워드에서 제외
    # 파라미터 one은 한글자 단어를 제외시킬건지(True:제외, False:제외x)
    # 한글자 단어를 제외시키면 real 한글자 키워드를 뽑지 못하는 issue(ex : 깡, 진)
    keywords = [*keywords]
    # 딕셔너리의 인덱스값만을 뽑아 배열로 만듬
    if(one == True):
      keywords = [word for word in keywords if len(word)!=1]
      # 한글자 단어 키워드에서 제외
    best_5_keywords = [word for word in keywords if not word in self.common_words][:5]
    # common_words 제외 & 상위 5개 키워드 추출
    # 반환값 : 상위 5개 키워드
    return best_5_keywords
  
  def get_comments_related_to_keyword(self,keyword, n=10):
    # 키워드에 대한 관련 댓글 추출
    data = self.data.sort_values(by='num_likes', ascending=False)
    # 좋아요 순으로 정렬
    comments = data['comment'].values.tolist()
    related_comments = [] #관련 댓글
    for i in range(len(comments)):
      if(len(related_comments) == n):
        break
      else:
        if(comments[i].find(keyword)!=-1):
          related_comments.append(comments[i])
    return related_comments

  def method_comparison(self, link):
    # soynlp, krwordsrank, konlpy 결과 비교
    self.get_comments_from_excel(link)
    keywords = [self.keywords_from_konlpy(), self.keywords_from_soynlp(), self.keywords_from_krwordsrank()]
    print('konlpy best 5 :'+', '.join(self.get_best5_keywords(keywords[0],True)))
    print('soynlp best 5 :'+', '.join(self.get_best5_keywords(keywords[1],True)))
    print('krwordsrank best 5 :'+', '.join(self.get_best5_keywords(keywords[2],False)))
  
  def get_comments_related_to_best5keywords(self, func, one=True, n=10):
    # best5 키워드를 추출하고 그에 관한 관련댓글 추출
    # 파라미터 func 사용할 키워드 추출 함수 설정
    if(func == self.keywords_from_krwordsrank):
      # func가 krwordsrank를 통한 키워드 추출일 경우 one 파라미터를 False로 수정한다
      one = False
    comments = []  #best5 키워드의 관련 댓글들 n개*5가 담길 리스트 
    best_5_keywords = self.get_best5_keywords(func(),one) #best5 키워드
    for i in best_5_keywords:
      comments.append(self.get_comments_related_to_keyword(i,n))
    # best5 키워드와 관련 댓글들을 반환
    return best_5_keywords, comments
  

