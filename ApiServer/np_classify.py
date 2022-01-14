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

train_data = pd.read_table('ratings_train.txt')
test_data = pd.read_table('ratings_test.txt')

okt = Okt()
stopwords = ['의','가','이','은','들','는','좀','잘','걍','과','도','를','으로','자','에','와','한','하다']
# 정수 인코딩

max_len = 30

# loading
with open('tokenizer.pickle', 'rb') as handle:
    tokenizer = pickle.load(handle)


loaded_model = load_model('best_model.h5')



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



sentiment_predict('영상 보니 너무 웃기네요')
sentiment_predict('영상 너무 재밌어요')
sentiment_predict("장난하냐 이게 뭐냐")
sentiment_predict("개 노잼")
sentiment_predict("주호민작가님은 이말년 작가님을 볼 때 정말 애정을 담아서 바라보시네요...")
sentiment_predict("와 이말년이 맛있게 먹으니까 진짜 맛있어보이네 ㅋㅋㅋㅋ")