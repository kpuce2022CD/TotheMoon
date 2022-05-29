# -*- coding: utf-8 -*-
from flask import Flask, request, jsonify
from comment import collectComment
from timeline.timelineExtractor import timelineExtractor
from findKeyword.keywordExtractor import keywordExtractor
from comment_classify import *
from interest.interestExtractor import get_interestData
from getVideoInformation import getVideoInformation

def create_app():
    
    app = Flask(__name__)
    
    @app.route('/', methods=['GET'])
    def start():
       collectComment(request.args.get('url'))
       return "success"

    @app.route('/classifycomments')
    def classifyComments():
        filepath = collectComment(request.args.get('url'))
        PosiNegative_comment_data, Emotion_comment_data = CommentClassifyProcessing(filepath)
        result_data = PosiNegative_comment_data + Emotion_comment_data
        return jsonify(result_data)

    @app.route('/searchkeyword')
    def searchKeyword():
        filepath = collectComment(request.args.get('url'))
        common_words = ['많이', '영상', '너무', '이번', '내가', 'ㅋㅋ', '남자', '조합', '이거', '있는', '아닌', '진짜', '아니', 'ㅠㅠ', '계속', '좋아',
                        '이렇게', '좋겠다', '제발', '사람', '점점', '보고', '근데', '그냥', '다음', '여기', '뭔가', '보는', 'ㄹㅇ', '하는', '언제',
                        '한번', '같아', '이런', '무슨', '지금', '같음', '느낌', '보면', '우리', '이제', 'ㅎㅎ', '알았', '보니', '좋다', '항상', '어떻게',
                        '다들', '생각', '가는', '그런', '하나', '어디', '좋은', '좋네', '보기', '올라', '응원', '올려', '진짜']

        keywords = keywordExtractor(common_words)
        keywords.get_comments_from_excel(filepath)
        best_5_keywords, comments = keywords.get_comments_related_to_best5keywords(keywords.keywords_from_soynlp)
        data = {'b5': best_5_keywords, 'comments': comments}
        return jsonify(data)

    @app.route('/timeline')
    def getTimeline():
        filepath = collectComment(request.args.get('url'))
        extractor = timelineExtractor()
        data = extractor.get_comments_from_excel(filepath).get_best_timelines()
        return jsonify(data)

    @app.route('/find')
    def find():
        filepath = collectComment(request.args.get('url'))
        keywords = keywordExtractor()
        keywords.get_comments_from_excel(filepath)
        comments = keywords.get_comments_related_to_keyword(request.args.get('keyword'))
        return jsonify(comments)

    @app.route('/interest')
    def get_interest():
        filepath = collectComment(request.args.get('url'))
        interest_data = get_interestData(filepath)
        return jsonify(interest_data)

    @app.route('/getvideoinformation')
    def getVideoData():
        videoInformation = getVideoInformation(request.args.get('url'))
        return jsonify(videoInformation)



    return app


if __name__=="__main__":
    
    create_app().run()