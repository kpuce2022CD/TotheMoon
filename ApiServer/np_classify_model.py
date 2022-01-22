import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import re
import urllib.request
from konlpy.tag import Okt
from tqdm import tqdm
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
from tensorflow.keras.layers import Embedding, Dense, LSTM
from tensorflow.keras.models import Sequential
from tensorflow.keras.models import load_model
from tensorflow.keras.callbacks import EarlyStopping, ModelCheckpoint
import pickle


movie_train_data = pd.read_table('movie_train.txt')
movie_test_data = pd.read_table('movie_test.txt')
steam_train_data = pd.read_table('steam_train.txt')
steam_test_data = pd.read_table('steam_test.txt')
shopping_train_data = pd.read_table('shopping_train.txt')
shopping_test_data = pd.read_table('shopping_test.txt')

movie_train_data['document'] = movie_train_data['document'].astype('string')
movie_test_data['document'] = movie_test_data['document'].astype('string')
steam_train_data['document'] = steam_train_data['document'].astype('string')
steam_test_data['document'] = steam_test_data['document'].astype('string')
shopping_train_data['document'] = shopping_train_data['document'].astype('string')
shopping_test_data['document'] = shopping_test_data['document'].astype('string')


"""movie_train_data['document'].dtype
movie_test_data['document'].dtype
steam_train_data['document'].dtype
steam_test_data['document'].dtype
shopping_train_data['document'].dtype
shopping_test_data['document'].dtype
"""


print('영화 데이터셋 훈련용 리뷰 개수 :', len(movie_train_data))  # 훈련용 리뷰 개수 출력
print('스팀 데이터셋 훈련용 리뷰 개수 :', len(steam_train_data))  # 훈련용 리뷰 개수 출력
print('쇼핑 데이터셋 훈련용 리뷰 개수 :', len(shopping_train_data))  # 훈련용 리뷰 개수 출력

movie_train_data[:5]  # 상위 5개 출력
steam_train_data[:5]  # 상위 5개 출력
shopping_train_data[:5]  # 상위 5개 출력

print('영화 데이터셋 테스트용 리뷰 개수 :', len(movie_test_data))  # 테스트용 리뷰 개수 출력
print('스팀 데이터셋 테스트용 리뷰 개수 :', len(steam_test_data))  # 테스트용 리뷰 개수 출력
print('쇼핑 데이터셋 테스트용 리뷰 개수 :', len(shopping_test_data))  # 테스트용 리뷰 개수 출력

# 영화 데이터셋의 document 열의 중복 제거
movie_train_data.drop_duplicates(subset=['document'], inplace=True)
print('총 샘플의 수 :', len(movie_train_data))

movie_train_data['label'].value_counts().plot(kind='bar')

# 스팀 데이터셋의 document 열의 중복 제거
steam_train_data.drop_duplicates(subset=['document'], inplace=True)
print('총 샘플의 수 :', len(steam_train_data))

steam_train_data['label'].value_counts().plot(kind='bar')

# 쇼핑 데이터셋의 document 열의 중복 제거
shopping_train_data.drop_duplicates(subset=['document'], inplace=True)
print('총 샘플의 수 :', len(shopping_train_data))

shopping_train_data['label'].value_counts().plot(kind='bar')

# 레이블 분포 확인
print(movie_train_data.groupby('label').size().reset_index(name='count'))
print(steam_train_data.groupby('label').size().reset_index(name='count'))
print(shopping_train_data.groupby('label').size().reset_index(name='count'))

# 널 값 체크
print(movie_train_data.isnull().values.any())
print(steam_train_data.isnull().values.any())
print(shopping_train_data.isnull().values.any())

# 널 값 위치 체크
print(movie_train_data.isnull().sum())
movie_train_data.loc[movie_train_data.document.isnull()]

# 널 값 위치 체크
print(steam_train_data.isnull().sum())
steam_train_data.loc[steam_train_data.document.isnull()]

# 널 값 위치 체크
print(shopping_train_data.isnull().sum())
shopping_train_data.loc[shopping_train_data.document.isnull()]

