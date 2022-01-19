from flask import Flask, request, jsonify
from comment import collectComment
from np_classify import youtube_comment_processing,\
    comment_classify,sentiment_predict,remove_emoji,emoticonToWord



def create_app():
    app = Flask(__name__)

    @app.route('/', methods=['GET'])
    def start():
        collectComment(request.args.get('url'))
        return "success"

    @app.route('/tospring')
    def test():
        array = ["1", "a", "3", "j", "1"]

        return jsonify(array)

    @app.route('/tospring2')
    def test2():
        filepath = collectComment(request.args.get('url'))
        comment_data = youtube_comment_processing(filepath)

        return jsonify(comment_data)

    return app
