import torch
from torch import nn
import torch.nn.functional as F
import torch.optim as optim
from torch.utils.data import Dataset, DataLoader
import gluonnlp as nlp
import numpy as np
from tqdm import tqdm, tqdm_notebook
# kobert
from kobert.utils import get_tokenizer
from kobert.pytorch_kobert import get_pytorch_kobert_model
# transformers
from transformers import AdamW
from transformers.optimization import get_cosine_schedule_with_warmup
import pandas as pd
import re
from konlpy.tag import Okt
from bs4 import BeautifulSoup

# GPU 사용
USE_CUDA = torch.cuda.is_available()  # gpu 세팅 확인 -> true or false
print(USE_CUDA)
#device = torch.device('cuda:0')  # gpu 사용 코드
device = torch.device('cpu') # cpu 사용 코드


# BERT 모델, Vocabulary 불러오기
bertmodel, vocab = get_pytorch_kobert_model()

# 토큰화
tokenizer = get_tokenizer()
tok = nlp.data.BERTSPTokenizer(tokenizer, vocab, lower=False)

# Setting parameters
max_len = 64
batch_size = 128
warmup_ratio = 0.1
num_epochs = 5
max_grad_norm = 1
log_interval = 200
learning_rate = 5e-5
num_workers = 0

okt = Okt()
f = open("stopwords.txt", 'r', encoding='utf8')
stopwords = f.read()


class BERTDataset(Dataset):
    def __init__(self, dataset, sent_idx, label_idx, bert_tokenizer, max_len,
                 pad, pair):
        transform = nlp.data.BERTSentenceTransform(
            bert_tokenizer, max_seq_length=max_len, pad=pad, pair=pair)

        self.sentences = [transform([i[sent_idx]]) for i in dataset]
        self.labels = [np.int32(i[label_idx]) for i in dataset]

    def __getitem__(self, i):
        return (self.sentences[i] + (self.labels[i],))

    def __len__(self):
        return (len(self.labels))

class BERTClassifier(nn.Module):
    def __init__(self,
                 bert,
                 hidden_size=768,
                 num_classes=7,  ##클래스 수 조정##
                 dr_rate=None,
                 params=None):
        super(BERTClassifier, self).__init__()
        self.bert = bert
        self.dr_rate = dr_rate

        self.classifier = nn.Linear(hidden_size, num_classes)
        if dr_rate:
            self.dropout = nn.Dropout(p=dr_rate)

    def gen_attention_mask(self, token_ids, valid_length):
        attention_mask = torch.zeros_like(token_ids)
        for i, v in enumerate(valid_length):
            attention_mask[i][:v] = 1
        return attention_mask.float()

    def forward(self, token_ids, valid_length, segment_ids):
        attention_mask = self.gen_attention_mask(token_ids, valid_length)

        _, pooler = self.bert(input_ids=token_ids, token_type_ids=segment_ids.long(),
                              attention_mask=attention_mask.float().to(token_ids.device))
        if self.dr_rate:
            out = self.dropout(pooler)
        return self.classifier(out)


model = BERTClassifier(bertmodel,  dr_rate=0.5).to(device)
model.load_state_dict(torch.load('model_state_dic.pt',map_location=device))
#model = torch.load('em_classify_model.pt',map_location=device)  # 파이토치 모델 로드


def emotion_predict(predict_sentence):
    data = [predict_sentence, '0']
    dataset_another = [data]

    another_test = BERTDataset(dataset_another, 0, 1, tok, max_len, True, False)
    test_dataloader = torch.utils.data.DataLoader(another_test, batch_size=batch_size, num_workers=0)

    model.eval()

    for batch_id, (token_ids, valid_length, segment_ids, label) in enumerate(test_dataloader):
        token_ids = token_ids.long().to(device)
        segment_ids = segment_ids.long().to(device)

        valid_length = valid_length
        label = label.long().to(device)

        out = model(token_ids, valid_length, segment_ids)

        test_eval = []
        for i in out:
            logits = i
            logits = logits.detach().cpu().numpy()

            if np.argmax(logits) == 0:
                test_eval.append("공포가")
            elif np.argmax(logits) == 1:
                test_eval.append("놀람이")
            elif np.argmax(logits) == 2:
                test_eval.append("분노가")
            elif np.argmax(logits) == 3:
                test_eval.append("슬픔이")
            elif np.argmax(logits) == 4:
                test_eval.append("중립이")
            elif np.argmax(logits) == 5:
                test_eval.append("행복이")
            elif np.argmax(logits) == 6:
                test_eval.append("혐오가")

        return np.argmax(logits)


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


