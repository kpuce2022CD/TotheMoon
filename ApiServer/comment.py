import pandas
from googleapiclient.discovery import build


def collectComment(url):
    # API 키 등록
    api_key = 'AIzaSyC02qrZyWZRNMDA60q307mi2S3JrI1pNY0'

    # 유튜브 영상 id
    video_id = url
    comments = list()
    api_obj = build('youtube', 'v3', developerKey=api_key)
    response = api_obj.commentThreads().list(part='snippet,replies', videoId=video_id, maxResults=100).execute()

    while response:
        for item in response['items']:
            comment = item['snippet']['topLevelComment']['snippet']
            comments.append(
                [comment['textDisplay'], comment['authorDisplayName'], comment['publishedAt'], comment['likeCount']])

            if item['snippet']['totalReplyCount'] > 0:
                for reply_item in item['replies']['comments']:
                    reply = reply_item['snippet']
                    comments.append(
                        [reply['textDisplay'], reply['authorDisplayName'], reply['publishedAt'], reply['likeCount']])

        if 'nextPageToken' in response:
            response = api_obj.commentThreads().list(part='snippet,replies', videoId=video_id,
                                                     pageToken=response['nextPageToken'], maxResults=100).execute()
        else:
            break
    df = pandas.DataFrame(comments)
    df.to_excel(url + '.xlsx', header=['comment', 'author', 'date', 'num_likes'], index=None)
    return url + '.xlsx'
