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
with open('np_tokenizer.pickle', 'rb') as handle:
    tokenizer = pickle.load(handle)

loaded_model = load_model('np_classify_model.h5')


def comment_classify(comment_file):
    print("dd")


def sentiment_predict(new_sentence):
    new_sentence = re.sub(r'[^ㄱ-ㅎㅏ-ㅣ가-힣 ]', '', new_sentence)
    new_sentence = okt.morphs(new_sentence, stem=True)  # 토큰화
    new_sentence = [word for word in new_sentence if not word in stopwords]  # 불용어 제거
    encoded = tokenizer.texts_to_sequences([new_sentence])  # 정수 인코딩
    pad_new = pad_sequences(encoded, maxlen=max_len)  # 패딩
    score = float(loaded_model.predict(pad_new))  # 예측
    return score

# 이모티콘 제거 함수(ASCII 코드에 해당하지 않는 경우)
def remove_emoji(inputString):
    return inputString.encode('ascii', 'ignore').decode('ascii')


# 이모티콘 유니코드 패턴
emoji_pattern = re.compile("["
                           u"\U0001F600-\U0001F64F"  # emoticons
                           u"\U0001F300-\U0001F5FF"  # symbols & pictographs
                           u"\U0001F680-\U0001F6FF"  # transport & map symbols
                           u"\U0001F1E0-\U0001F1FF"  # flags (iOS)
                           u"\U00002500-\U00002BEF"  # chinese char
                           u"\U00002702-\U000027B0"
                           u"\U00002702-\U000027B0"
                           # u"\U000024C2-\U0001F251"
                           u"\U0001f926-\U0001f937"
                           u"\U00010000-\U0010ffff"
                           u"\u2640-\u2642"
                           u"\u2600-\u2B55"
                           u"\u200d"
                           u"\u23cf"
                           u"\u23e9"
                           u"\u231a"
                           u"\ufe0f"  # dingbats
                           u"\u3030"
                           "]+", flags=re.UNICODE)


# 이모티콘 문자로 대체
def emoticonToWord(comment):
    emoticon_list = ["❤️", "❤", "🧡", "💛", "💚", "💙", "💞", "💓", "💜", "❣️", "💕", "💘", "💗", "💓", "💝", "💟",
                     "😻", "💔", "👍", "👎", "🙌", "😘", "😍", "😃", "😄", "😁", "😆", "☺️", "😊", "😚", "🤗", "😭",
                     "😢", "😤", "😠", "😡", "🤬", "😳", "🤔", "🤣", "🥰", "🤢", "🥳"]
    emotion_list = [" 사랑해요", " 사랑해요", " 사랑해요", " 사랑해요", " 사랑해요", " 사랑해요", " 사랑해요", " 사랑해요", " 사랑해요", " 사랑해요", " 사랑해요",
                    " 사랑해요", " 사랑해요", " 사랑해요", " 사랑해요", " 사랑해요", " 사랑해요", " 싫어해요", " 최고에요", "최악이에요", "만세", " 사랑해요",
                    " 사랑해요", " 좋아요", " 좋아요", " 좋아요", " 좋아요", " 좋아요", " 좋아요", " 좋아요", " 좋아요", " 슬퍼요", " 슬퍼요", " 삐졌어요",
                    " 화났어요", " 화났어요", " 화났어요", " 잘 모르겠어요", " 고민해 볼게요", " 좋아요", " 좋아요", " 역겨워요", " 축하해요"]
    emodic = dict(zip(emoticon_list, emotion_list))
    templist = list(comment)  # 수정을 위해서 임시로 문장-> 리스트

    for index, word in enumerate(comment):
        if word in emoticon_list:  # 정의한 이모티콘리스트에 있다면
            temp = word.replace(word, emodic[word])  # 해당 이모티콘을 설정한 단어로 변환
            templist[index] = temp
    comment = "".join(templist)  # 리스트 -> 문장
    return comment


# 유튜브 데이터 전처리
def youtube_comment_processing(filename):
    df = pd.read_excel(filename)

    comments = df.values    # 댓글 데이터(댓글, 작성자, 날짜, 좋아요 수)
    comment_result = []  # 전처리 후 데이터
    dic_return = [] # 스프링으로 전송할 json 데이터

    for i in comments:
        val = i[0]
        print(val)
        val = re.sub("<br>", " ", val)  # <br> 한줄띄기 -> 스페이스 공백으로 변환 , 제거 이모티콘 추가
        val = emoticonToWord(val)  # 이모티콘 텍스트로 변환
        val = re.sub(emoji_pattern, "", val)  # 이모티콘 제거
        remove_emoji(val) #이모티콘 제거
        comment_result.append(val)

    for i in range(len(comment_result)):  # 배열 크기만큼 실행
        print("------------------전처리 후-----------------")
        print("댓글: " + comments[i][0])
        print("작성자: " + comments[i][1])
        print("작성 날짜: " + comments[i][2])
        print("좋아요 개수: " + str(comments[i][3]))
        score = sentiment_predict(comment_result[i])
        if (score > 0.5):
            if(score > 0.8):
                print("{:.2f}% 확률로 긍정 리뷰입니다.\n".format(score * 100))
                dic_temp = {"index": "1", "id": comments[i][1], "comment": comments[i][0],
                            "date": comments[i][2], "num_like": str(comments[i][3])}
                dic_return.append(dic_temp)
        else:
            if(score < 0.2):
                print("{:.2f}% 확률로 부정 리뷰입니다.\n".format((1 - score) * 100))
                dic_temp = {"index": "0", "id": comments[i][1], "comment": comments[i][0],
                            "date": comments[i][2], "num_like": str(comments[i][3])}
                dic_return.append(dic_temp)

    return dic_return

##test