def emClassifyProcessing(filename):
    df = pd.read_excel(filename)

    comments = df.values  # 댓글 데이터(댓글, 작성자, 날짜, 좋아요 수)
    comment_result = []  # 전처리 후 데이터
    dic_return = []  # 스프링으로 전송할 json 데이터

    for i in comments:
        val = i[0]
        timeInComment = []
        print(val)
        val = re.sub("<br>", " ", val)  # <br> 한줄띄기 -> 스페이스 공백으로 변환 , 제거 이모티콘 추가
        val = emoticonToWord(val)  # 이모티콘 텍스트로 변환
        val = re.sub(emoji_pattern, "", val)  # 이모티콘 제거
        remove_emoji(val)  # 이모티콘 제거
        soup = BeautifulSoup(val, 'html.parser')
        for j in soup.find_all('a'):  # 타임라인 처리
            timeInComment.append(j.text)
        print(timeInComment)
        comment_result.append(val)

    for i in range(len(comment_result)):  # 배열 크기만큼 실행
        print("------------------전처리 후-----------------")
        print("댓글: " + comments[i][0])
        print("작성자: " + comments[i][1])
        print("작성 날짜: " + comments[i][2])
        print("좋아요 개수: " + str(comments[i][3]))
        index = emotion_predict(comment_result[i])
        if index == 0:
            print("분석결과 : 공포\n")
            dic_temp = {"index": "2", "id": comments[i][1], "comment": comments[i][0],
                        "date": comments[i][2], "num_like": str(comments[i][3])}
            dic_return.append(dic_temp)
        elif index == 1:
            print("분석결과 : 놀람\n")
            dic_temp = {"index": "3", "id": comments[i][1], "comment": comments[i][0],
                        "date": comments[i][2], "num_like": str(comments[i][3])}
            dic_return.append(dic_temp)
        elif index == 2:
            print("분석결과 : 분노\n")
            dic_temp = {"index": "4", "id": comments[i][1], "comment": comments[i][0],
                        "date": comments[i][2], "num_like": str(comments[i][3])}
            dic_return.append(dic_temp)
        elif index == 3:
            print("분석결과 : 슬픔\n")
            dic_temp = {"index": "5", "id": comments[i][1], "comment": comments[i][0],
                        "date": comments[i][2], "num_like": str(comments[i][3])}
            dic_return.append(dic_temp)
        elif index == 4:
            print("분석결과 : 중립\n")
            dic_temp = {"index": "6", "id": comments[i][1], "comment": comments[i][0],
                        "date": comments[i][2], "num_like": str(comments[i][3])}
            dic_return.append(dic_temp)
        elif index == 5:
            print("분석결과 : 행복\n")
            dic_temp = {"index": "7", "id": comments[i][1], "comment": comments[i][0],
                        "date": comments[i][2], "num_like": str(comments[i][3])}
            dic_return.append(dic_temp)
        elif index == 6:
            print("분석결과 : 혐오\n")
            dic_temp = {"index": "8", "id": comments[i][1], "comment": comments[i][0],
                        "date": comments[i][2], "num_like": str(comments[i][3])}
            dic_return.append(dic_temp)
    print(dic_return)
    return dic_return


#emClassifyProcessing("test.xlsx")

"""# 질문 무한반복하기! 0 입력시 종료
end = 1
while end == 1:
    sentence = input("하고싶은 말을 입력해주세요 : ")
    if sentence == 0:
        break
    predict(sentence)
    print("\n")
"""
