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
f = open("stopwords.txt", 'r', encoding='utf8')
stopwords = f.read()
# 정수 인코딩

max_len = 30

# loading
with open('tokenizer.pickle', 'rb') as handle:
    tokenizer = pickle.load(handle)

loaded_model = load_model('best_model.h5')

global comment_dic
comment_dic = {}  # 추출한 댓글 데이터
global author_dic
author_dic = {}  # 추출한 작성자 데이터
global date_dic
date_dic = {}  # 추출한 작성 날짜 데이터
global num_likes_dic
num_likes_dic = {}  # 추출한 좋아요 개수 데이터
global comment_result
comment_result = []  # 전처리 후 데이터
global author_result
author_result = []  # 전처리 후 데이터
global date_result
date_result = []  # 전처리 후 데이터
global num_likes_result
num_likes_result = []  # 전처리 후 데이터

global dic_return  # 스프링으로 전송할 json 데이터
dic_return = []

global dic_temp  # json 배열 추가를 위한 버퍼 역할
dic_temp = {}
def comment_classify(comment_file):
    print("dd")


def sentiment_predict(new_sentence, id, comment, date, num_like):
    global dic_return
    global dic_temp

    new_sentence = re.sub(r'[^ㄱ-ㅎㅏ-ㅣ가-힣 ]', '', new_sentence)
    new_sentence = okt.morphs(new_sentence, stem=True)  # 토큰화
    new_sentence = [word for word in new_sentence if not word in stopwords]  # 불용어 제거
    encoded = tokenizer.texts_to_sequences([new_sentence])  # 정수 인코딩
    pad_new = pad_sequences(encoded, maxlen=max_len)  # 패딩
    score = float(loaded_model.predict(pad_new))  # 예측
    if (score > 0.5):
        print("{:.2f}% 확률로 긍정 리뷰입니다.\n".format(score * 100))
        dic_temp = {"index": "1", "id": id, "comment": comment,
                    "date": date, "num_like": num_like}
        dic_return.append(dic_temp)

    else:
        print("{:.2f}% 확률로 부정 리뷰입니다.\n".format((1 - score) * 100))
        dic_temp = {"index": "0", "id": id, "comment": comment,
                    "date": date, "num_like": num_like}
        dic_return.append(dic_temp)




comment =""
sentiment_predict(comment, 0, comment, 0, 0)
print(comment)