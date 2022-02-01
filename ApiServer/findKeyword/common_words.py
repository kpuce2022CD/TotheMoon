from keywordExtracter import keywordExtracter
from collections import Counter

class CommonWords:  # commonwords를 추출하는 클래스

  def __init__(self):
    self.count = Counter()
    self.keywordExtracter = keywordExtracter()

  def word_count_list_krwordrank(self,links):
    # krwordsrank를 통한 commonwords 추출
    word_list = []
    for i in links:
      keywords = self.keywordExtracter.get_comments_from_excel(i).keywords_from_krwordsrank()
      keywords = [*keywords]
      word_list.extend(keywords)
      # 여러 영상의 댓글의 키워드를 뽑아 하나의 리스트로 합침
    self.count = Counter(word_list)
    # 리스트의 빈도수 계산
    return self

  def word_count_list_soynlp(self, links):
    # soynlp를 통한 commonwords 추출
    word_list = []
    for i in links:
      keywords = self.keywordExtracter.get_comments_from_excel(i).keywords_from_soynlp()
      keywords = [*keywords]
      word_list.extend(keywords)
      # 여러 영상의 댓글의 키워드를 뽑아 하나의 리스트로 합침
    self.count = Counter(word_list)
    # 리스트의 빈도수 계산
    return self

  def word_count_list_konlpy(self, links):
    # konlpy를 통한 commonwords 추출
    word_list = []
    for i in links:
      keywords = self.keywordExtracter.get_comments_from_excel(i).keywords_from_konlpy()
      keywords = [*keywords]
      word_list.extend(keywords)
      # 여러 영상의 댓글의 키워드를 뽑아 하나의 리스트로 합침
    self.count = Counter(word_list)
    # 리스트의 빈도수 계산
    return self

  def common_word_list(self,n):
    # 파라미터 n은 빈도수가 n이상인 단어를 common_word로 판단
    common_words = []
    for key, value in self.count.items():
      if(value>=n):
        common_words.append(key)
    # 반환값 : commonwords 리스트
    return common_words
  
  def print_word_count_list(self):
    print(self.count)






