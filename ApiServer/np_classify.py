import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import re
import urllib.request
import os
from konlpy.tag import Okt
from tqdm import tqdm
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences

from tensorflow.keras.layers import Embedding, Dense, LSTM
from tensorflow.keras.models import Sequential
from tensorflow.keras.models import load_model
from tensorflow.keras.callbacks import EarlyStopping, ModelCheckpoint
import pickle


os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'

okt = Okt()
f = open("stopwords.txt", 'r' , encoding='utf8')
stopwords = f.read()
# 정수 인코딩

max_len = 30

# loading
with open('tokenizer.pickle', 'rb') as handle:
    tokenizer = pickle.load(handle)


loaded_model = load_model('best_model.h5')

def comment_classify(comment_file):
  print("dd")

def sentiment_predict(new_sentence):
  new_sentence = re.sub(r'[^ㄱ-ㅎㅏ-ㅣ가-힣 ]','', new_sentence)
  new_sentence = okt.morphs(new_sentence, stem=True) # 토큰화
  new_sentence = [word for word in new_sentence if not word in stopwords] # 불용어 제거
  encoded = tokenizer.texts_to_sequences([new_sentence]) # 정수 인코딩
  pad_new = pad_sequences(encoded, maxlen = max_len) # 패딩
  score = float(loaded_model.predict(pad_new)) # 예측
  if(score > 0.5):
    print("{:.2f}% 확률로 긍정 리뷰입니다.\n".format(score * 100))
  else:
    print("{:.2f}% 확률로 부정 리뷰입니다.\n".format((1 - score) * 100))

# 이모티콘 유니코드 패턴
emoji_pattern = re.compile("["
        u"\U0001F600-\U0001F64F"  # emoticons
        u"\U0001F300-\U0001F5FF"  # symbols & pictographs
        u"\U0001F680-\U0001F6FF"  # transport & map symbols
        u"\U0001F1E0-\U0001F1FF"  # flags (iOS)
                           "]+", flags=re.UNICODE)

# 유튜브 데이터 전처리
def youtube_comment_processing(filename) :
    df = pd.read_excel(filename)
    comment_dic = {}  # 추출한 댓글 데이터
    comment_result = []  # 전처리 후 데이터

    # 엑셀 파일로부터 데이터 추출
    for i in df.index:
        comment = df.loc[i, 'comment']  # 댓글 내용
        author = df.loc[i, 'author']  # 작성자
        date = df.loc[i, 'date']  # 작성 날짜
        num_likes = df.loc[i, 'num_likes']  # 좋아요 개수
        comment_dic[i] = comment  # comment_dic 딕셔너리에 추가

    for val in comment_dic.values() :
        print(val) # 댓글 원본
        val = re.sub(emoji_pattern, "", val) # 이모티콘 제거
        val = re.sub("<br>|♡", " ", val) # <br> 한줄띄기 -> 스페이스 공백으로 변환 , 제거 이모티콘 추가
        comment_result.append(val)

    for i in comment_result:
        print("------------------전처리 후-----------------")
        print(i)
        sentiment_predict(i)

youtube_comment_processing("test.xlsx")