movie_train_data = movie_train_data.dropna(how='any')  # Null 값이 존재하는 행 제거
print(movie_train_data.isnull().values.any())  # Null 값이 존재하는지 확인

steam_train_data = steam_train_data.dropna(how='any')  # Null 값이 존재하는 행 제거
print(steam_train_data.isnull().values.any())  # Null 값이 존재하는지 확인

shopping_train_data = shopping_train_data.dropna(how='any')  # Null 값이 존재하는 행 제거
print(shopping_train_data.isnull().values.any())  # Null 값이 존재하는지 확인

# 영화 데이터셋 한글과 공백을 제외하고 모두 제거
movie_train_data['document'] = movie_train_data['document'].str.replace("[^ㄱ-ㅎㅏ-ㅣ가-힣 ]", "")
movie_train_data[:5]

# 스팀 데이터셋 한글과 공백을 제외하고 모두 제거
steam_train_data['document'] = steam_train_data['document'].str.replace("[^ㄱ-ㅎㅏ-ㅣ가-힣 ]", "")
steam_train_data[:5]

# 쇼핑 데이터셋 한글과 공백을 제외하고 모두 제거
shopping_train_data['document'] = shopping_train_data['document'].str.replace("[^ㄱ-ㅎㅏ-ㅣ가-힣 ]", "")
shopping_train_data[:5]

# 영화 데이터셋 한글, 공백 제거 후 널 값 체크
movie_train_data['document'] = movie_train_data['document'].str.replace('^ +', "")  # white space 데이터를 empty value로 변경
movie_train_data['document'].replace('', np.nan, inplace=True)
print(movie_train_data.isnull().sum())

# 스팀 데이터셋 한글, 공백 제거 후 널 값 체크
steam_train_data['document'] = steam_train_data['document'].str.replace('^ +', "")  # white space 데이터를 empty value로 변경
steam_train_data['document'].replace('', np.nan, inplace=True)
print(steam_train_data.isnull().sum())

# 쇼핑 데이터셋 한글, 공백 제거 후 널 값 체크
shopping_train_data['document'] = shopping_train_data['document'].str.replace('^ +',"")  # white space 데이터를 empty value로 변경
shopping_train_data['document'].replace('', np.nan, inplace=True)
print(shopping_train_data.isnull().sum())

# 영화 데이터셋 널 값 제거 후 체크
movie_train_data = movie_train_data.dropna(how='any')
print(len(movie_train_data))

# 스팀 데이터셋 널 값 제거 후 체크
steam_train_data = steam_train_data.dropna(how='any')
print(len(steam_train_data))

# 쇼핑 데이터셋 널 값 제거 후 체크
shopping_train_data = shopping_train_data.dropna(how='any')
print(len(shopping_train_data))

movie_test_data.drop_duplicates(subset=['document'], inplace=True)  # document 열에서 중복인 내용이 있다면 중복 제거
movie_test_data['document'] = movie_test_data['document'].str.replace("[^ㄱ-ㅎㅏ-ㅣ가-힣 ]", "")  # 정규 표현식 수행
movie_test_data['document'] = movie_test_data['document'].str.replace('^ +', "")  # 공백은 empty 값으로 변경
movie_test_data['document'].replace('', np.nan, inplace=True)  # 공백은 Null 값으로 변경
movie_test_data = movie_test_data.dropna(how='any')  # Null 값 제거
print('전처리 후 영화 테스트용 샘플의 개수 :', len(movie_test_data))

steam_test_data.drop_duplicates(subset=['document'], inplace=True)  # document 열에서 중복인 내용이 있다면 중복 제거
steam_test_data['document'] = steam_test_data['document'].str.replace("[^ㄱ-ㅎㅏ-ㅣ가-힣 ]", "")  # 정규 표현식 수행
steam_test_data['document'] = steam_test_data['document'].str.replace('^ +', "")  # 공백은 empty 값으로 변경
steam_test_data['document'].replace('', np.nan, inplace=True)  # 공백은 Null 값으로 변경
steam_test_data = steam_test_data.dropna(how='any')  # Null 값 제거
print('전처리 후 스팀 테스트용 샘플의 개수 :', len(steam_test_data))

