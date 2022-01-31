import numpy as np
import pandas as pd
from krwordrank.word import KRWordRank
from soynlp.noun import LRNounExtractor_v2
from konlpy.tag import Okt
from collections import Counter
import copy

# 영어 숫자를 제외한 한국어 댓글를 통해 키워드를 추출
# soynlp, krwordsrank는 비지도 학습이므로 댓글 수가 적으면 정확한 키워드 추출이 안될 수 있다(댓글 50개이하..?)


class keywordExtracter: # 키워드 추출 클래스

  def __init__(self, common_words=[]):
    self.common_words = common_words
    
  def set_common_words(self, common_words):
    self.common_words = common_words
  
  def get_comments_from_excel(self,link):
    data = pd.read_excel(link)  #엑셀 파일 읽기
    data['comment'] = data['comment'].str.replace('[^ㄱ-ㅎㅏ-ㅣ가-힣 ]','', regex=True)   # 한국어를 제외한 문자 제거(숫자, 영어도 모두 제거)
    data['comment'] = data['comment'].str.replace('^ +','', regex=True)
    data['comment'].replace('',np.nan, inplace=True)
    data = data.dropna(how='any')
    self.data = data['comment'].values.tolist() # df to list
    return self
  
  def keywords_from_krwordsrank(self):
    # krwordsrank를 통한 키워드 추출
    wordrank_extractor = KRWordRank()
    # 영어 포함 시 normalize 해주어야함
    keywords, rank, graph = wordrank_extractor.extract(self.data)
    return keywords
  
  def keywords_from_soynlp(self):
    # soynlp를 통한 키워드 추출
    # verbose : 함수 실행시 상세 정보를 출력할 것인지
    noun_extractor = LRNounExtractor_v2(verbose=False, extract_compound=True)
    keywords = noun_extractor.train_extract(self.data)
    keywords = sorted(keywords.items(), reverse=True, key= lambda x : x[1])   # 빈도수 순으로 정렬(빈도수 높은 순으로)
    keywords = dict(((key, value) for key, value in keywords))
    # 딕셔너리로 변환
    # 반환값 : 빈도수 순으로 정렬된 키워드 딕셔너리(키워드 : 빈도수)
    return keywords
  
  def keywords_from_konlpy(self):
    # konlpy를 통한 키워드 추출
    data = copy.deepcopy(self.data)   # 깊은 복사
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



  def method_comparison(self, link):
    # soynlp, krwordsrank, konlpy 결과 비교
    self.get_comments_from_excel(link)
    keywords = [self.keywords_from_konlpy(), self.keywords_from_soynlp(), self.keywords_from_krwordsrank()]
    print('konlpy best 5 :'+', '.join(self.get_best5_keywords(keywords[0],True)))
    print('soynlp best 5 :'+', '.join(self.get_best5_keywords(keywords[1],True)))
    print('krwordsrank best 5 :'+', '.join(self.get_best5_keywords(keywords[2],False)))
  

