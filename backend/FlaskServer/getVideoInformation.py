from googleapiclient.discovery import build
from googleapiclient.errors import HttpError
# from oauth2client.tools import argparser
import re


def getVideoInformation(url):
    DEVELOPER_KEY = 'AIzaSyC02qrZyWZRNMDA60q307mi2S3JrI1pNY0'
    YOUTUBE_API_SERVICE_NAME = 'youtube'
    YOUTUBE_API_VERSION = 'v3'

    youtube = build(YOUTUBE_API_SERVICE_NAME, YOUTUBE_API_VERSION, developerKey=DEVELOPER_KEY)

    videoInformation = []  # 스프링으로 전송할 json 데이터

    title = []  # 동영상 제목
    id = []  # 동영상 id
    date = []  # 동영상 업로드 날짜
    categor_id = []  # 동영상 카테고리 ID
    view = []  # 조회 수
    like = []  # 좋아요 수
    dislike = []  # 싫어요 수
    comment = []  # 댓글 수
    hour = []  # 동영상 재생길이(시)
    min = []  # 동영상 재생길이(분)
    sec = []  # 동영상 재생길이(초)

    video_id = url  # video 아이디

    request = youtube.videos().list(
        id=video_id,
        part='snippet,contentDetails,statistics'
    )

    response = request.execute()

    title.append(response['items'][0]['snippet']['title'])
    date.append(response['items'][0]['snippet']['publishedAt'].split('T')[0])
    view.append(response['items'][0]['statistics']['viewCount'])
    like.append(response['items'][0]['statistics']['likeCount'])

    dic_temp = {"title": title[0], "date": date[0], "view": view[0],
                "like": like[0]}
    videoInformation.append(dic_temp)
    return videoInformation