shopping_test_data.drop_duplicates(subset=['document'], inplace=True)  # document 열에서 중복인 내용이 있다면 중복 제거
shopping_test_data['document'] = shopping_test_data['document'].str.replace("[^ㄱ-ㅎㅏ-ㅣ가-힣 ]", "")  # 정규 표현식 수행
shopping_test_data['document'] = shopping_test_data['document'].str.replace('^ +', "")  # 공백은 empty 값으로 변경
shopping_test_data['document'].replace('', np.nan, inplace=True)  # 공백은 Null 값으로 변경
shopping_test_data = shopping_test_data.dropna(how='any')  # Null 값 제거
print('전처리 후 쇼핑 테스트용 샘플의 개수 :', len(shopping_test_data))

stopwords = ['의', '가', '이', '은', '들', '는', '좀', '잘', '걍', '과', '도', '를', '으로', '자', '에', '와', '한', '하다']
okt = Okt()

# 영화 훈련 데이터셋 토큰화
X_train = []
for sentence in tqdm(movie_train_data['document']):
    tokenized_sentence = okt.morphs(sentence, stem=True)  # 토큰화
    stopwords_removed_sentence = [word for word in tokenized_sentence if not word in stopwords]  # 불용어 제거
    X_train.append(stopwords_removed_sentence)

# print(len(X_train)) : 145393

# 스팀 훈련 데이터셋 토큰화
for sentence in tqdm(steam_train_data['document']):
    tokenized_sentence = okt.morphs(sentence, stem=True)  # 토큰화
    stopwords_removed_sentence = [word for word in tokenized_sentence if not word in stopwords]  # 불용어 제거
    X_train.append(stopwords_removed_sentence)

# print(len(X_train)) : 218437

# 쇼핑 훈련 데이터셋 토큰화
for sentence in tqdm(shopping_train_data['document']):
    tokenized_sentence = okt.morphs(sentence, stem=True)  # 토큰화
    stopwords_removed_sentence = [word for word in tokenized_sentence if not word in stopwords]  # 불용어 제거
    X_train.append(stopwords_removed_sentence)

# print(len(X_train)) : 370286

print(X_train)

# 영화 테스트 데이터셋 토큰화
X_test = []
for sentence in tqdm(movie_test_data['document']):
    tokenized_sentence = okt.morphs(sentence, stem=True)  # 토큰화
    stopwords_removed_sentence = [word for word in tokenized_sentence if not word in stopwords]  # 불용어 제거
    X_test.append(stopwords_removed_sentence)

# 스팀 테스트 데이터셋 토큰화
for sentence in tqdm(steam_test_data['document']):
    tokenized_sentence = okt.morphs(sentence, stem=True)  # 토큰화
    stopwords_removed_sentence = [word for word in tokenized_sentence if not word in stopwords]  # 불용어 제거
    X_test.append(stopwords_removed_sentence)

# 쇼핑 테스트 데이터셋 토큰화
for sentence in tqdm(shopping_test_data['document']):
    tokenized_sentence = okt.morphs(sentence, stem=True)  # 토큰화
    stopwords_removed_sentence = [word for word in tokenized_sentence if not word in stopwords]  # 불용어 제거
    X_test.append(stopwords_removed_sentence)

# print(len(X_train)) : 370285

tokenizer = Tokenizer()
tokenizer.fit_on_texts(X_train)

# print(len(X_train)) : 370285

print(tokenizer.word_index)

threshold = 3
total_cnt = len(tokenizer.word_index)  # 단어의 수
rare_cnt = 0  # 등장 빈도수가 threshold보다 작은 단어의 개수를 카운트
total_freq = 0  # 훈련 데이터의 전체 단어 빈도수 총 합
rare_freq = 0  # 등장 빈도수가 threshold보다 작은 단어의 등장 빈도수의 총 합

# 단어와 빈도수의 쌍(pair)을 key와 value로 받는다.
for key, value in tokenizer.word_counts.items():
    total_freq = total_freq + value

    # 단어의 등장 빈도수가 threshold보다 작으면
    if (value < threshold):
        rare_cnt = rare_cnt + 1
        rare_freq = rare_freq + value

print('단어 집합(vocabulary)의 크기 :', total_cnt)
print('등장 빈도가 %s번 이하인 희귀 단어의 수: %s' % (threshold - 1, rare_cnt))
print("단어 집합에서 희귀 단어의 비율:", (rare_cnt / total_cnt) * 100)
print("전체 등장 빈도에서 희귀 단어 등장 빈도 비율:", (rare_freq / total_freq) * 100)

# 전체 단어 개수 중 빈도수 2이하인 단어는 제거.
# 0번 패딩 토큰을 고려하여 + 1
vocab_size = total_cnt - rare_cnt + 1
print('단어 집합의 크기 :', vocab_size)

tokenizer = Tokenizer(vocab_size)
tokenizer.fit_on_texts(X_train)
X_train = tokenizer.texts_to_sequences(X_train)
X_test = tokenizer.texts_to_sequences(X_test)

print(X_train[:3])

y_train = np.array(movie_train_data['label'])
y_test = np.array(movie_test_data['label'])
y_train = np.append(y_train, np.array(steam_train_data['label']))
y_test = np.append(y_test, steam_test_data['label'])
y_train = np.append(y_train, shopping_train_data['label'])
y_test = np.append(y_test, shopping_test_data['label'])

# print(len(y_train)) : 370285

print(len(X_train))
print(len(y_train))

print(len(X_test))
print(len(y_test))

print(X_train)
print(X_test)
print(y_train)
print(y_test)

drop_train = [index for index, sentence in enumerate(X_train) if len(sentence) < 1]

print(drop_train)
print(y_train)
# 빈 샘플들을 제거
X_train = np.delete(X_train, drop_train, axis=0)
y_train = np.delete(y_train, drop_train, axis=0)
print(len(X_train))
print(len(y_train))

print('리뷰의 최대 길이 :', max(len(review) for review in X_train))
print('리뷰의 평균 길이 :', sum(map(len, X_train)) / len(X_train))
plt.hist([len(review) for review in X_train], bins=50)
plt.xlabel('length of samples')
plt.ylabel('number of samples')
plt.show()

# IndexError: index 370135 is out of bounds for axis 0 with size 369805
# IndexError: index 145613 is out of bounds for axis 0 with size 145393

def below_threshold_len(max_len, nested_list):
  count = 0
  for sentence in nested_list:
    if(len(sentence) <= max_len):
        count = count + 1
  print('전체 샘플 중 길이가 %s 이하인 샘플의 비율: %s'%(max_len, (count / len(nested_list))*100))

max_len = 30
below_threshold_len(max_len, X_train)

X_train = pad_sequences(X_train, maxlen=max_len)
X_test = pad_sequences(X_test, maxlen=max_len)

# 데이터 모델링
embedding_dim = 100
hidden_units = 128

model = Sequential()
model.add(Embedding(vocab_size, embedding_dim))
model.add(LSTM(hidden_units))
model.add(Dense(1, activation='sigmoid'))

es = EarlyStopping(monitor='val_loss', mode='min', verbose=1, patience=4)
mc = ModelCheckpoint('np_classify_model.h5', monitor='val_acc', mode='max', verbose=1, save_best_only=True)

model.compile(optimizer='rmsprop', loss='binary_crossentropy', metrics=['acc'])
history = model.fit(X_train, y_train, epochs=15, callbacks=[es, mc], batch_size=64, validation_split=0.2)

# 토크나이저 파일 생성
# saving
with open('np_tokenizer.pickle', 'wb') as handle:
    pickle.dump(tokenizer, handle, protocol=pickle.HIGHEST_PROTOCOL)

# 모델 테스트 정확도 측정
loaded_model = load_model('np_classify_model.h5')
print("\n 테스트 정확도: %.4f" % (loaded_model.evaluate(X_test, y_test)[1]))

# 정확도 측정 함수
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

